package org.elasticsearch.index.analysis.pinyin.entity;

import org.elasticsearch.common.collect.Lists;
import org.junit.Test;

import java.util.List;

public class TokenEntityTest {

    @Test
    public void testWrap() throws Exception {

        List<String> ts = Lists.newArrayList(
                "12", "sss", "sdf", "sxf"
        );

        System.out.println(TokenEntity.wrap(ts));

    }
}