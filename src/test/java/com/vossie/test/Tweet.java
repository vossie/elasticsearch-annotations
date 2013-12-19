package com.vossie.test;

import com.vossie.elasticsearch.annotations.*;
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
        index = "twitter",
//        type = "tweet"    /** optional, if not set it will use the simple class name in a lower hyphenated format */,
        _elasticsearchFields = {
                @ElasticsearchField(
                        fieldName = FieldName._ID,
                        index = "not_analyzed",
                        store = "yes"
                ),
                @ElasticsearchField(
                        fieldName = FieldName._TYPE,
                        index = "no",
                        store = "yes"
                ),
                @ElasticsearchField(
                    fieldName = FieldName._SOURCE,
                    enabled = BooleanValue.TRUE
                ),
                @ElasticsearchField(
                        fieldName = FieldName._ALL,
                        enabled = BooleanValue.TRUE
                ),
                @ElasticsearchField(
                        fieldName = FieldName._ANALYZER,
                        path = "user"
                ),
                @ElasticsearchField(
                        fieldName = FieldName._BOOST,
                        name = "my_boost",
                        null_value = "1.0"
                ),
                @ElasticsearchField(
                        fieldName = FieldName._PARENT,
                        type = User.class
                ),
                @ElasticsearchField(
                        fieldName = FieldName._ROUTING,
                        required = BooleanValue.FALSE,
                        path = "blog.post_id"
                ),
                @ElasticsearchField(
                        fieldName = FieldName._INDEX,
                        enabled = BooleanValue.FALSE
                ),
                @ElasticsearchField(
                        fieldName = FieldName._SIZE,
                        enabled = BooleanValue.FALSE,
                        store = "yes"
                ),
                @ElasticsearchField(
                        fieldName = FieldName._TIMESTAMP,
                        enabled = BooleanValue.FALSE,
                        path = "post_date",
                        format = "dateOptionalTime"
                ),
                @ElasticsearchField(
                        fieldName = FieldName._TTL,
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
            null_value = "na"
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
