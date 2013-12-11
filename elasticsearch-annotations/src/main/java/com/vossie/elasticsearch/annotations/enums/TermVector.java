package com.vossie.elasticsearch.annotations.enums;

import com.vossie.elasticsearch.annotations.common.Empty;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * User: cvosloo
 * Date: 10/12/2013
 * Time: 14:07
 */
public enum TermVector {

    NULL {
        @Override
        public String toString() {
            return Empty.NULL;
        }
    },

    NO {
        @Override
        public String toString() {
            return "no";
        }
    },

    YES {
        @Override
        public String toString() {
            return "yes";
        }
    },

    WITH_OFFSETS {
        @Override
        public String toString() {
            return "with_offsets";
        }
    },

    WITH_POSITIONS {
        @Override
        public String toString() {
            return "with_positions";
        }
    },

    WITH_POSITIONS_OFFSETS {
        @Override
        public String toString() {
            return "with_positions_offsets";
        }
    },
}
