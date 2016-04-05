package org.elasticsearch.index.analysis.pinyin.utils;

import junit.framework.TestCase;

public class FMMSegmentationTest extends TestCase {

    public void testSplitSpell() throws Exception {

        String pinyin = "我爱北京tiananmen";
        System.out.println(FMMSegmentation.splitSpell(pinyin));

        pinyin = "fh";
        System.out.println(FMMSegmentation.splitSpell(pinyin));
    }

    public void testTrieTree() {

        System.out.println(FMMSegmentation.trieTree.contains("ting".toCharArray()));

    }

    public void testSplit() {

        System.out.println(FMMSegmentation.split("我爱北京tiananmen"));
        System.out.println(FMMSegmentation.split("三乡青nianejia"));
        System.out.println(FMMSegmentation.split("fh"));
        System.out.println(FMMSegmentation.split("heheda"));
        System.out.println(FMMSegmentation.split("yingoulifanchuan"));
        System.out.println(FMMSegmentation.split("buzhidaoshuoshenmehaole"));


    }
}