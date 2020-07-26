package org.vossie.models;

import org.vossie.elasticsearch.annotations.ElasticsearchDocument;
import org.vossie.elasticsearch.annotations.ElasticsearchField;
import org.vossie.elasticsearch.annotations.ElasticsearchIndex;
import org.vossie.elasticsearch.annotations.ElasticsearchType;
import org.vossie.elasticsearch.annotations.enums.BooleanValue;
import org.vossie.elasticsearch.annotations.enums.FieldName;
import org.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Created by rpatadia on 09/01/2014.
 */
@ElasticsearchIndex(_indexName = "locationWithStaticInnerClass")
@ElasticsearchDocument(
        type = "anyType",
        _elasticsearchFields = {
                @ElasticsearchField(
                        _fieldName = FieldName._SOURCE,
                        enabled = BooleanValue.TRUE
                )
        }
)
public class LocationWithStaticInnerClass {
    @ElasticsearchType(
            type = FieldType.KEYWORD,
            index = BooleanValue.TRUE
    )
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @ElasticsearchIndex(_indexName = "locationStaticInnerClass")
    @ElasticsearchDocument(
            type = "locationType",
            _elasticsearchFields = {
                    @ElasticsearchField(
                            _fieldName = FieldName._INDEX,
                            enabled = BooleanValue.TRUE
                    ),
                    @ElasticsearchField(
                            _fieldName = FieldName._BOOST,
                            name = "my_boost",
                            null_value = "1.0"
                    )
            }
    )
    public static class Location {

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

}
