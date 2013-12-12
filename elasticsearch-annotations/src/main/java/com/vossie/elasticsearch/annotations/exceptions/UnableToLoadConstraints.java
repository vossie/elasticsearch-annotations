package com.vossie.elasticsearch.annotations.exceptions;

import java.io.IOException;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * User: cvosloo
 * Date: 12/12/2013
 * Time: 12:24
 */
public class UnableToLoadConstraints extends AnnotationException {

    public UnableToLoadConstraints(IOException e) {
        super(e.getMessage());
    }
}
