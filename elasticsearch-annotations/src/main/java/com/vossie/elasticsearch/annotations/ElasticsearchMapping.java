package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.common.ElasticsearchDocumentMetadata;
import com.vossie.elasticsearch.annotations.common.ElasticsearchFieldMetadata;
import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.ElasticsearchType;
import com.vossie.elasticsearch.annotations.exceptions.ClassNotAnnotated;
import com.vossie.elasticsearch.annotations.exceptions.InvalidAttributeForType;
import com.vossie.elasticsearch.annotations.exceptions.InvalidParentDocumentSpecified;
import com.vossie.elasticsearch.annotations.exceptions.UnableToLoadConstraints;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ElasticsearchMapping.class);
    private static HashMap<Class<?>, ElasticsearchDocumentMetadata> mappingCache = new HashMap<>();

    public static final String OBJECT_TTL       = "_ttl";
    public static final String OBJECT_PARENT    = "_parent";
    public static final String OBJECT_SOURCE    = "_source";

    public static final String OBJECT_PROPERTIES= "properties";

    public static final String FIELD_TYPE       = "type";
    public static final String FIELD_ENABLED    = "enabled";
    public static final String FIELD_DEFAULT    = "default";

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
            ElasticsearchFieldProperties elasticsearchFieldProperties = field.getAnnotation(ElasticsearchFieldProperties.class);

            // Skip over the field is not annotated
            if(elasticsearchFieldProperties == null)
                continue;

            ElasticsearchFieldMetadata elasticsearchFieldMetadata;
            boolean isArray = false;
            Class<?> childClass = null;

            if(elasticsearchFieldProperties.type().equals(ElasticsearchType.GEO_POINT) ||
                    elasticsearchFieldProperties.type().equals(ElasticsearchType.OBJECT) ||
                    elasticsearchFieldProperties.type().equals(ElasticsearchType.NESTED)) {

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
            elasticsearchFieldMetadata = new ElasticsearchFieldMetadata(field.getName(), elasticsearchFieldProperties, isArray, getElasticsearchFieldsMetadata(childClass));

            // Add to the response list
            elasticsearchFieldMappings.put(elasticsearchFieldMetadata.getFieldName(), elasticsearchFieldMetadata);
        }

        return elasticsearchFieldMappings;
    }

    /**
     *
     * @param clazz
     * @param elasticsearchDocument
     * @throws com.vossie.elasticsearch.annotations.exceptions.InvalidParentDocumentSpecified
     * @throws IOException
     */
    private static void validateParentTypeReference(Class<?> clazz, ElasticsearchDocument elasticsearchDocument) throws InvalidParentDocumentSpecified {

        // Test to see if we have a parent child relationship and set accordingly.
        if(!Empty.class.isAssignableFrom(elasticsearchDocument.parent())) {

            ElasticsearchDocument parentType;

            try {
                parentType = getElasticsearchType(elasticsearchDocument.parent());

            } catch (ClassNotAnnotated e) {
                throw new InvalidParentDocumentSpecified(elasticsearchDocument.parent(), "ClassNotAnnotated", elasticsearchDocument.index());
            }

            if(parentType != null) {
                if(!parentType.index().equals(elasticsearchDocument.index()))
                    throw new InvalidParentDocumentSpecified(clazz, elasticsearchDocument.index(), parentType.index());
            }
        }
    }

    /**
     * Get the document and field metadata associated with this mapping by class.
     * @param clazz The class to inspect.
     * @return Returns the meta data used to describe this entity.
     * @throws com.vossie.elasticsearch.annotations.exceptions.InvalidParentDocumentSpecified
     * @throws ClassNotAnnotated
     */
    public static ElasticsearchDocumentMetadata getProperties(Class<?> clazz) throws InvalidParentDocumentSpecified, ClassNotAnnotated, InvalidAttributeForType, UnableToLoadConstraints {

        // Check the cache to see if we have already parsed this reference.
        if(mappingCache.containsKey(clazz))
            return mappingCache.get(clazz);

        // Get the annotation.
        ElasticsearchDocument elasticsearchDocument = getElasticsearchType(clazz);

        // Test to see if we have a parent child relationship and set accordingly.
        validateParentTypeReference(clazz, elasticsearchDocument);

        // Add this item to the local cache for fast lookup.
        mappingCache.put(
                clazz,
                new ElasticsearchDocumentMetadata(clazz, elasticsearchDocument, getElasticsearchFieldsMetadata(clazz))
        );

        // Return the reference.
        return mappingCache.get(clazz);
    }
}
