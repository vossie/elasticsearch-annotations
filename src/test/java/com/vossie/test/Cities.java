package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchMultiFieldType;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 11/12/2013
 * Time: 13:24
 */
public class Cities {

    @ElasticsearchType(
            type = FieldType.STRING,
            fields = {
                    @ElasticsearchMultiFieldType(_name = "raw", type = FieldType.STRING, index = "not_analyzed")
            }
    )
    private String name;

    @ElasticsearchType(type = FieldType.GEO_POINT)
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
