package com.vossie.models;

import com.vossie.elasticsearch.annotations.ElasticsearchMultiFieldType;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.FieldType;

public class Cities {

    @ElasticsearchType( type = FieldType.TEXT, fields = @ElasticsearchMultiFieldType(_name = "raw", type = FieldType.KEYWORD))
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
