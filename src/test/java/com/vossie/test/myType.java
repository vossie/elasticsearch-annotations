package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.FieldName;
import com.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Created by rpatadia on 16/12/2013.
 */
@ElasticsearchDocument(
        index = "my-index",
        type = "my_type",
        _elasticsearchFields = {
                @ElasticsearchField(
                        fieldName = FieldName._SOURCE,
                        includes = {
                                "path1.*", "path2.*"
                        },
                        excludes = {
                                "pat3.*"
                        }
                ),
                @ElasticsearchField(
                        fieldName = FieldName._SOURCE,
                        includes = {
                                "path1.*", "path2.*"
                        },
                        excludes = {
                                "pat3.*"
                        }
                )
        }
)
public class myType {

    @ElasticsearchType(type = FieldType.STRING)
    private String myValue;
}
