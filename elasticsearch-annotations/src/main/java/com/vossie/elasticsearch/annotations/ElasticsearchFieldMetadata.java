package com.vossie.elasticsearch.annotations;

import org.elasticsearch.search.sort.SortOrder;

import java.util.Collections;
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
    private ElasticsearchField elasticsearchField;
    private Map<String, ElasticsearchFieldMetadata> children;

    public ElasticsearchFieldMetadata(String fieldName, ElasticsearchField elasticsearchField, boolean isArray, Map<String,ElasticsearchFieldMetadata> children){
        this.fieldName = fieldName;
        this.elasticsearchField = elasticsearchField;
        this.isArray = isArray;
        this.children = Collections.unmodifiableMap(children);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getAnalyzer() {
        return this.elasticsearchField.analyzer();
    }

    public ElasticsearchField.Type getType() {
        return this.elasticsearchField.type();
    }

    public boolean isParentId() {
        return this.elasticsearchField.isParentId();
    }

    public Map<String, ElasticsearchFieldMetadata> getChildren() {
        return children;
    }

    public boolean isArray() {
        return isArray;
    }

    /**
     * Should we use this field as the default sort order for queries if none is specified.
     * @return Boolean
     */
    public boolean isDefaultSortByField() {
        return this.elasticsearchField.isDefaultSortByField();
    }

    /**
     * The default sort order to use if no sort order is specified.
     * @return
     */
    public SortOrder getDefaultSortOrder() {
        return this.elasticsearchField.defaultSortOrder();
    }
}
