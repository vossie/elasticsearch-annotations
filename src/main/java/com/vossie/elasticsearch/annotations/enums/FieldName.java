package com.vossie.elasticsearch.annotations.enums;

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
    }
}
