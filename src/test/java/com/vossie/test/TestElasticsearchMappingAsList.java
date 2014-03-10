package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.enums.FieldType;
import org.junit.Test;

import java.util.Map;

/**
 * Copyright © 2013 Carel Vosloo.
 * com.vossie.test.User: cvosloo
 * Date: 09/12/2013
 * Time: 21:00
 */
public class TestElasticsearchMappingAsList {

    @Test
    public void testFlatListCreation() {

        Map<String,FieldType> out = ElasticsearchMapping.get(User.class).asMap();

        System.out.println(out);
    }
}
