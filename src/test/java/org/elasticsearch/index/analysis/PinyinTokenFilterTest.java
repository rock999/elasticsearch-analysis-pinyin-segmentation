package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
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

        Set<String> pinyin = getTokens("广'中'苑");

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
        int position = 0;
        while (filter.incrementToken()) {
            CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
            OffsetAttribute oa = filter.getAttribute(OffsetAttribute.class);
            PositionIncrementAttribute pa = filter.getAttribute(PositionIncrementAttribute.class);
            System.out.println(ta);
            System.out.println(oa.startOffset() + "_" + oa.endOffset());
            System.out.println(position += pa.getPositionIncrement());
            System.out.println("**************");
            pinyin.add(ta.toString());
        }
        return pinyin;
    }


}