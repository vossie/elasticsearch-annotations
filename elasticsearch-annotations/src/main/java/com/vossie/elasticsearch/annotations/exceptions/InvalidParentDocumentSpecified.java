package com.vossie.elasticsearch.annotations.exceptions;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 11:58
 */
public class InvalidParentDocumentSpecified extends AnnotationException {

    public InvalidParentDocumentSpecified(Class clazz, String childIndex, String parentIndex) {
        super(String.format("The supplied class %s is indexed to %s and it specified parent index is %s.", clazz.getCanonicalName(), childIndex, parentIndex));
    }
}
