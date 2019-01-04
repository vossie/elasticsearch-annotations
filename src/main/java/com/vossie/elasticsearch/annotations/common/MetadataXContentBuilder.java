package com.vossie.elasticsearch.annotations.common;

import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.ElasticsearchMultiFieldType;
import com.vossie.elasticsearch.annotations.enums.FieldName;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public final class MetadataXContentBuilder {

    private static HashMap<String, XContentBuilder> cache = new HashMap<>();

    protected static XContentBuilder getXContentBuilder(ElasticsearchDocumentMetadata elasticsearchDocumentMetadata) {

        String key = String.format("%s-%s", elasticsearchDocumentMetadata.getIndexName(), elasticsearchDocumentMetadata.getTypeName());

        // Return from cache if it has been previously parsed.
        if(cache.containsKey(key))
            return cache.get(key);

        try {

            /** Set the objects type name */
            XContentBuilder xbMapping = jsonBuilder()
                    .startObject()
                    .startObject(elasticsearchDocumentMetadata.getTypeName());


            for(String fieldName : elasticsearchDocumentMetadata.getFieldNames()) {

                ElasticsearchNodeMetadata field = elasticsearchDocumentMetadata.getFieldMetaData(fieldName);
                xbMapping.startObject(field.getFieldName());

                for(String attributeName : field.getAttributes().keySet()) {

                    if(fieldName.equals(FieldName._PARENT.toString()) && attributeName.equals("type")) {

                        xbMapping.field(
                                attributeName,
                                elasticsearchDocumentMetadata.getParent().getTypeName()
                        );
                    }
                    else if(field.getAttributes().get(attributeName).getClass().isArray()) {
                        xbMapping.field(attributeName,field.getAttributes().get(attributeName));
                    }
                    else
                        xbMapping.field(attributeName,field.getAttributes().get(attributeName).toString());
                }

                xbMapping.endObject();
            }

            // Add the fields.
            setXContentBuilderFields(xbMapping, elasticsearchDocumentMetadata.getProperties());

            // End
            xbMapping
                    .endObject()
                    .endObject();

            // Add to local cache.
            cache.put(key, xbMapping);

            return xbMapping;

        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    /**
     * Populate the child field nodes.
     * @param xbMapping The content builder to use.
     * @param fields The fields to append
     */
    private static void setXContentBuilderFields(XContentBuilder xbMapping, Map<String, ElasticsearchNodeMetadata> fields) {

        if(fields.keySet().size() < 1)
            return;

        try {
            xbMapping.startObject(ElasticsearchMapping.OBJECT_PROPERTIES);

            // Iterate over all the annotated fields
            for(String fieldName : fields.keySet()) {

                ElasticsearchNodeMetadata elasticsearchField = fields.get(fieldName);

                xbMapping.startObject(elasticsearchField.getFieldName());
                    setFields(elasticsearchField, xbMapping);
                    setXContentBuilderFields(xbMapping, elasticsearchField.getChildren());
                xbMapping.endObject();
            }

            xbMapping.endObject();

        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }

    private static void setFields(ElasticsearchNodeMetadata elasticsearchField, XContentBuilder xbMapping) throws IOException {

        for(String attribute : elasticsearchField.getAttributes().keySet()) {
            Object values = elasticsearchField.getAttributes().get(attribute);

            if(ElasticsearchMultiFieldType[].class.isAssignableFrom(values.getClass())) {

                xbMapping.startObject(attribute);
                for(ElasticsearchMultiFieldType fieldType:  (ElasticsearchMultiFieldType[]) values) {

                    xbMapping.startObject(fieldType._name());
                    setElasticsearchMultiFieldType(fieldType, xbMapping);
                    xbMapping.endObject();
                }
                xbMapping.endObject();
            }
            else {
                xbMapping.field(attribute, elasticsearchField.getAttributes().get(attribute));
            }
        }
    }

    private static void setElasticsearchMultiFieldType(ElasticsearchMultiFieldType fieldType, XContentBuilder xbMapping) throws IOException {

        if(!fieldType.index().equals(Empty.NULL))
            xbMapping.field("index", fieldType.index());

        if(!fieldType.type().equals(Empty.NULL))
            xbMapping.field("type", fieldType.type());

        if(!fieldType.analyzer().equals(Empty.NULL))
            xbMapping.field("analyzer", fieldType.analyzer());

        if(!fieldType.normalizer().equals(Empty.NULL))
            xbMapping.field("normalizer", fieldType.normalizer());
    }
}
