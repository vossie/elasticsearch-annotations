package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchIndex;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;
import com.vossie.elasticsearch.annotations.enums.FieldName;
import com.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Created by rpatadia on 09/01/2014.
 */
@ElasticsearchIndex(_indexName = "locationWithLocalClass")
@ElasticsearchDocument(
        type = "anyType",
        _elasticsearchFields = {
                @ElasticsearchField(
                        _fieldName = FieldName._SOURCE,
                        enabled = BooleanValue.TRUE
                )
        }
)
public class LocationWithLocalClass {

    @ElasticsearchType(
            type = FieldType.STRING,
            index = "analyzed"
    )
    private String userName;

    public LocationWithLocalClass (){
        @ElasticsearchIndex(_indexName = "locationLocalClass")
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
        class Location {

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



}
