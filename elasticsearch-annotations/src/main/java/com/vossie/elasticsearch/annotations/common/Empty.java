package com.vossie.elasticsearch.annotations.common;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Copyright © 2013 Carel Vosloo.
 * Used to identify empty class references in the annotations.
 * User: cvosloo
 * Date: 09/12/2013
 * Time: 21:47
 */
public final class Empty {

    public static final String NULL = "__null";

    public Empty() {
        throw new NotImplementedException();
    }
}
