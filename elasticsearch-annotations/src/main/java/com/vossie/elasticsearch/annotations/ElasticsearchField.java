package com.vossie.elasticsearch.annotations;

import org.elasticsearch.search.sort.SortOrder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ElasticsearchField {

    /**
     * The index analysis module acts as a configurable registry of Analyzers that can be used in order to both break
     * indexed (analyzed) fields when a document is indexed and process query strings. It maps to the Lucene Analyzer.
     * @return The name of the analyzer to use.
     */
    public String analyzer() default "";

    /**
     * The type of data stored in this field.
     * @return The type of data to be indexed.
     */
    public Type type();

    /**
     * Does this field contain a reference key to the parent object.
     * @return Boolean
     */
    public boolean isParentId() default false;

    /**
     * Should we use this field as the default sort order for queries if none is specified.
     * @return Boolean
     */
    public boolean isDefaultSortByField() default false;

    /**
     * The default sort order to use if no sort order is specified.
     * @return
     */
    public SortOrder defaultSortOrder() default SortOrder.ASC;

    /**
     * The types of data we can index by.
     */
    public enum Type {

        STRING,
        LONG,
        INTEGER,
        FLOAT,
        DOUBLE,
        BOOLEAN,
        OBJECT,
        DATE,
        GEO_POINT /** geo_point */
    }
}
