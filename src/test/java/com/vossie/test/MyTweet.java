package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;
import com.vossie.elasticsearch.annotations.enums.FieldName;
import com.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Created by rpatadia on 08/01/2014.
 */
@ElasticsearchDocument(
        index = TweetIndex.class,
        type = "tweet",
        _elasticsearchFields = {
                @ElasticsearchField(
                        _fieldName = FieldName._INDEX,
                        enabled = BooleanValue.TRUE
                )
        }
)
public class MyTweet extends Tweet {
    @ElasticsearchType(
            type = FieldType.STRING,
            index = "analyzed"
    )
    private String myMessage;

    public String getUser() {
        return myMessage;
    }

    public void setUser(String myMessage) {
        this.myMessage = myMessage;
    }
}
