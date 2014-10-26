package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.enums.SettingsFormat;

import java.lang.annotation.*;

/**
 * Copyright (c) 2014 Carel Vosloo <code@bronzegate.com>
 * All rights reserved. No warranty, explicit or implicit, provided.
 * Created: 24/10/14 20:29 by carel
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ElasticsearchIndex {

    /**
     *  The name of this index.
     * @return
     */
    public String _indexName();

    /**
     * The Elasticsearch index settings.
     * @return
     */
    public String settings() default "";

    /**
     * The format that the settings are stored in.
     * In future we will add URL, file, etc.
     * @return
     */
    public SettingsFormat settingsFormat() default SettingsFormat.JSON;

}
