package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.enums.BooleanNullable;
import com.vossie.elasticsearch.annotations.enums.CoreTypes;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * com.vossie.test.User: cvosloo
 * Date: 06/12/2013
 * Time: 12:32
 */
@ElasticsearchDocument(index = "twitter", source = true, type = "twitterUser")
public class User {

    @ElasticsearchField(type = CoreTypes.STRING, analyzer = "not_analyzed", isDefaultSortByField = BooleanNullable.TRUE)
    private String user;

    @ElasticsearchField(type = CoreTypes.DATE)
    private String dateOfBirth;

    @ElasticsearchField(type = CoreTypes.GEO_POINT)
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
