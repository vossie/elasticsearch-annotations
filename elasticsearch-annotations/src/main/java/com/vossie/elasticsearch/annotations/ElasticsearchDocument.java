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
     *  The index name.
     * @return
     */
    public String index();

    /**
     * The object type name to index as.
     * @return
     */
    public String type() default "";

    /**
     * Is this a child entity with a parent object.
     * @return
     */
    public Class<?> parent() default Empty.class;

    /**
     * When does the data expire
     * @return
     */
    public String ttl() default "";

    /**
     * Should we store the source data in the index.
     * @return
     */
    public boolean source() default true;
}
