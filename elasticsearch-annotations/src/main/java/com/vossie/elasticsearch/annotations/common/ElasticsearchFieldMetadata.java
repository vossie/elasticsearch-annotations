package com.vossie.elasticsearch.annotations.common;

import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.ElasticsearchRootField;
import com.vossie.elasticsearch.annotations.enums.SystemField;
import com.vossie.elasticsearch.annotations.exceptions.*;
import com.vossie.elasticsearch.annotations.util.AttributeNameHelper;
import com.vossie.elasticsearch.annotations.util.ESTypeAttributeConstraints;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:36
 */
public class ElasticsearchFieldMetadata {

    private boolean isArray;
    private String fieldName;
    private Map<String, Object> attributes;
    private ElasticsearchField elasticsearchField;
    private ElasticsearchRootField elasticsearchRootField;
    private Map<String, ElasticsearchFieldMetadata> children;

    public ElasticsearchFieldMetadata(String fieldName, ElasticsearchField elasticsearchField, boolean isArray,
                                      Map<String,ElasticsearchFieldMetadata> children) throws InvalidAttributeForType {

        this.isArray = isArray;
        this.fieldName = fieldName;
        this.elasticsearchField = elasticsearchField;
        this.children = Collections.unmodifiableMap(children);

        setAttributes(this.elasticsearchField.type().toString(), this.elasticsearchField);
    }

    public ElasticsearchFieldMetadata(String fieldName, ElasticsearchRootField elasticsearchRootFieldField,
                                      Map<String,ElasticsearchFieldMetadata> children) throws InvalidAttributeForType {

        this.isArray = isArray;
        this.fieldName = fieldName;
        this.children = Collections.unmodifiableMap(children);
        this.elasticsearchRootField = elasticsearchRootFieldField;

        setAttributes(this.elasticsearchRootField._rootFieldName().toString(), this.elasticsearchRootField);
    }

    private void setAttributes(String typeName, Annotation annotation) throws InvalidAttributeForType {

        // Todo: Find a way of doing this without the spring dependency.
        Map<String, Object> allAttributes = Collections.unmodifiableMap(AnnotationUtils.getAnnotationAttributes(annotation));

        ESTypeAttributeConstraints constraints = new ESTypeAttributeConstraints();
        Map<String, Object> tempAttributes = new HashMap<>();

        for(String key : allAttributes.keySet()) {

            String attributeName = AttributeNameHelper.getAttributeName(annotation, key);
            Object value = allAttributes.get(key);

            if(value.toString().equals(Empty.NULL))
                continue;

            if(value.toString().equals("0") || value.toString().equals("0.0"))
                continue;

            if(value.toString().equals(Empty.class.toString()))
                continue;

            else if(!constraints.isValidAttributeForType(typeName, attributeName)) {

                if(!constraints.getAttributeNames().contains(attributeName))
                    continue;

                throw new InvalidAttributeForType(typeName,attributeName, annotation.getClass().getCanonicalName());
            }

            if(typeName.equals(SystemField._PARENT.toString()) && attributeName.equals("type")) {
                try {
                    tempAttributes.put(attributeName, ElasticsearchMapping.getProperties((Class<?>) value));
                } catch (AnnotationException e) {
                    e.printStackTrace();
                }
            }
            else {
                tempAttributes.put(attributeName, value);
            }
        }


        this.attributes = Collections.unmodifiableMap(tempAttributes);
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Map<String, ElasticsearchFieldMetadata> getChildren() {
        return this.children;
    }

    public boolean isArray() {
        return this.isArray;
    }
}
