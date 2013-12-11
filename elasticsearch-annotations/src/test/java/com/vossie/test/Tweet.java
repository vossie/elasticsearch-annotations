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
@ElasticsearchDocument(index = "twitter", source = true, parent = User.class, defaultSortByField = "postDate")
public class Tweet {

    @ElasticsearchField(type = ElasticsearchType.STRING, index = "not_analyzed", isParentId = BooleanValue.TRUE)
    private String user;

    @ElasticsearchField(type = ElasticsearchType.DATE)
    private String postDate;

    @ElasticsearchField(type = ElasticsearchType.STRING)
    private String message;

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
}
