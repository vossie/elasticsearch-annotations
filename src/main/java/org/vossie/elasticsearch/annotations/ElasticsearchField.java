package org.vossie.elasticsearch.annotations;

import org.vossie.elasticsearch.annotations.common.Empty;
import org.vossie.elasticsearch.annotations.enums.BooleanValue;
import org.vossie.elasticsearch.annotations.enums.FieldName;
import org.vossie.elasticsearch.annotations.util.MethodToAttributeNameMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Each mapping has a number of fields associated with it which can be used to control how
 * the document metadata (eg _all) is indexed.
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ElasticsearchField {

    public FieldName _fieldName();


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
     * This is to support use in Scala annotations
     * @return String or ___null if it is not set.
     */
    public String typeAsString() default Empty.NULL;

    /**
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return String or ___null if it is not set.
     */
    public BooleanValue required() default BooleanValue.NULL;

    /**
     * Allow to specify paths in the source that would be included when it’s stored, supporting * as wildcard annotation
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-source-field.html
     * @return
     */
    public String[] includes() default {};

    /**
     * Allow to specify paths in the source that would be excluded when it’s stored, supporting * as wildcard annotation
     * @return
     */
    public String[] excludes() default {};

    /**
     * https://www.elastic.co/guide/en/elasticsearch/reference/5.6/normalizer.html
     * @return String or ___null if it is not set.
     */
    public String normalizer() default Empty.NULL;

    /**
     * Maps to default
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return String or ___null if it is not set.
     */
    @MethodToAttributeNameMapping(mapsTo = "default")
    public String defaultValue() default Empty.NULL;

    public String format() default Empty.NULL;
}
