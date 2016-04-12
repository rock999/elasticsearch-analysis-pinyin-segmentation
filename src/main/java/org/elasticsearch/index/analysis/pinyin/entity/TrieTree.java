package org.elasticsearch.index.analysis.pinyin.entity;


import org.elasticsearch.common.collect.Maps;

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


    /**
     * add a tree node whose children are all itself with the key be number from 0 to 9, inclusive
     * in order to present a number (may be with infinite length)
     */
    public void addNumberNode() {
        TreeNode numberNode = new TreeNode();
        numberNode.children = Maps.newHashMap();
        numberNode.endpoint = true;
        for (char i = '0'; i <= '9'; i++) {
            numberNode.children.put(i, numberNode);
            children.put(i, numberNode);
        }
    }
}


