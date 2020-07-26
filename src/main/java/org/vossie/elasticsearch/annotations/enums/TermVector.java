package org.vossie.elasticsearch.annotations.enums;

import org.vossie.elasticsearch.annotations.common.Empty;

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
