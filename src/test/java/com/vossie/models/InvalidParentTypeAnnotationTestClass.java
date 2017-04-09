package com.vossie.models;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchIndex;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.enums.FieldName;
import com.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Copyright © 2013 Carel Vosloo.
 * com.vossie.models.User: cvosloo
 * Date: 09/12/2013
 * Time: 21:12
 */
@ElasticsearchIndex(_indexName = "test")
@ElasticsearchDocument(
        _elasticsearchFields = {
                @ElasticsearchField(
                        _fieldName = FieldName._PARENT,
                        type = Object.class
                )
        }
)
public class InvalidParentTypeAnnotationTestClass {

    @ElasticsearchType(type = FieldType.KEYWORD)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
