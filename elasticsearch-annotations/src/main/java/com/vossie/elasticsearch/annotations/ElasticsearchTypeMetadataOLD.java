package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.exceptions.ClassNotAnnotated;
import com.vossie.elasticsearch.annotations.exceptions.InvalidParentTypeSpecified;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:34
 */
public class ElasticsearchTypeMetadataOLD {

    private Class clazz;
    private XContentBuilder xContentBuilder;
    private ElasticsearchType elasticsearchType;
    private Map<String,ElasticsearchFieldMetadataTemp> elasticsearchFields = new HashMap<>();

//    public ElasticsearchTypeMetadataOLD(Class clazz, XContentBuilder xContentBuilder, ElasticsearchType elasticsearchType) {
//        this(clazz, xContentBuilder, elasticsearchType, null);
//    }

    public ElasticsearchTypeMetadataOLD(Class clazz, XContentBuilder xContentBuilder, ElasticsearchType elasticsearchType, ElasticsearchFieldMetadataTemp... elasticsearchFieldMetadataTemp) {

        this.elasticsearchType = elasticsearchType;
        this.xContentBuilder = xContentBuilder;
        this.clazz = clazz;

        if(elasticsearchFieldMetadataTemp != null)
            for(ElasticsearchFieldMetadataTemp field : elasticsearchFieldMetadataTemp)
                this.elasticsearchFields.put(field.getFieldName(), field);
    }

    public Map<String, ElasticsearchFieldMetadataTemp> getElasticsearchFields() {
        return elasticsearchFields;
    }

    public ElasticsearchTypeMetadataOLD putAllElasticsearchFields(List<ElasticsearchFieldMetadataTemp> fields) {

        for(ElasticsearchFieldMetadataTemp field : fields)
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

        if(this.elasticsearchType.type().equals(""))
            return this.clazz.getSimpleName().toLowerCase();

        return this.elasticsearchType.type();
    }

    /**
     * Get the parent type name
     * @return Parent type name.
     */
    public ElasticsearchTypeMetadataOLD getParent() throws ClassNotAnnotated, InvalidParentTypeSpecified {
        return ElasticsearchMapping.getMapping(this.elasticsearchType.parent());
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
    public Map<String, ElasticsearchFieldMetadataTemp> getFields() {
        return this.elasticsearchFields;
    }

    /**
     * Get the metadata associated with a specific field.
     * @param fieldName The name of the field to retrieve.
     * @return The meta data if found otherwise null.
     */
    public ElasticsearchFieldMetadataTemp getFieldMetaData(String fieldName) {

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
