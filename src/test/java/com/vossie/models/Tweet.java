package com.vossie.models;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchMultiFieldType;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;
import com.vossie.elasticsearch.annotations.enums.FieldName;
import com.vossie.elasticsearch.annotations.enums.FieldType;


@ElasticsearchDocument /** required */(
        index = TweetIndex.class,
//      type = "tweet"    /** optional, if not set it will use the simple class name in a lower hyphenated format */,
        _elasticsearchFields =  @ElasticsearchField( _fieldName = FieldName._SOURCE, enabled = BooleanValue.TRUE)
)
public class Tweet {

    @ElasticsearchType( type = FieldType.KEYWORD, index = BooleanValue.FALSE /* Changing all index values to false for testing. */)
    private String user;

    @ElasticsearchType( type = FieldType.DATE, format = "YYYY-MM-dd")
    private String postDate;

    @ElasticsearchType( type = FieldType.TEXT, store = BooleanValue.TRUE, index = BooleanValue.FALSE, fields = @ElasticsearchMultiFieldType(_name = "raw", index = BooleanValue.FALSE, type = FieldType.KEYWORD) )
    private String message;

    @ElasticsearchType( type = FieldType.BOOLEAN)
    private Boolean hes_my_special_tweet;

    @ElasticsearchType( type = FieldType.INTEGER)
    private Integer priority;

    @ElasticsearchType(type = FieldType.FLOAT)
    private Float rank;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getHes_my_special_tweet() {
        return hes_my_special_tweet;
    }

    public void setHes_my_special_tweet(Boolean hes_my_special_tweet) {
        this.hes_my_special_tweet = hes_my_special_tweet;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Float getRank() {
        return rank;
    }

    public void setRank(Float rank) {
        this.rank = rank;
    }
}
