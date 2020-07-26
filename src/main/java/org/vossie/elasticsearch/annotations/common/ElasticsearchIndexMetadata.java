package org.vossie.elasticsearch.annotations.common;

import org.vossie.elasticsearch.annotations.ElasticsearchIndex;
import org.vossie.elasticsearch.annotations.enums.SettingsFormat;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

public class ElasticsearchIndexMetadata {

    public ElasticsearchIndexMetadata(Class<?> clazz, ElasticsearchIndex elasticsearchIndex) {

        this.clazz = clazz;
        this.indexName = (elasticsearchIndex ==null) ? null : elasticsearchIndex._indexName();
        this.settings = (elasticsearchIndex ==null) ? null : loadSettings(elasticsearchIndex.settingsFormat(), elasticsearchIndex.settings());
    }

    private final String indexName;

    private final Settings.Builder settings;

    private final Class<?> clazz;

    public Class<?> getClazz() {
        return clazz;
    }

    public String getIndexName() {
        return indexName;
    }

    public Settings.Builder getSettings() {
        return settings;
    }

    public boolean hasSettings() {
        return (getSettings() != null);
    }

    private Settings.Builder loadSettings(SettingsFormat settingsFormat, String source) {

        if(source == null || source.isEmpty())
            return null;

        switch (settingsFormat) {
            case JSON: {
                return Settings.builder().loadFromSource(source, XContentType.JSON);
            }
        }

        return null;
    }
}
