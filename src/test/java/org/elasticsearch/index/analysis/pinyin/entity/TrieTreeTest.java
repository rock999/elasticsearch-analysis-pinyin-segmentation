package org.elasticsearch.index.analysis.pinyin.entity;

import org.junit.Assert;
import org.junit.Test;

public class TrieTreeTest {

    @Test
    public void testFindEndIndex() throws Exception {
        TrieTree trieTree = new TrieTree();

        trieTree.add("abcd".toCharArray());
        trieTree.add("def".toCharArray());

        Assert.assertEquals(trieTree.findEndIndexInChildren("abcdef".toCharArray(), 0), 4);
        Assert.assertEquals(trieTree.findEndIndexInChildren("x".toCharArray(), 0), 0);
        Assert.assertEquals(trieTree.findEndIndexInChildren("def".toCharArray(), 0), 3);
        Assert.assertEquals(trieTree.findEndIndexInChildren("def".toCharArray(), 1), 1);


    }

    @Test
    public void testContains() throws Exception {
        TrieTree trieTree = new TrieTree();

        String s = "asd";
        Assert.assertFalse(trieTree.contains(s.toCharArray()));
        String s1 = "hello";
        String s2 = "ass";

        trieTree.add(s.toCharArray());
        trieTree.add(s2.toCharArray());
        trieTree.add(s1.toCharArray());
        Assert.assertTrue(trieTree.contains(s.toCharArray()));
        Assert.assertTrue(trieTree.contains(s1.toCharArray()));
        Assert.assertTrue(trieTree.contains(s2.toCharArray()));
        Assert.assertFalse(trieTree.contains("asss".toCharArray()));
        Assert.assertFalse(trieTree.contains("ad".toCharArray()));
        Assert.assertTrue(trieTree.contains("a".toCharArray()));
        Assert.assertTrue(trieTree.contains("as".toCharArray()));

        System.out.println(trieTree);


    }
}