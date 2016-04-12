package org.elasticsearch.index.analysis.pinyin.utils;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.index.analysis.pinyin.entity.TrieTree;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FMMSegmentation {

    private static final ESLogger logger = Loggers.getLogger(FMMSegmentation.class);
    static final TrieTree trieTree = new TrieTree();

    static {
        try (
             InputStream fis = Thread.currentThread()
                     .getContextClassLoader()
                     .getResourceAsStream("spell.txt");
             InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
             BufferedReader br = new BufferedReader(isr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                trieTree.add(line.toCharArray());
            }
        } catch (IOException e) {
            logger.error("Load spell error {}", e);
        }
    }

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
    public static List<String> split(String s) {
        List<String> tokenResult = Lists.newArrayList();

        int beginIndex = 0;
        int endIndex = 0;

        char[] string = s.toCharArray();
        while (endIndex < s.length()) {
            endIndex = trieTree.findEndIndexInChildren(string, beginIndex);
            if (beginIndex < endIndex) {
                StringBuilder sb = new StringBuilder();
                for (int i = beginIndex; i < endIndex; i++) {
                    sb.append(string[i]);
                }
                tokenResult.add(sb.toString());
                beginIndex = endIndex;
            } else {
                tokenResult.add(String.valueOf(string[beginIndex]));
                beginIndex++;
                endIndex++;
            }
        }
        return tokenResult;
    }

}
