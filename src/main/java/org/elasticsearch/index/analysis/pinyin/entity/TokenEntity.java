package org.elasticsearch.index.analysis.pinyin.entity;

import org.elasticsearch.common.collect.Lists;

import java.util.List;

/**
 * Token class that contains necessary information such as token value, begin/end offset, relative position and so on
 */
public class TokenEntity {

    String value;
    int beginOffset;
    int endOffset;
    // zero based, the first element with relativePosition of value 0
    int relativePosition;

    public TokenEntity duplicate() {
        return new TokenEntity()
                .setBeginOffset(beginOffset)
                .setEndOffset(endOffset)
                .setRelativePosition(relativePosition)
                .setValue(value);
    }

    public String getValue() {
        return value;
    }

    public TokenEntity setValue(String value) {
        this.value = value;
        return this;
    }

    public int getBeginOffset() {
        return beginOffset;
    }

    public TokenEntity setBeginOffset(int beginOffset) {
        this.beginOffset = beginOffset;
        return this;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public TokenEntity setEndOffset(int endOffset) {
        this.endOffset = endOffset;
        return this;
    }

    public int getRelativePosition() {
        return relativePosition;
    }

    public TokenEntity setRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
        return this;
    }

    @Override
    public String toString() {
        return org.elasticsearch.common.base.MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("beginOffset", beginOffset)
                .add("endOffset", endOffset)
                .add("relativePosition", relativePosition)
                .toString();
    }

    public static List<TokenEntity> wrap(List<String> tokens) {

        if (tokens == null || tokens.size() == 0) {
            return Lists.newArrayList();
        }
        List<TokenEntity> result = Lists.newArrayList();

        int position = 0;
        int beginOffset = 0;
        for (String str : tokens) {
            result.add(new TokenEntity()
                    .setBeginOffset(beginOffset)
                    .setEndOffset(beginOffset += str.length())
                    .setRelativePosition(position++)
                    .setValue(str));
        }
        return result;
    }
}
