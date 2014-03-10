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
    public ElasticsearchField[] _elasticsearchFields() default {};
}
