package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.enums.SettingsFormat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
