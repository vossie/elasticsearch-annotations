package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;
import com.vossie.elasticsearch.annotations.enums.SystemField;
import com.vossie.elasticsearch.annotations.util.MethodToAttributeNameMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:28
 *
 * Each mapping has a number of fields associated with it which can be used to control how
 * the document metadata (eg _all) is indexed.
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ElasticsearchRootField {

    public SystemField _rootFieldName();


    /**
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return String or ___null if it is not set.
     */
    public String index() default Empty.NULL;

    /**
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return String or ___null if it is not set.
     */
    public String store() default Empty.NULL;

    /**
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return String or ___null if it is not set.
     */
    public String path() default  Empty.NULL;

    /**
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return String or ___null if it is not set.
     */
    public BooleanValue enabled() default BooleanValue.NULL;

    /**
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return String or ___null if it is not set.
     */
    public String name() default Empty.NULL;

    /**
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return String or ___null if it is not set.
     */
    public String null_value() default Empty.NULL;

    /**
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return String or ___null if it is not set.
     */
    public Class<?> type() default Empty.class;

    /**
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return String or ___null if it is not set.
     */
    public BooleanValue required() default BooleanValue.NULL;

    /**
     * Maps to default
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return String or ___null if it is not set.
     */
    @MethodToAttributeNameMapping(mapsTo = "default")
    public String defaultValue() default Empty.NULL;

    public String format() default Empty.NULL;
}
