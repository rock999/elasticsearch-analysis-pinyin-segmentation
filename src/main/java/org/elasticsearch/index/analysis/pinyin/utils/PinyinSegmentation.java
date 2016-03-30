package org.elasticsearch.index.analysis.pinyin.utils;

import java.util.List;

public class PinyinSegmentation {

    public static List<String> split(String s) {
        List<String> rawPinyins = FMMSegmentation.splitSpell(s);

        return rawPinyins;
    }
}
