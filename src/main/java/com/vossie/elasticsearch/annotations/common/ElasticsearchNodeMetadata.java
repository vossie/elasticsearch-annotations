package com.vossie.elasticsearch.annotations.common;

import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.util.AttributeNameHelper;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ElasticsearchNodeMetadata {

    private boolean isArray;
    private String fieldName;
    private Map<String, Object> attributes;
    private ElasticsearchType elasticsearchType;
    private ElasticsearchField elasticsearchField;
    private Map<String, ElasticsearchNodeMetadata> children;

    public ElasticsearchNodeMetadata(String fieldName, ElasticsearchType elasticsearchType, boolean isArray,
                                     Map<String, ElasticsearchNodeMetadata> children)  {

        this.isArray = isArray;
        this.fieldName = fieldName;
        this.elasticsearchType = elasticsearchType;
        this.children = Collections.unmodifiableMap(children);

        setAttributes(this.elasticsearchType);
    }

    public ElasticsearchNodeMetadata(String fieldName, ElasticsearchField elasticsearchFieldField,
                                     Map<String, ElasticsearchNodeMetadata> children)  {

        this.fieldName = fieldName;
        this.children = Collections.unmodifiableMap(children);
        this.elasticsearchField = elasticsearchFieldField;

        setAttributes(this.elasticsearchField);
    }

    private void setAttributes( Annotation annotation)  {

        Map<String, Object> allAttributes = AnnotationUtils.getAnnotationAttributes(annotation);

        Map<String, Object> tempAttributes = new HashMap<>();

        for(String key : allAttributes.keySet()) {

            String attributeName = AttributeNameHelper.getAttributeName(annotation, key);
            Object value = allAttributes.get(key);

            if(key.toString() == "_fieldName")
                continue;

            if (value.getClass().isArray()){
                if (((Object[]) value).length ==0)
                    continue;
            }

            if(value.toString().equals(Empty.NULL))
                continue;

            if(value.toString().equals("0") || value.toString().equals("0.0"))
                continue;

            if(value.toString().equals(Empty.class.toString()))
                continue;

            tempAttributes.put(attributeName, value);
        }

        this.attributes = Collections.unmodifiableMap(tempAttributes);
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Map<String, ElasticsearchNodeMetadata> getChildren() {
        return this.children;
    }

    public boolean isArray() {
        return this.isArray;
    }
}
