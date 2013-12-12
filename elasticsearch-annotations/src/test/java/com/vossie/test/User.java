package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;
import com.vossie.elasticsearch.annotations.enums.ElasticsearchType;

import java.util.List;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * com.vossie.test.User: cvosloo
 * Date: 06/12/2013
 * Time: 12:32
 */
@ElasticsearchDocument(index = "twitter", source = true, type = "twitterUser", defaultSortByField = "user")
public class User {

    @ElasticsearchField(
            type = ElasticsearchType.STRING,
            store = BooleanValue.TRUE
    )
    private String user;

    @ElasticsearchField(
            type = ElasticsearchType.DATE,
            format = "dateOptionalTime"
    )
    private String dateOfBirth;

    @ElasticsearchField(
            type = ElasticsearchType.GEO_POINT
    )
    private Location location;

    @ElasticsearchField(
            type = ElasticsearchType.NESTED
    )
    private List<Cities> citiesVisited;

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

    public List<Cities> getCitiesVisited() {
        return citiesVisited;
    }

    public void setCitiesVisited(List<Cities> citiesVisited) {
        this.citiesVisited = citiesVisited;
    }
}
