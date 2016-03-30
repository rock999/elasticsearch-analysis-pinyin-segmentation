Extended Pinyin Analysis for ElasticSearch
========================================

The Extended Pinyin Analysis plugin fork from elasticsearch-analysis-pinyin(https://github.com/medcl/elasticsearch-analysis-pinyin).
And now only supports ES v1.6

##This extension aims to solve the following problems in production env.

 * 对于中文字段, 要支持中文的搜索 比如 刘德华 => 刘德华
 * 对于中文字段, 要支持拼音的搜索 比如 liudehua => 刘德华
 * 对于中文字段, 要支持中文加拼音的搜索 比如 刘dehua => 刘德华
 * 对于中文字段, 要支持拼音首字母的搜索 比如 ldh => 刘德华
 * 输入的拼音可能包含分隔符, 比如 liu de hua 或 liu'de'hua

##Solutions
  * 建索引时候 中文字段要建立 中文 + 拼音 + 拼音首字母 的索引 如 刘德华 => 刘德华 liudehua liu de hua
  * 查询时 中文要切分成 中文 + 拼音 + 拼音首字母 的查询 刘德华 => 刘德华 liudehua liu de hua
  * 查询时 拼音 先按照分隔符来分 如 liu'de'hua => liu de hua
  * 再按照音节来分隔 如 dehua => de hua
  * 对于拼音里可能出现的歧义(ambiguity) 都要分隔出来 如 yingou => yin gou ying ou
  * 对于 ambiguity 先不考虑采用 最有可能 的拆分, 而是把所有的可能性都列出来

##Design
考虑到拼音 可能与别的分词器结合 (比如 ik) 所以放在tokenizer filter 里实现.


## How to use it?

Add the following code into {ES_dir}/conf/elasticsearch.yml

    index:
      analysis:
        analyzer:
          pinyin_analyzer:
            tokenizer: standard
            filter: [pinyin_segment]
        filter:
          pinyin_segment:
            type: pinyin_segment

specify the analyzer in index mapping, for example

    curl -XPOST http://localhost:9200/medcl/folks/_mapping -d'
    {
        "folks": {
            "properties": {
                "name": {
                    "type": "string",
                    "analyzer": "pinyin_analyzer",
                    "search_analyzer": "pinyin_analyzer",
                    "index_analyzer": "pinyin_analyzer"
                }
            }
        }
    }'

Restart ES
Get done


