package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.common.Empty;

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
     *  The elastic index to use name.
     * @return
     */
    public String index();

    /**
     * The object type name to index as.
     * @return
     */
    public String type() default Empty.NULL;

    /**
     * The system fields.
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return
     */
    public ElasticsearchRootField[] _rootFields() default {};


    //TODO: Implement index_analyzer
//    public String index_analyzer() default Empty.NULL;

    //TODO: Implement search_analyzer
//    public String search_analyzer() default Empty.NULL;

    //TODO: Implement dynamic_date_formats
//    public String[] dynamic_date_formats() default Empty.NULL;

    //TODO: Implement date_detection
//    public BooleanValue date_detection() default BooleanValue.NULL;

    //TODO: Implement numeric_detection
//    public BooleanValue numeric_detection() default BooleanValue.NULL;

    //TODO: Implement dynamic_templates
//    public String[] dynamic_templates() default Empty.NULL;
}
