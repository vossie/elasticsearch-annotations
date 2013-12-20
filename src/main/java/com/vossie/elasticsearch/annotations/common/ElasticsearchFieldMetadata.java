package com.vossie.elasticsearch.annotations.common;

import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
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
    private ElasticsearchType elasticsearchType;
    private ElasticsearchField elasticsearchField;
    private Map<String, ElasticsearchFieldMetadata> children;

    public ElasticsearchFieldMetadata(String fieldName, ElasticsearchType elasticsearchType, boolean isArray,
                                      Map<String,ElasticsearchFieldMetadata> children)  {

        this.isArray = isArray;
        this.fieldName = fieldName;
        this.elasticsearchType = elasticsearchType;
        this.children = Collections.unmodifiableMap(children);

        setAttributes(this.elasticsearchType.type().toString(), this.elasticsearchType);
    }

    public ElasticsearchFieldMetadata(String fieldName, ElasticsearchField elasticsearchFieldField,
                                      Map<String,ElasticsearchFieldMetadata> children)  {

        this.fieldName = fieldName;
        this.children = Collections.unmodifiableMap(children);
        this.elasticsearchField = elasticsearchFieldField;

        setAttributes(this.elasticsearchField.fieldName().toString(), this.elasticsearchField);
    }

    private void setAttributes(String typeName, Annotation annotation)  {

        // Todo: Find a way of doing this without the spring dependency.
        Map<String, Object> allAttributes = Collections.unmodifiableMap(AnnotationUtils.getAnnotationAttributes(annotation));

        ESTypeAttributeConstraints constraints = new ESTypeAttributeConstraints();
        Map<String, Object> tempAttributes = new HashMap<>();

        for(String key : allAttributes.keySet()) {

            String attributeName = AttributeNameHelper.getAttributeName(annotation, key);
            Object value = allAttributes.get(key);

            if(value.toString().equals(Empty.NULL))
                continue;

            if(value.getClass().isArray() && ((Object[]) value).length == 0)
                continue;

            if(value.toString().equals("0") || value.toString().equals("0.0"))
                continue;

            if(value.toString().equals(Empty.class.toString()))
                continue;

            else if(!constraints.isValidAttributeForType(typeName, attributeName)) {

                if(!constraints.getAttributeNames().contains(attributeName))
                    continue;

//                throw new InvalidAttributeForType(typeName,attributeName, annotation.getClass().getCanonicalName());
            }

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

    public Map<String, ElasticsearchFieldMetadata> getChildren() {
        return this.children;
    }

    public boolean isArray() {
        return this.isArray;
    }
}
