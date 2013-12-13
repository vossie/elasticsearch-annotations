package com.vossie.elasticsearch.annotations.enums;

import com.vossie.elasticsearch.annotations.common.Empty;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 10/12/2013
 * Time: 14:13
 */
public enum  PostingsFormat {

    NULL {
        @Override
        public String toString() {
            return Empty.NULL;
        }
    },

    DEFAULT {
        @Override
        public String toString() {
            return "default";
        }
    },

    DIRECT {
        @Override
        public String toString() {
            return "direct";
        }
    },

    MEMORY {
        @Override
        public String toString() {
            return "memory";
        }
    },

    PULSING {
        @Override
        public String toString() {
            return "pulsing";
        }
    },

    BLOOM_DEFAULT {
        @Override
        public String toString() {
            return "bloom_default";
        }
    },

    BLOOM_PULSING {
        @Override
        public String toString() {
            return "bloom_pulsing";
        }
    }
}
