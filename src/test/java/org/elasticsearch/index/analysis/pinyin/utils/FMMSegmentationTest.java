package org.elasticsearch.index.analysis.pinyin.utils;

import junit.framework.TestCase;

public class FMMSegmentationTest extends TestCase {

    public void testSplitSpell() throws Exception {

        String pinyin = "我爱北京tiananmen";
        System.out.println(FMMSegmentation.splitSpell(pinyin));

    }
}