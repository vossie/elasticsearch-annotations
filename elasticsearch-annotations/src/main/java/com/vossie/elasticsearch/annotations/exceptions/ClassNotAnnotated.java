package com.vossie.elasticsearch.annotations.exceptions;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 11:58
 */
public class ClassNotAnnotated extends Exception {

    public ClassNotAnnotated(Class clazz) {
        super(String.format("The supplied class %s is not annotated with @ElasticsearchType.", clazz.getCanonicalName()));
    }
}
