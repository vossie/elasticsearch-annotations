package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.enums.CoreTypes;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * com.vossie.test.User: cvosloo
 * Date: 09/12/2013
 * Time: 21:12
 */
@ElasticsearchDocument(index = "test", parent = Object.class)
public class InvalidParentTypeAnnotationTestClass {

    @ElasticsearchField(type = CoreTypes.STRING)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
