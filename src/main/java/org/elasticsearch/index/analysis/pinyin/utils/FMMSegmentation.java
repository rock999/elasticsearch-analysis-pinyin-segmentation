package org.elasticsearch.index.analysis.pinyin.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FMMSegmentation {

    public static List<String> splitSpell(String s) {
        String regEx = "[^aoeiuv]?h?[iuv]?(ai|ei|ao|ou|er|ang?|eng?|ong|a|o|e|i|u|ng|n)?";
        int tag;
        List<String> tokenResult = new LinkedList<String>();
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

}
