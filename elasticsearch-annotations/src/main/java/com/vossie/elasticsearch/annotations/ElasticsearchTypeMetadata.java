package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.exceptions.ClassNotAnnotated;
import com.vossie.elasticsearch.annotations.exceptions.InvalidParentTypeSpecified;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:34
 */
public class ElasticsearchTypeMetadata {

    private XContentBuilder xContentBuilder;
    private ElasticsearchType elasticsearchType;
    private Map<String,ElasticsearchFieldMetadata> elasticsearchFields;

    public ElasticsearchTypeMetadata(ElasticsearchType elasticsearchType, Map<String, ElasticsearchFieldMetadata> elasticsearchFields) throws ClassNotAnnotated, InvalidParentTypeSpecified {
        this.elasticsearchType = elasticsearchType;
        this.elasticsearchFields = Collections.unmodifiableMap(elasticsearchFields);
        this.xContentBuilder = MetadataMappingBuilder.getXContentBuilder(this);
    }

    public Map<String, ElasticsearchFieldMetadata> getElasticsearchFields() {
        return elasticsearchFields;
    }

    public ElasticsearchTypeMetadata putAllElasticsearchFields(List<ElasticsearchFieldMetadata> fields) {

        for(ElasticsearchFieldMetadata field : fields)
            this.elasticsearchFields.put(field.getFieldName(), field);

        return this;
    }

    /**
     * Get the index name.
     * @return index name
     */
    public String getIndexName(){
        return this.elasticsearchType.index();
    }

    /**
     * Get the type name for this index mapping.
     * @return
     */
    public String getTypeName() {
        return this.elasticsearchType.type();
    }

    /**
     * Get the parent type name
     * @return Parent type name.
     */
    public ElasticsearchTypeMetadata getParent() throws ClassNotAnnotated, InvalidParentTypeSpecified {
        return ElasticsearchMapping.getMapping(this.elasticsearchType.parent());
    }

    public boolean hasParent() {
        return (!elasticsearchType.parent().getName().equals(TopLevelType.class.getName()));
    }

    public boolean isChildType() {
        return (!this.elasticsearchType.parent().equals(""));
    }

    /**
     * The time after which the indexed data will expire
     * @return The time to live
     */
    public String getTtl(){
        return this.elasticsearchType.ttl();
    }

    /**
     * Does this document have an expires after time.
     * @return True if the indexed item expires after a defined period.
     */
    public boolean hasTtl() {
        return (!this.elasticsearchType.ttl().equals(""));
    }

    /**
     * Should we store the source data in the index.
     * The _source field is an automatically generated field that stores the actual JSON that was used as the indexed document. It is not indexed (searchable), just stored. When executing "fetch" requests, like get or search, the _source field is returned by default.
     *
     * Though very handy to have around, the source field does incur storage overhead within the index. For this reason, it can be disabled. For example:
     * @return True if the source is stored.
     */
    public boolean isSourceStoredWithIndex() {
        return this.elasticsearchType.source();
    }

    /**
     * Get a list of field names.
     * @return
     */
    public String[] getFieldNames() {
        return this.getElasticsearchFields().keySet().toArray(new String[this.getElasticsearchFields().size()]);
    }

    /**
     * Get all the fields in this mapping.
     * @return Map of indexed fields.
     */
    public Map<String, ElasticsearchFieldMetadata> getFields() {
        return this.elasticsearchFields;
    }

    /**
     * Get the metadata associated with a specific field.
     * @param fieldName The name of the field to retrieve.
     * @return The meta data if found otherwise null.
     */
    public ElasticsearchFieldMetadata getFieldMetaData(String fieldName) {

        if(this.elasticsearchFields.containsKey(fieldName))
            return this.elasticsearchFields.get(fieldName);

        return null;
    }

    /**
     * Get a json formatted string representing the mapping;
     * @return The mapping.
     */
    public String toString() {
        try {
            return this.xContentBuilder.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a json formatted string representing the mapping;
     * @return The mapping.
     * @throws IOException
     */
    public String toJson() throws IOException {
        return this.xContentBuilder.string();
    }
}
