package com.vossie.elasticsearch.annotations.enums;

import com.vossie.elasticsearch.annotations.common.Empty;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 10/12/2013
 * Time: 14:16
 */
public enum FloatValue {

    NULL {
        @Override
        public String toString() {
            return Empty.NULL;
        }
    },

    DEFAULT {
        @Override
        public String toString() {
            return "1.0f";
        }
    }
}
