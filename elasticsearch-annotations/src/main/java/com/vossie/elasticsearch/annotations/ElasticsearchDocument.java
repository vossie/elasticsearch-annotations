package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ElasticsearchDocument {

    /**
     *  The index name.
     * @return
     */
    public String index();

    /**
     * The object type name to index as.
     * @return
     */
    public String type() default Empty.NULL;

    public ElasticsearchRootField[] _rootFields() default {};

    /**
     * Is this a child entity with a parent object.
     * @return
     */
    public Class<?> parent() default Empty.class;

    /**
     * When does the data expire
     * @return
     */
    public String ttl() default Empty.NULL;

    /**
     * Should we store the source data in the index.
     * @return
     */
    public boolean source() default true;

    //TODO: Implement index_analyzer
    public String index_analyzer() default Empty.NULL;

    //TODO: Implement search_analyzer
    public String search_analyzer() default Empty.NULL;

    //TODO: Implement dynamic_date_formats
    public String[] dynamic_date_formats() default Empty.NULL;

    //TODO: Implement date_detection
    public BooleanValue date_detection() default BooleanValue.NULL;

    //TODO: Implement numeric_detection
    public BooleanValue numeric_detection() default BooleanValue.NULL;

    //TODO: Implement dynamic_templates
    public String[] dynamic_templates() default Empty.NULL;
}
