package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.common.ElasticsearchDocumentMetadata;
import com.vossie.elasticsearch.annotations.common.ElasticsearchIndexMetadata;
import com.vossie.elasticsearch.annotations.common.ElasticsearchNodeMetadata;
import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.FieldType;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:33
 */
public abstract class ElasticsearchMapping {

    private static final HashMap<Class<?>, ElasticsearchDocumentMetadata> mappingCache = new HashMap<>();
    private static final HashMap<String, ElasticsearchIndexMetadata> indexCache = new HashMap<>();

    public static final String OBJECT_PROPERTIES= "properties";

    /**
     * Get the ElasticsearchDocument annotations for the provided class object.
     * @param clazz The class to inspect.
     * @return The ElasticsearchDocument details
     *
     */
    private static ElasticsearchDocument getElasticsearchType(Class<?> clazz) {

        return clazz.getAnnotation(ElasticsearchDocument.class);
    }

    /**
     * Get the ElasticsearchDocument annotations for the provided class object.
     * @param clazz The class to inspect.
     * @return The ElasticsearchDocument details
     *
     */
    private static ElasticsearchIndex getElasticsearchIndex(Class<?> clazz) {

        ElasticsearchIndex elasticsearchIndex = clazz.getAnnotation(ElasticsearchIndex.class);

        if(elasticsearchIndex != null)
            return elasticsearchIndex;

        elasticsearchIndex = clazz.getSuperclass().getAnnotation(ElasticsearchIndex.class);

        return elasticsearchIndex;
    }

    private static Field[] getAllDeclaredFields(Class<?> clazz) {

        // Get all the fields declared in the class
        Field[] fieldsFromClass = clazz.getDeclaredFields();


        // Also get the super class fields to support polymorphism
        Field[] fieldsFromSuper = new Field[0];

        if(clazz.getSuperclass() != null)
            fieldsFromSuper = clazz.getSuperclass().getDeclaredFields();


        // Copy all the fields into a single array.
        Field[] fields = new Field[fieldsFromClass.length + fieldsFromSuper.length];

        if(fieldsFromClass.length > 0)
            System.arraycopy(fieldsFromClass,0,fields,0,fieldsFromClass.length);

        if(fieldsFromSuper.length > 0)
            System.arraycopy(fieldsFromSuper,0,fields,fieldsFromClass.length,fieldsFromSuper.length);

        return fields;
    }

    /**
     * Retrieve the metadata describing the annotated class fields.
     * @param clazz The class to scan
     * @return Returns a list containing the field metadata
     */
    private static Map<String, ElasticsearchNodeMetadata> getElasticsearchFieldsMetadata(Class<?> clazz) {

        Map<String, ElasticsearchNodeMetadata> elasticsearchFieldMappings = new HashMap<>();

        if(clazz == null)
            return elasticsearchFieldMappings;

        // get all the declared fields in the class.
        Field[] fields = getAllDeclaredFields(clazz);

        // Iterate over all the annotated fields
        for(Field field : fields) {

            // Get the annotation from the field
            ElasticsearchType elasticsearchType = field.getAnnotation(ElasticsearchType.class);

            // Skip over the field is not annotated
            if(elasticsearchType == null)
                continue;

            ElasticsearchNodeMetadata elasticsearchNodeMetadata;
            boolean isArray = false;
            Class<?> childClass = null;

            if(elasticsearchType.type().equals(FieldType.GEO_POINT) ||
                    elasticsearchType.type().equals(FieldType.OBJECT) ||
                    elasticsearchType.type().equals(FieldType.NESTED)) {

                // If it is an array we need the component type
                isArray = field.getType().isArray();

                // Get the child class
                childClass = (isArray)
                        ? field.getType().getComponentType()
                        : field.getType();


                if (Collection.class.isAssignableFrom(field.getType())){
                    isArray = true;

                    Type type = field.getGenericType();
                    if (type instanceof ParameterizedType) {

                        ParameterizedType pType = (ParameterizedType)type;
                        Type[] arr = pType.getActualTypeArguments();

                        for (Type tp: arr) {
                            childClass = (Class<?>)tp;
                        }
                    }
                }
            }

            // Set the children
            elasticsearchNodeMetadata = new ElasticsearchNodeMetadata(field.getName(), elasticsearchType, isArray, getElasticsearchFieldsMetadata(childClass));

            // Add to the response list
            elasticsearchFieldMappings.put(elasticsearchNodeMetadata.getFieldName(), elasticsearchNodeMetadata);
        }

        return elasticsearchFieldMappings;
    }


    private static Map<String, ElasticsearchNodeMetadata> getElasticsearchSystemFieldsMetadata(ElasticsearchField[] systemFields) {

        Map<String, ElasticsearchNodeMetadata> elasticsearchFieldMappings = new HashMap<>();

        // Add the system fields
        if(systemFields != null)
            for (ElasticsearchField systemField : systemFields){

                ElasticsearchNodeMetadata metadata = new ElasticsearchNodeMetadata(
                        systemField._fieldName().toString(),
                        systemField,
                        new HashMap<String,ElasticsearchNodeMetadata>()
                );

                elasticsearchFieldMappings.put(systemField._fieldName().toString(), metadata);
            }

        return elasticsearchFieldMappings;
    }

    /**
     * Get the document and field metadata associated with this mapping by class.
     * @param clazz The class to inspect.
     * @return Returns the meta data used to describe this entity.
     */
    public static ElasticsearchDocumentMetadata get(Class<?> clazz) {

        // Check the cache to see if we have already parsed this reference.
        if(mappingCache.containsKey(clazz))
            return mappingCache.get(clazz);

        // Get the annotation.
        ElasticsearchDocument elasticsearchDocument = getElasticsearchType(clazz);

        if(elasticsearchDocument == null)
            return null;

        // If a class is provided to get the index information from then use that else try to see if the current class has the ES index annotation set.
        ElasticsearchIndexMetadata elasticsearchIndexMetadata = (elasticsearchDocument.index().isAssignableFrom(Empty.class))
                ? getIndex(clazz)
                : getIndex(elasticsearchDocument.index());

        if(elasticsearchIndexMetadata == null) {
            throw new RuntimeException("No ElasticsearchIndex annotation found containing the index information for " + elasticsearchDocument.type());
        }

        ElasticsearchDocumentMetadata documentMetadata = new ElasticsearchDocumentMetadata(
                clazz,
                elasticsearchDocument,
                getElasticsearchFieldsMetadata(clazz),
                getElasticsearchSystemFieldsMetadata(elasticsearchDocument._elasticsearchFields()),
                elasticsearchIndexMetadata
        );

        // Add this item to the local cache for fast lookup.
        mappingCache.put(clazz, documentMetadata );

        // Return the reference.
        return mappingCache.get(clazz);
    }

    /**
     * Get the document and field metadata associated with this mapping by class.
     * @param clazz The class to inspect.
     * @return Returns the meta data used to describe this entity.
     */
    public static ElasticsearchIndexMetadata getIndex(Class<?> clazz) {

        // Get the annotation.
        ElasticsearchIndexMetadata elasticsearchIndex = new ElasticsearchIndexMetadata(clazz, getElasticsearchIndex(clazz));

        if(elasticsearchIndex == null)
            return null;

        // Add this item to the local cache for fast lookup.
        if(!indexCache.containsKey(elasticsearchIndex.getIndexName()))
            indexCache.put(elasticsearchIndex.getIndexName(), elasticsearchIndex );

        // Return the reference.
        return elasticsearchIndex;
    }
}
