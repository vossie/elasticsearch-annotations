package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;
import com.vossie.elasticsearch.annotations.enums.FieldName;
import com.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Created by rpatadia on 16/12/2013.
 */
@ElasticsearchDocument(
        index = "company",
        _elasticsearchFields = {
                @ElasticsearchField(
                        fieldName = FieldName._SOURCE,
                        enabled = BooleanValue.FALSE,
                        includes = {
                                "path1.*", "path2.*"
                        },
                        excludes = {
                                "pat3.*"
                        },
                        path = "someString"
                )
        }
)
public class Employee {

    @ElasticsearchType(type = FieldType.STRING, index = "not_analyzed")
    private String firstName;

    private String lastName;

    private String joinDate;

    @ElasticsearchType(type = FieldType.OBJECT)
    private Location location;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public Location getLocation() {
        return location;
    }
}