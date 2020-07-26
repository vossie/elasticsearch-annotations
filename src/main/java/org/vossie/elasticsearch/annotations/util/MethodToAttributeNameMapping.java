package org.vossie.elasticsearch.annotations.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * This is a simple annotation to assist when mapping annotated methods to Elasticsearch field names.
 * Example: Elastic uses default as a parameter, default is a restricted name in Java annotations so the annotation
 * method used in ElasticsearchField is defaultValue but it needs to be mapped to default.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodToAttributeNameMapping {

    public String mapsTo();
}
