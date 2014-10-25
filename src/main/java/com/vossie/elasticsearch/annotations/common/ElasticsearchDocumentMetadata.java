package com.vossie.elasticsearch.annotations.common;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.enums.FieldName;
import com.vossie.elasticsearch.annotations.enums.FieldType;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:34
 */
public class ElasticsearchDocumentMetadata {

    private final String typeName;
    private final ElasticsearchDocument elasticsearchDocument;
    private final Map<String,ElasticsearchNodeMetadata> elasticsearchTypes;
    private final Map<String,ElasticsearchNodeMetadata> elasticsearchFields;
    private final Map<String, Object> attributes;
    private final ElasticsearchIndexMetadata elasticsearchIndex;

    public ElasticsearchDocumentMetadata(Class<?> clazz, ElasticsearchDocument elasticsearchDocument, Map<String, ElasticsearchNodeMetadata> elasticsearchTypes, Map<String, ElasticsearchNodeMetadata> elasticsearchFields, ElasticsearchIndexMetadata elasticsearchIndex) {

        // Set the type name
        this.typeName = (elasticsearchDocument.type().equals(Empty.NULL))
                ? UPPER_CAMEL.to(LOWER_HYPHEN, clazz.getSimpleName().toLowerCase())
                : elasticsearchDocument.type();

        this.elasticsearchDocument = elasticsearchDocument;
        this.elasticsearchTypes = Collections.unmodifiableMap(elasticsearchTypes);
        this.elasticsearchFields = Collections.unmodifiableMap(elasticsearchFields);

        // Todo: Find a way of doing this without the spring dependency.
        this.attributes = Collections.unmodifiableMap(AnnotationUtils.getAnnotationAttributes(elasticsearchDocument));

        this.elasticsearchIndex = elasticsearchIndex;
    }

    public Map<String, ElasticsearchNodeMetadata> getElasticsearchProperties() {
        return elasticsearchTypes;
    }

    /**
     * Get the index name.
     * @return index name
     */
    public String getIndexName(){
        return this.elasticsearchDocument.index();
    }

    /**
     * Get the type name for this index mapping.
     * If it is not set the class name will be changed from upper camel case to lower hyphen case.
     * @return
     */
    public String getTypeName() {
        return this.typeName;
    }

    /**
     * Get the parent
     * @return Parent
     */
    public ElasticsearchDocumentMetadata getParent()  {

        Class<?> parentClass = (this.elasticsearchFields.get(FieldName._PARENT.toString()).getAttributes().containsKey("type"))
                    ?(Class<?>) this.elasticsearchFields.get(FieldName._PARENT.toString()).getAttributes().get("type")
                    : null;

        if(parentClass != null)
            return ElasticsearchMapping.get(parentClass);

        return null;
    }


    public boolean hasParent() {
        return (this.elasticsearchFields.containsKey(FieldName._PARENT.toString()));
    }


    /**
     * Should we store the source data in the index.
     * The _source field is an automatically generated field that stores the actual JSON that was used as the indexed document. It is not indexed (searchable), just stored. When executing "fetch" requests, like get or search, the _source field is returned by default.
     *
     * Though very handy to have around, the source field does incur storage overhead within the index. For this reason, it can be disabled. For example:
     * @return True if the source is stored.
     */
    public boolean isSourceStoredWithIndex() {
        return (this.elasticsearchFields.containsKey(FieldName._SOURCE.toString()))
                ? Boolean.valueOf(this.elasticsearchFields.get(FieldName._SOURCE.toString()).getAttributes().get("enabled").toString())
                : true;
    }

    /**
     * Get a list of field names.
     * @return
     */
    public String[] getPropertyNames() {
        return this.getElasticsearchProperties().keySet().toArray(new String[this.getElasticsearchProperties().size()]);
    }

    /**
     * Get all the fields in this mapping.
     * @return Map of indexed fields.
     */
    public Map<String, ElasticsearchNodeMetadata> getProperties() {
        return this.elasticsearchTypes;
    }

    /**
     * Get the metadata associated with a specific field.
     * @param fieldName The name of the field to retrieve.
     * @return The meta data if found otherwise null.
     */
    public ElasticsearchNodeMetadata getPropertyMetaData(String fieldName) {

        return (this.elasticsearchTypes.containsKey(fieldName))
                ? this.elasticsearchTypes.get(fieldName)
                : null;
    }

    public ElasticsearchIndexMetadata getElasticsearchIndex() {
        return elasticsearchIndex;
    }

    /**
     * Get a list of field names.
     * @return
     */
    public String[] getFieldNames() {
        return this.getFields().keySet().toArray(new String[this.getFields().size()]);
    }

    /**
     * Get all the fields in this mapping.
     * @return Map of indexed fields.
     */
    public Map<String, ElasticsearchNodeMetadata> getFields() {
        return this.elasticsearchFields;
    }

    /**
     * Get the metadata associated with a specific field.
     * @param fieldName The name of the field to retrieve.
     * @return The meta data if found otherwise null.
     */
    public ElasticsearchNodeMetadata getFieldMetaData(String fieldName) {

        if(this.elasticsearchFields.containsKey(fieldName))
            return this.elasticsearchFields.get(fieldName);

        return null;
    }

    /**
     * Get a json formatted string representing the mapping;
     * @return The mapping.
     */
    @Override
    public String toString() {
        try {
            return toMapping();
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
    public String toMapping() throws IOException {

        return MetadataXContentBuilder.getXContentBuilder(this).string();
    }

    private Map<String,FieldType> _allAsFlatList;
    public Map<String,FieldType> asMap(){

        if(_allAsFlatList != null)
            return _allAsFlatList;

        Map<String,FieldType> response = new HashMap<>();

        response.put(getTypeName(),FieldType.OBJECT);


        for (String field : this.getElasticsearchProperties().keySet()) {


            ElasticsearchNodeMetadata nodeMetadata = this.getElasticsearchProperties().get(field);

            String prefix=getTypeName() +"."+ nodeMetadata.getFieldName();
            response.put(prefix, (FieldType) nodeMetadata.getAttributes().get("type"));

            loopThroughFields(response,prefix,nodeMetadata);
        }

        _allAsFlatList=response;
        return _allAsFlatList;
    }

    private Map<String,FieldType> loopThroughFields(Map<String,FieldType> list, String preFix, ElasticsearchNodeMetadata nodeMetadata){

        if(list==null || list.size()<1)
            return list;

        for (String field : nodeMetadata.getChildren().keySet()) {

            ElasticsearchNodeMetadata n = nodeMetadata.getChildren().get(field);
            String current = preFix + "." + field;
            list.put(current, (FieldType) n.getAttributes().get("type"));
            loopThroughFields(list, current, n);
        }

        return list;
    }
}
