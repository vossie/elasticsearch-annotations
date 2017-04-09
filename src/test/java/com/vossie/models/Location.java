package com.vossie.models;

import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Copyright © 2013 Carel Vosloo.
 * com.vossie.models.User: cvosloo
 * Date: 06/12/2013
 * Time: 12:36
 */
public class Location {

    @ElasticsearchType(type = FieldType.DOUBLE)
    private double lat;

    @ElasticsearchType(type = FieldType.DOUBLE)
    private double lon;

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public Location setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Location setLon(double lon) {
        this.lon = lon;
        return this;
    }
}
