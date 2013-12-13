package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchRootField;
import com.vossie.elasticsearch.annotations.enums.FieldType;
import com.vossie.elasticsearch.annotations.enums.SystemField;

/**
 * Copyright © 2013 Carel Vosloo.
 * com.vossie.test.User: cvosloo
 * Date: 09/12/2013
 * Time: 21:12
 */
@ElasticsearchDocument(
        index = "test",
        _rootFields = {
                @ElasticsearchRootField(
                        _rootFieldName = SystemField._PARENT,
                        type = Object.class
                )
        }
)
public class InvalidParentTypeAnnotationTestClass {

    @ElasticsearchField(type = FieldType.STRING)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
