package com.vossie.elasticsearch.annotations.enums;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * User: cvosloo
 * Date: 10/12/2013
 * Time: 14:16
 */
public enum BooleanNullable {

    NULL {
        @Override
        public String toString() {
            return "__null";
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
