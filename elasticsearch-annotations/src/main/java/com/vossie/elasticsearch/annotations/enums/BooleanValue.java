package com.vossie.elasticsearch.annotations.enums;

import com.vossie.elasticsearch.annotations.common.Empty;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
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
            return Empty.NULL;
        }
    },

    FALSE {
        @Override
        public String toString() {
            return Empty.NULL;
        }
    }
}
