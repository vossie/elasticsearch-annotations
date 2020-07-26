package org.vossie.elasticsearch.annotations.enums;

import org.vossie.elasticsearch.annotations.common.Empty;

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
