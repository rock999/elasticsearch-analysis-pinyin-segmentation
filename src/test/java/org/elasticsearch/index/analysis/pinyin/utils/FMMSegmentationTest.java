package org.elasticsearch.index.analysis.pinyin.utils;

import junit.framework.TestCase;
import org.elasticsearch.common.collect.Lists;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class FMMSegmentationTest extends TestCase {

    public void testSplitSpell() throws Exception {

        String pinyin = "hehuangongyu";
        System.out.println(FMMSegmentation.splitSpell(pinyin));

        pinyin = "fh";
        System.out.println(FMMSegmentation.splitSpell(pinyin));
    }

    public void testTrieTree() {

        // Assert all the words in the test file are also contained in the trie-tree
        try (
                InputStream fis = Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream("spell.txt");
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(isr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                Assert.assertTrue(FMMSegmentation.trieTree.contains(line.toCharArray()));
            }
        } catch (IOException e) {
            System.out.println("Load spell error {}" + e);
        }

        Assert.assertFalse(FMMSegmentation.trieTree.contains("tiang".toCharArray()));
        Assert.assertFalse(FMMSegmentation.trieTree.contains("ttian".toCharArray()));
        Assert.assertFalse(FMMSegmentation.trieTree.contains("nisan".toCharArray()));
        Assert.assertFalse(FMMSegmentation.trieTree.contains("taing".toCharArray()));
        Assert.assertFalse(FMMSegmentation.trieTree.contains("tidng".toCharArray()));
        Assert.assertFalse(FMMSegmentation.trieTree.contains("tinsg".toCharArray()));
        Assert.assertFalse(FMMSegmentation.trieTree.contains("woaibeijingtiananmen".toCharArray()));
        Assert.assertFalse(FMMSegmentation.trieTree.contains("tiwng".toCharArray()));

    }

    public void testSplit() {

        Assert.assertEquals(FMMSegmentation.split("我爱北京tiananmen"),
                Lists.newArrayList("我", "爱", "北", "京", "tian", "an", "men"));
        Assert.assertEquals(FMMSegmentation.split("三乡青nianejia"),
                Lists.newArrayList("三", "乡", "青", "nian", "e", "jia"));
        Assert.assertEquals(FMMSegmentation.split("fh"),
                Lists.newArrayList("f", "h"));
        Assert.assertEquals(FMMSegmentation.split("heheda"),
                Lists.newArrayList("he", "he", "da"));
        Assert.assertEquals(FMMSegmentation.split("yingoulifanchuan"),
                Lists.newArrayList("ying", "ou", "li", "fan", "chuan"));
        Assert.assertEquals(FMMSegmentation.split("yongangongyu"),
                Lists.newArrayList("yong", "ang", "ong", "yu"));
        Assert.assertEquals(FMMSegmentation.split("changanguojizhongxin"),
                Lists.newArrayList("chang", "ang", "uo", "ji", "zhong", "xin"));
        Assert.assertEquals(FMMSegmentation.split("baoan35quanhuagongyequgonglujusushe"),
                Lists.newArrayList("bao", "an", "35", "quan", "hua", "gong",
                        "ye", "qu", "gong", "lu", "ju", "su", "she"));

        Assert.assertEquals(FMMSegmentation.split("1234567890de0987654321"),
                Lists.newArrayList("1234567890", "de", "0987654321"));

        System.out.println(FMMSegmentation.split("hengange"));
    }
}