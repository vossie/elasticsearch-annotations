package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.common.Empty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
     * Deprecated and will be removed in next version
     * @return
     */
    @Deprecated
    public String type() default Empty.NULL;

    /**
     * The system fields.
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
     * @return
     */
    public ElasticsearchField[] _elasticsearchFields() default {};
}
