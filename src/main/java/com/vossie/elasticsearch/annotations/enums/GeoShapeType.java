package com.vossie.elasticsearch.annotations.enums;

/**
 * Created by rpatadia on 03/01/2014.
 */
public enum GeoShapeType {

    POINT {
        @Override
        public String toString() {
            return "point";
        }
    },

    ENVELOPE {
        @Override
        public String toString() {
            return "envelope";
        }
    },

    POLYGON {
        @Override
        public String toString() {
            return "polygon";
        }
    },

    MULTI_POLYGON {
        @Override
        public String toString() {
            return "multipolygon";
        }
    }
}
