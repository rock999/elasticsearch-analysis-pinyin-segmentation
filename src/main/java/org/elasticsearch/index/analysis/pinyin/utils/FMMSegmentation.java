package org.elasticsearch.index.analysis.pinyin.utils;

import org.elasticsearch.common.collect.Lists;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FMMSegmentation {

    /**
     * This is a Regular-Expression based implementation
     * "fh" is not split in this implementation
     * @param s
     * @return
     */
    public static List<String> splitSpell(String s) {
        String regEx = "[^aoeiuv]?h?[iuv]?(ai|ei|ao|ou|er|ang?|eng?|ong|a|o|e|i|u|ng|n)?";
        int tag;
        List<String> tokenResult = Lists.newArrayList();;
        Pattern pat = Pattern.compile(regEx);
        for (int i = s.length(); i > 0; i = i - tag) {
            Matcher matcher = pat.matcher(s);
            matcher.find();
            tokenResult.add(matcher.group());
            tag = matcher.end() - matcher.start();
            s = s.substring(tag);
        }

        return tokenResult;
    }

    // Use a trie tree to implement the FMM segmentation
    // FIXME: implement this funcitoin
    public static List<String> split(String s) {
        List<String> tokenResult = Lists.newArrayList();

        return tokenResult;
    }

}
