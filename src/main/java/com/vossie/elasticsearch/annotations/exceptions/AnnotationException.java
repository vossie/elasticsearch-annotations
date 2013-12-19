package com.vossie.elasticsearch.annotations.exceptions;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 12/12/2013
 * Time: 12:22
 */
public abstract class AnnotationException extends Exception {

    public AnnotationException(){
        super();
    }

    public AnnotationException(String message) {
        super(message);
    }
}
