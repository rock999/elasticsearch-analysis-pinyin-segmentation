package org.elasticsearch.index.analysis.pinyin.utils;

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

    public static List<String> split(String s) {
        List<String> rawPinyins = FMMSegmentation.splitSpell(s);
        OverlapAmbiguitySolver.solve(rawPinyins);
        CombinationAmbiguitySolver.solve(rawPinyins);
        return rawPinyins;
    }
}
