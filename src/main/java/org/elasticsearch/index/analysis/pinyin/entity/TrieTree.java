package org.elasticsearch.index.analysis.pinyin.entity;


/**
 * The root node of the tree that contains no value
 */
public class TrieTree extends TreeNode {

    public boolean add(char[] string) {
        if (string == null) return false;

        return addChild(string, 0);
    }

    public boolean contains(char[] string) {
        if (string == null || string.length == 0) return false;
        return containsStringInChildren(string, 0);
    }


}


