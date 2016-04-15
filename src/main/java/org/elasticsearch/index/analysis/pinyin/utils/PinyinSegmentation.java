package org.elasticsearch.index.analysis.pinyin.utils;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.index.analysis.pinyin.entity.TokenEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class aims to partition the pinyin string, such as liudehua,
 * into single pinyin strings, liu, de and hua
 *
 * The basic idea comes from the paper
 * Ambiguity Solution of Pinyin Segmentation in Continuous Pinyin-to-Character Conversion
 * (http://ieeexplore.ieee.org/xpl/login.jsp?tp=&arnumber=4906775&url=http%3A%2F%2Fieeexplore.ieee.org%2Fiel5%2F4815086%2F4906745%2F04906775.pdf%3Farnumber%3D4906775)
 *
 * firstly an FMM segmentation is used, which splits the string to the longest pinyin and may lead to the ambiguity of overlap and combination,
 * therefore two ambiguity solvers are called to solve the overlap and combination respectively.
 */
public class PinyinSegmentation {

    private static final double INITIAL_LETTER_THRES = 0.5;

    // initial letter mode means adding the first letter as token
    // eg. given "liudehua" we got "liu" "l" "de" "d" "hua" "h"
    // eg. given "刘德华" we got "刘 ""liu" "l" "德" "de" "d" "华" "hua" "h"
    // eg. given "刘德hua" we got "刘" "liu" "l" "德" "de" "d" "hua" "h"
    // The above mode is not friendly to the phrase_match eg
    // de will match to dian since they share the d as the initial letter
    final boolean initialLetterMode = false;

    public List<TokenEntity> split(String s) {
        List<String> splitted = FMMSegmentation.split(s);
        if ( splitted.size() * 1.0 / s.length() > INITIAL_LETTER_THRES) {
            // if the number of splitted words is more than half of the length of s,
            // then assume that s is a string of initial letters
            // for example given 'ldh' splitted to 'l' 'd' 'h', equal to the size of 'ldh'
            // so 'ldh' is more likely to be the init letters of "liudehua" but not three terms

            return Lists.newArrayList(new TokenEntity()
                            .setBeginOffset(0)
                            .setEndOffset(s.length())
                            .setRelativePosition(0)
                            .setValue(s));
        }

        List<TokenEntity> rawPinyins = TokenEntity.wrap(splitted);
        List<TokenEntity> initialLetters;
        if (initialLetterMode) {
           initialLetters = grepInitialLetters(rawPinyins);
        }
        OverlapAmbiguitySolver.solve(rawPinyins);
        CombinationAmbiguitySolver.solve(rawPinyins);

        if (initialLetterMode) {
            rawPinyins.addAll(initialLetters);
        }
        Collections.sort(rawPinyins, new Comparator<TokenEntity>() {
            @Override
            public int compare(TokenEntity o1, TokenEntity o2) {
                return o1.getRelativePosition() - o2.getRelativePosition();
            }
        });
        return rawPinyins;
    }

    private List<TokenEntity> grepInitialLetters(List<TokenEntity> tokens) {
        List<TokenEntity> result = Lists.newArrayList();
        for (TokenEntity t : tokens) {
            String value = t.getValue();
            if (value != null && value.length() > 1 && value.charAt(0) < 128) {
                result.add(t.duplicate()
                        .setValue(t.getValue().substring(0, 1)));
            }
        }
        return result;
    }
}
