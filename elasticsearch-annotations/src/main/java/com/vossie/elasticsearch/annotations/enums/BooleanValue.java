package com.vossie.elasticsearch.annotations.enums;

import com.vossie.elasticsearch.annotations.common.Empty;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 10/12/2013
 * Time: 14:16
 */
public enum BooleanValue {

    NULL {
        @Override
        public String toString() {
            return Empty.NULL;
        }
    },

    TRUE {
        @Override
        public String toString() {
            return "true";
        }
    },

    FALSE {
        @Override
        public String toString() {
            return "false";
        }
    }
}
