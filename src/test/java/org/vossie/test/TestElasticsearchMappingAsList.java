package org.vossie.test;

import org.vossie.elasticsearch.annotations.ElasticsearchMapping;
import org.vossie.elasticsearch.annotations.enums.FieldType;
import org.vossie.models.User;
import org.junit.Test;

import java.util.Map;

public class TestElasticsearchMappingAsList {

    @Test
    public void testFlatListCreation() {

        Map<String,FieldType> out = ElasticsearchMapping.get(User.class).asMap();

        System.out.println(out);
    }
}
