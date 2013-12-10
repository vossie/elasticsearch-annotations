package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.exceptions.ClassNotAnnotated;
import com.vossie.elasticsearch.annotations.exceptions.InvalidParentTypeSpecified;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * User: cvosloo
 * Date: 10/12/2013
 * Time: 10:02
 */
public class MetadataMappingBuilder {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MetadataMappingBuilder.class);
    private static HashMap<String, XContentBuilder> cache = new HashMap<>();

    protected static XContentBuilder getXContentBuilder(ElasticsearchTypeMetadata elasticsearchTypeMetadata) throws ClassNotAnnotated, InvalidParentTypeSpecified {

        String key = String.format("%s-%s", elasticsearchTypeMetadata.getIndexName(), elasticsearchTypeMetadata.getTypeName());

        // Return from cache if it has been previously parsed.
        if(cache.containsKey(key))
            return cache.get(key);

        try {

            /** Set the objects type name */
            XContentBuilder xbMapping = jsonBuilder()
                    .startObject()
                    .startObject(elasticsearchTypeMetadata.getTypeName());

            // Set the object expiry
            if(elasticsearchTypeMetadata.hasTtl()) {
                xbMapping
                        .startObject(ElasticsearchMapping.OBJECT_TTL)
                        .field(ElasticsearchMapping.FIELD_ENABLED, true)
                        .field(ElasticsearchMapping.FIELD_DEFAULT, elasticsearchTypeMetadata.getTtl())
                        .endObject();
            }

            // Test to see if we have a parent child relationship and set accordingly.
            if(elasticsearchTypeMetadata.hasParent()) {
                xbMapping.startObject(ElasticsearchMapping.OBJECT_PARENT).field(ElasticsearchMapping.FIELD_TYPE, elasticsearchTypeMetadata.getParent().getTypeName()).endObject();
            }

            // Set the _source mapping value.
            if(!elasticsearchTypeMetadata.isSourceStoredWithIndex())
                xbMapping.startObject(ElasticsearchMapping.OBJECT_SOURCE).field(ElasticsearchMapping.FIELD_ENABLED, elasticsearchTypeMetadata.isSourceStoredWithIndex()).endObject();

            // Add the fields.
            setXContentBuilderFields(xbMapping, elasticsearchTypeMetadata.getFields());

            // End
            xbMapping
                    .endObject()
                    .endObject();

            // Add to local cache.
            cache.put(key, xbMapping);

            return xbMapping;

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private static void setXContentBuilderFields(XContentBuilder xbMapping, Map<String, ElasticsearchFieldMetadata> fields) throws IOException {

        xbMapping.startObject(ElasticsearchMapping.OBJECT_PROPERTIES);

        // Iterate over all the annotated fields
        for(String fieldName : fields.keySet()) {

            ElasticsearchFieldMetadata elasticsearchField = fields.get(fieldName);

            xbMapping.startObject(elasticsearchField.getFieldName());

            xbMapping.field(ElasticsearchMapping.FIELD_TYPE, elasticsearchField.getType().toString().toLowerCase());

            if(!elasticsearchField.getAnalyzer().equals(""))
                xbMapping.field(ElasticsearchMapping.FIELD_INDEX, elasticsearchField.getAnalyzer());

            if(elasticsearchField.getType().equals(ElasticsearchField.Type.GEO_POINT) || elasticsearchField.getType().equals(ElasticsearchField.Type.OBJECT))
                setXContentBuilderFields(xbMapping, elasticsearchField.getChildren());

            xbMapping.endObject();
        }

        xbMapping.endObject();
    }
}
