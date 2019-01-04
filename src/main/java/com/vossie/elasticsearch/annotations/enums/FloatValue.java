package com.vossie.elasticsearch.annotations.enums;

import com.vossie.elasticsearch.annotations.common.Empty;

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
