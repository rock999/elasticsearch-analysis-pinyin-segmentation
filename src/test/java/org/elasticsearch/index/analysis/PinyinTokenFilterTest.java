package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

public class PinyinTokenFilterTest {

    // Use Lucene standard tokenizer as the tokenizer


    @Test
    public void testTokenFilter() throws IOException {

        Set<String> pinyin = getTokens("yingou里翻船");

        System.out.println("pinyin result:");
        System.out.println(pinyin);

//        Assert.assertEquals(
//                Sets.newHashSet("liu", "de", "hua", "刘", "德", "华"),
//                pinyin);
    }

    private Set<String> getTokens(String string) throws IOException {
        StringReader sr = new StringReader(string);
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_41);
        PinyinTokenFilter pinyinTokenFilter = new PinyinTokenFilter(analyzer.tokenStream("f",sr));
        PinyinSegmentationTokenFilter segmentationTokenFilter =
                new PinyinSegmentationTokenFilter(pinyinTokenFilter);
        Set<String> pinyin= new HashSet<String>();
        TokenFilter filter = segmentationTokenFilter;
        filter.reset();
        while (filter.incrementToken()) {
            CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
            pinyin.add(ta.toString());
        }
        return pinyin;
    }


}