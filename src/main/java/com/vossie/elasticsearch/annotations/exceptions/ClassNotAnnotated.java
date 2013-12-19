package com.vossie.elasticsearch.annotations.exceptions;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 11:58
 */
public class ClassNotAnnotated extends AnnotationException {

    public ClassNotAnnotated(Class clazz) {
        super(String.format("The supplied class %s is not annotated with @ElasticsearchDocument.", clazz.getCanonicalName()));
    }
}
