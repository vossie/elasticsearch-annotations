package com.vossie.elasticsearch.annotations.enums;

import com.vossie.elasticsearch.annotations.common.Empty;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 10/12/2013
 * Time: 14:21
 */
public enum IndexOption {

    NULL {
        @Override
        public String toString() {
            return Empty.NULL;
        }
    },

    DOCS {
        @Override
        public String toString() {
            return "docs";
        }
    },

    FREQS {
        @Override
        public String toString() {
            return "freqs";
        }
    },

    POSITIONS {
        @Override
        public String toString() {
            return "positions";
        }
    },

    OFFSETS {
        @Override
        public String toString() {
            return "offsets";
        }
    }
}
