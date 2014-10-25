package com.vossie.elasticsearch.annotations.enums;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 10/12/2013
 * Time: 14:21
 */
public enum SettingsFormat {

    JSON {
        @Override
        public String toString() {
            return "json";
        }
    }
}
