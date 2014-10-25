package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.common.Empty;

import java.lang.annotation.*;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ElasticsearchDocument {

    /**
     *  The class reference to the location of the ElasticsearchIndex annotation
     *  containing the index information.
     * @return
     */
    public Class<?> index() default Empty.class;

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
