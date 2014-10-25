package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchIndex;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;
import com.vossie.elasticsearch.annotations.enums.FieldType;

import java.util.List;

/**
 * Copyright © 2013 Carel Vosloo.
 * com.vossie.test.User: cvosloo
 * Date: 06/12/2013
 * Time: 12:32
 */
@ElasticsearchIndex(_indexName = "twitter")
@ElasticsearchDocument()
public class User {

    @ElasticsearchType(
            type = FieldType.STRING,
            store = BooleanValue.TRUE
    )
    private String user;

    @ElasticsearchType(
            type = FieldType.DATE,
            format = "dateOptionalTime"
    )
    private String dateOfBirth;

    @ElasticsearchType(
            type = FieldType.GEO_POINT
    )
    private Location location;

    @ElasticsearchType(
            type = FieldType.NESTED
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
