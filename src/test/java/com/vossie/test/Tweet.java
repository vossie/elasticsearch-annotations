package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchMultiFieldType;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;
import com.vossie.elasticsearch.annotations.enums.FieldName;
import com.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Copyright © 2013 Carel Vosloo.
 * com.vossie.test.User: cvosloo
 * Date: 06/12/2013
 * Time: 12:28
 */
@ElasticsearchDocument /** required */(
        index = TweetIndex.class,
//      type = "tweet"    /** optional, if not set it will use the simple class name in a lower hyphenated format */,
        _elasticsearchFields = {
                @ElasticsearchField(
                        _fieldName = FieldName._ID,
                        index = "not_analyzed",
                        store = "yes"
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._TYPE,
                        index = "no",
                        store = "yes"
                ),
                @ElasticsearchField(
                    _fieldName = FieldName._SOURCE,
                    enabled = BooleanValue.TRUE
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._ALL,
                        enabled = BooleanValue.TRUE
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._ANALYZER,
                        path = "user"
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._BOOST,
                        name = "my_boost",
                        null_value = "1.0"
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._PARENT,
                        type = User.class
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._ROUTING,
                        required = BooleanValue.FALSE,
                        path = "blog.post_id"
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._INDEX,
                        enabled = BooleanValue.FALSE
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._SIZE,
                        enabled = BooleanValue.FALSE,
                        store = "yes"
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._TIMESTAMP,
                        enabled = BooleanValue.FALSE,
                        path = "post_date",
                        format = "dateOptionalTime"
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._TTL,
                        enabled = BooleanValue.TRUE,
                        defaultValue = "1d"
                )
        }
)
public class Tweet {

    @ElasticsearchType(
            type = FieldType.STRING,
            index = "not_analyzed"
    )
    private String user;

    @ElasticsearchType(
            type = FieldType.DATE,
            format = "YYYY-MM-dd"
    )
    private String postDate;

    @ElasticsearchType(
            type = FieldType.STRING,
            store = BooleanValue.TRUE,
            index = "analyzed",
            null_value = "na",
            fields = {
                    @ElasticsearchMultiFieldType(_name = "raw", index = "not_analyzed", type = FieldType.STRING)
            }
    )
    private String message;

    @ElasticsearchType(
            type = FieldType.BOOLEAN
    )
    private Boolean hes_my_special_tweet;

    @ElasticsearchType(
            type = FieldType.INTEGER
    )
    private Integer priority;

    @ElasticsearchType(
            type = FieldType.FLOAT
    )
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
