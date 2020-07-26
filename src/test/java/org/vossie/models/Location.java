package org.vossie.models;

import org.vossie.elasticsearch.annotations.ElasticsearchType;
import org.vossie.elasticsearch.annotations.enums.FieldType;

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
