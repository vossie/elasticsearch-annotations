package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;
import com.vossie.elasticsearch.annotations.enums.ElasticsearchType;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * com.vossie.test.User: cvosloo
 * Date: 06/12/2013
 * Time: 12:28
 */
@ElasticsearchDocument(
        index = "twitter",
        type = "tweet"      /** Optional, if not set it will use the simple class name in a lower hyphenated format */,
        source = true       /** Optional */,
        parent = User.class /** Optional */
)
public class Tweet {

    @ElasticsearchField(
            type = ElasticsearchType.STRING,
            index = "not_analyzed"
    )
    private String user;

    @ElasticsearchField(
            type = ElasticsearchType.DATE,
            format = "YYYY-MM-dd"
    )
    private String postDate;

    @ElasticsearchField(
            type = ElasticsearchType.STRING,
            store = BooleanValue.TRUE,
            index = "analyzed",
            null_value = "na"
    )
    private String message;

    @ElasticsearchField(
            type = ElasticsearchType.BOOLEAN
    )
    private Boolean hes_my_special_tweet;

    @ElasticsearchField(
            type = ElasticsearchType.INTEGER
    )
    private Integer priority;

    @ElasticsearchField(
            type = ElasticsearchType.FLOAT
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
