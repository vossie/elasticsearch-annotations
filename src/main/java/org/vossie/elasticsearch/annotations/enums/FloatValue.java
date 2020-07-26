package org.vossie.elasticsearch.annotations.enums;

import org.vossie.elasticsearch.annotations.common.Empty;

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
