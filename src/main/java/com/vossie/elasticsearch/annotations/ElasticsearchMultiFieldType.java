package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.*;

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
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/0.90/mapping-multi-field-type.html
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/_multi_fields.html
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.LOCAL_VARIABLE)
public @interface ElasticsearchMultiFieldType {

    /**
     * The system field for the name of this sub-field.
     * Example:
     *
     * Source;
     * {@ElasticsearchMultiFieldType(_name = "raw", type = FieldType.STRING, index = "not_analyzed")}
     *
     * Result;
     * "fields": {
     *  "raw":   { "type": "string", "index": "not_analyzed" }
     *  }
     * @return
     */
    public String _name();

    /**
     * The type of field this is.
     * @return
     */
    public FieldType type();

    /**
     * Set to analyzed for the field to be indexed and searchable after being broken down into token using an analyzer.
     * not_analyzed means that its still searchable, but does not go through any analysis process or broken down into
     * tokens. no means that it won’t be searchable at all (as an individual field; it may still be included in _all).
     * Setting to no disables include_in_all. Defaults to analyzed.
     * @return
     */
    public String index() default Empty.NULL;
}
