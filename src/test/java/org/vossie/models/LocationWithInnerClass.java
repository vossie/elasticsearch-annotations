package org.vossie.models;

import org.vossie.elasticsearch.annotations.ElasticsearchDocument;
import org.vossie.elasticsearch.annotations.ElasticsearchField;
import org.vossie.elasticsearch.annotations.ElasticsearchIndex;
import org.vossie.elasticsearch.annotations.ElasticsearchType;
import org.vossie.elasticsearch.annotations.enums.BooleanValue;
import org.vossie.elasticsearch.annotations.enums.FieldName;
import org.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Created by rpatadia on 08/01/2014.
 */
@ElasticsearchIndex(_indexName = "locationWithInnerClass")
@ElasticsearchDocument(
        type = "anyType",
        _elasticsearchFields = {
                @ElasticsearchField(
                        _fieldName = FieldName._SOURCE,
                        enabled = BooleanValue.TRUE
                )
        }
)
public class LocationWithInnerClass {

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


    @ElasticsearchIndex(_indexName = "locationInnerClass")
    @ElasticsearchDocument(
            type = "locationType",
            _elasticsearchFields = {
                    @ElasticsearchField(
                            _fieldName = FieldName._INDEX,
                            enabled = BooleanValue.FALSE
                    ),
                    @ElasticsearchField(
                            _fieldName = FieldName._SIZE,
                            enabled = BooleanValue.FALSE,
                            store = "yes"
                    ),
                    @ElasticsearchField(
                    _fieldName = FieldName._BOOST,
                    name = "my_boost",
                    null_value = "1.0"
                )
            }
    )
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

}
