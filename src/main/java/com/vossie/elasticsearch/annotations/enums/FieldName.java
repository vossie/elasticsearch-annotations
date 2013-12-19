package com.vossie.elasticsearch.annotations.enums;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 12/12/2013
 * Time: 14:14
 */
public enum FieldName {

    _UID {
        @Override
        public String toString() {
            return "_uid";
        }
    },

    _ID {
        @Override
        public String toString() {
            return "_id";
        }
    },

    _TYPE {
        @Override
        public String toString() {
            return "_type";
        }
    },

    _SOURCE {
        @Override
        public String toString() {
            return "_source";
        }
    },

    _ALL {
        @Override
        public String toString() {
            return "_all";
        }
    },

    _ANALYZER {
        @Override
        public String toString() {
            return "_analyzer";
        }
    },

    _BOOST {
        @Override
        public String toString() {
            return "_boost";
        }
    },

    _PARENT {
        @Override
        public String toString() {
            return "_parent";
        }
    },

    _ROUTING {
        @Override
        public String toString() {
            return "_routing";
        }
    },

    _INDEX {
        @Override
        public String toString() {
            return "_index";
        }
    },

    _SIZE {
        @Override
        public String toString() {
            return "_size";
        }
    },

    _TIMESTAMP {
        @Override
        public String toString() {
            return "_timestamp";
        }
    },

    _TTL {
        @Override
        public String toString() {
            return "_ttl";
        }
    }
}
