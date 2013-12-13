package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.common.ElasticsearchDocumentMetadata;
import com.vossie.elasticsearch.annotations.common.ElasticsearchFieldMetadata;
import com.vossie.elasticsearch.annotations.enums.FieldType;
import com.vossie.elasticsearch.annotations.exceptions.ClassNotAnnotated;
import com.vossie.elasticsearch.annotations.exceptions.InvalidAttributeForType;
import com.vossie.elasticsearch.annotations.exceptions.InvalidParentDocumentSpecified;
import com.vossie.elasticsearch.annotations.exceptions.UnableToLoadConstraints;

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

    private static HashMap<Class<?>, ElasticsearchDocumentMetadata> mappingCache = new HashMap<>();

    public static final String OBJECT_PROPERTIES= "properties";

    /**
     * Get the ElasticsearchDocument annotations for the provided class object.
     * @param clazz The class to inspect.
     * @return The ElasticsearchDocument details
     * @throws ClassNotAnnotated if the annotation is not found.
     */
    private static ElasticsearchDocument getElasticsearchType(Class<?> clazz) throws ClassNotAnnotated {

        ElasticsearchDocument doc = clazz.getAnnotation(ElasticsearchDocument.class);

        if (doc != null)
            return doc;

        throw new ClassNotAnnotated(clazz);
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
     * @throws InvalidAttributeForType If the wrong attribute has been set based on the type of field
     */
    private static Map<String, ElasticsearchFieldMetadata> getElasticsearchFieldsMetadata(Class<?> clazz)
            throws InvalidAttributeForType {

        Map<String, ElasticsearchFieldMetadata> elasticsearchFieldMappings = new HashMap<>();

        if(clazz == null)
            return elasticsearchFieldMappings;

        // get all the declared fields in the class.
        Field[] fields = getAllDeclaredFields(clazz);

        // Iterate over all the annotated fields
        for(Field field : fields) {

            // Get the annotation from the field
            ElasticsearchField elasticsearchField = field.getAnnotation(ElasticsearchField.class);

            // Skip over the field is not annotated
            if(elasticsearchField == null)
                continue;

            ElasticsearchFieldMetadata elasticsearchFieldMetadata;
            boolean isArray = false;
            Class<?> childClass = null;

            if(elasticsearchField.type().equals(FieldType.GEO_POINT) ||
                    elasticsearchField.type().equals(FieldType.OBJECT) ||
                    elasticsearchField.type().equals(FieldType.NESTED)) {

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
            elasticsearchFieldMetadata = new ElasticsearchFieldMetadata(field.getName(), elasticsearchField, isArray, getElasticsearchFieldsMetadata(childClass));

            // Add to the response list
            elasticsearchFieldMappings.put(elasticsearchFieldMetadata.getFieldName(), elasticsearchFieldMetadata);
        }

        return elasticsearchFieldMappings;
    }


    private static Map<String, ElasticsearchFieldMetadata> getElasticsearchSystemFieldsMetadata(ElasticsearchRootField[] systemFields)
            throws InvalidAttributeForType {

        Map<String, ElasticsearchFieldMetadata> elasticsearchFieldMappings = new HashMap<>();

        // Add the system fields
        if(systemFields != null)
            for (ElasticsearchRootField systemField : systemFields){

                ElasticsearchFieldMetadata metadata = new ElasticsearchFieldMetadata(
                        systemField._rootFieldName().toString(),
                        systemField,
                        new HashMap<String,ElasticsearchFieldMetadata>()
                );

                elasticsearchFieldMappings.put(systemField._rootFieldName().toString(), metadata);
            }

        return elasticsearchFieldMappings;
    }

    /**
     * Get the document and field metadata associated with this mapping by class.
     * @param clazz The class to inspect.
     * @return Returns the meta data used to describe this entity.
     * @throws com.vossie.elasticsearch.annotations.exceptions.InvalidParentDocumentSpecified
     * @throws ClassNotAnnotated
     */
    public static ElasticsearchDocumentMetadata get(Class<?> clazz) throws ClassNotAnnotated, InvalidAttributeForType, UnableToLoadConstraints, InvalidParentDocumentSpecified {

        // Check the cache to see if we have already parsed this reference.
        if(mappingCache.containsKey(clazz))
            return mappingCache.get(clazz);

        // Get the annotation.
        ElasticsearchDocument elasticsearchDocument = getElasticsearchType(clazz);

        ElasticsearchDocumentMetadata documentMetadata = new ElasticsearchDocumentMetadata(
                clazz,
                elasticsearchDocument,
                getElasticsearchFieldsMetadata(clazz),
                getElasticsearchSystemFieldsMetadata(elasticsearchDocument._rootFields())
        );

        // Add this item to the local cache for fast lookup.
        mappingCache.put(clazz, documentMetadata );

        // Return the reference.
        return mappingCache.get(clazz);
    }
}
