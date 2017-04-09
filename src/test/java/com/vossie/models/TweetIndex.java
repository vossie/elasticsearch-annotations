package com.vossie.models;

import com.vossie.elasticsearch.annotations.ElasticsearchIndex;

/**
 * Copyright (c) 2014 Carel Vosloo <code@bronzegate.com>
 * All rights reserved. No warranty, explicit or implicit, provided.
 * Created: 24/10/14 21:13 by carel
 */
@ElasticsearchIndex(
        /** https://gist.github.com/clintongormley/780895
         *  http://gibrown.com/2013/04/17/mapping-wordpress-posts-to-elasticsearch/ */
        _indexName = "twitter1",

        settings = "{\n" +
                "    \"analysis\": {\n" +
                "        \"filter\": {\n" +
                "            \"stop_filter\": {\n" +
                "                \"type\": \"stop\",\n" +
                "                \"stopwords\": [\n" +
                "                    \"_english_\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"stemmer_filter\": {\n" +
                "                \"type\": \"stemmer\",\n" +
                "                \"name\": \"minimal_english\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"analyzer\": {\n" +
                "            \"html_analyzer\": {\n" +
                "                \"type\": \"custom\",\n" +
                "                \"tokenizer\": \"uax_url_email\",\n" +
                "                \"filter\": [\n" +
                "                    \"lowercase\",\n" +
                "                    \"stop_filter\",\n" +
                "                    \"stemmer_filter\"\n" +
                "                ],\n" +
                "                \"char_filter\": [\n" +
                "                    \"html_strip\"\n" +
                "                ]\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}"
)
public class TweetIndex {

}
