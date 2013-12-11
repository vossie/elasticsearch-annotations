package com.vossie.elasticsearch.annotations.common;

import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.exceptions.ClassNotAnnotated;
import com.vossie.elasticsearch.annotations.exceptions.InvalidParentDocumentSpecified;
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
public class MetadataXContentBuilder {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MetadataXContentBuilder.class);
    private static HashMap<String, XContentBuilder> cache = new HashMap<>();

    protected static XContentBuilder getXContentBuilder(ElasticsearchDocumentMetadata elasticsearchDocumentMetadata) throws ClassNotAnnotated, InvalidParentDocumentSpecified {

        String key = String.format("%s-%s", elasticsearchDocumentMetadata.getIndexName(), elasticsearchDocumentMetadata.getTypeName());

        // Return from cache if it has been previously parsed.
        if(cache.containsKey(key))
            return cache.get(key);

        try {

            /** Set the objects type name */
            XContentBuilder xbMapping = jsonBuilder()
                    .startObject()
                    .startObject(elasticsearchDocumentMetadata.getTypeName());

            // Set the object expiry
            if(elasticsearchDocumentMetadata.hasTtl()) {
                xbMapping
                        .startObject(ElasticsearchMapping.OBJECT_TTL)
                        .field(ElasticsearchMapping.FIELD_ENABLED, true)
                        .field(ElasticsearchMapping.FIELD_DEFAULT, elasticsearchDocumentMetadata.getTtl())
                        .endObject();
            }

            // Test to see if we have a parent child relationship and set accordingly.
            if(elasticsearchDocumentMetadata.hasParent()) {
                xbMapping.startObject(ElasticsearchMapping.OBJECT_PARENT).field(ElasticsearchMapping.FIELD_TYPE, elasticsearchDocumentMetadata.getParent().getTypeName()).endObject();
            }

            // Set the _source mapping value.
            if(!elasticsearchDocumentMetadata.isSourceStoredWithIndex())
                xbMapping.startObject(ElasticsearchMapping.OBJECT_SOURCE).field(ElasticsearchMapping.FIELD_ENABLED, elasticsearchDocumentMetadata.isSourceStoredWithIndex()).endObject();

            // Add the fields.
            setXContentBuilderFields(xbMapping, elasticsearchDocumentMetadata.getFields());

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

        if(fields.keySet().size() < 1)
            return;

        xbMapping.startObject(ElasticsearchMapping.OBJECT_PROPERTIES);

        // Iterate over all the annotated fields
        for(String fieldName : fields.keySet()) {

            ElasticsearchFieldMetadata elasticsearchField = fields.get(fieldName);

            xbMapping.startObject(elasticsearchField.getFieldName());

            for(String attribute : elasticsearchField.getAttributes().keySet()) {
                xbMapping.field(attribute, elasticsearchField.getAttributes().get(attribute));
            }

            setXContentBuilderFields(xbMapping, elasticsearchField.getChildren());

            xbMapping.endObject();
        }

        xbMapping.endObject();
    }
}
