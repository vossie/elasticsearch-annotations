package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchIndex;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.FieldType;
import com.vossie.elasticsearch.annotations.enums.TermVector;

/**
 * Copyright © 2013 Carel Vosloo.
 * com.vossie.test.User: cvosloo
 * Date: 06/12/2013
 * Time: 12:32
 */
@ElasticsearchIndex(_indexName = "twitter")
@ElasticsearchDocument(type = "twitterUser")
public class UserWithInvalidAttribute {

    @ElasticsearchType(type = FieldType.STRING, index = "not_analyzed")
    private String user;

    // Term vector is not valid for FieldType.DATE type.
    @ElasticsearchType(type = FieldType.DATE, term_vector = TermVector.NO)
    private String dateOfBirth;

    @ElasticsearchType(type = FieldType.GEO_POINT)
    private Location location;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
