package com.vossie.elasticsearch.annotations.enums;

public enum SettingsFormat {

    JSON {
        @Override
        public String toString() {
            return "json";
        }
    }
}
