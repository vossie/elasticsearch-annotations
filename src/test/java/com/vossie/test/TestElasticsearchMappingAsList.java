package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.enums.FieldType;
import com.vossie.models.User;
import org.junit.Test;

import java.util.Map;

public class TestElasticsearchMappingAsList {

    @Test
    public void testFlatListCreation() {

        Map<String,FieldType> out = ElasticsearchMapping.get(User.class).asMap();

        System.out.println(out);
    }
}
