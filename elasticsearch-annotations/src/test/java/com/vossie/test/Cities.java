package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchFieldProperties;
import com.vossie.elasticsearch.annotations.enums.ElasticsearchType;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * User: cvosloo
 * Date: 11/12/2013
 * Time: 13:24
 */
public class Cities {

    @ElasticsearchFieldProperties(type = ElasticsearchType.STRING)
    private String name;

    @ElasticsearchFieldProperties(type = ElasticsearchType.GEO_POINT)
    private Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
