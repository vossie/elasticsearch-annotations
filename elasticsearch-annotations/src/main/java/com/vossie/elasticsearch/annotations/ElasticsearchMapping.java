package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.exceptions.ClassNotAnnotated;
import com.vossie.elasticsearch.annotations.exceptions.InvalidParentTypeSpecified;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
    private static HashMap<String, ElasticsearchTypeMetadata> mappingCache = new HashMap<>();

    public static final String OBJECT_TTL       = "_ttl";
    public static final String OBJECT_PARENT    = "_parent";
    public static final String OBJECT_SOURCE    = "_source";

    public static final String OBJECT_PROPERTIES= "properties";

    public static final String FIELD_TYPE       = "type";
    public static final String FIELD_INDEX      = "index";
    public static final String FIELD_ENABLED    = "enabled";
    public static final String FIELD_DEFAULT    = "default";

    /**
     * Get the ElasticsearchType annotations for the provided class object.
     * @param clazz The class to inspect.
     * @return The ElasticsearchType details
     * @throws ClassNotAnnotated if the annotation is not found.
     */
    private static ElasticsearchType getElasticsearchType(Class<?> clazz) throws ClassNotAnnotated {

        for(Annotation a : clazz.getAnnotations())
            if(a instanceof ElasticsearchType)
                return (ElasticsearchType) a;

        throw new ClassNotAnnotated(clazz);
    }

    /**
     * Get the type name from the annotation. If it is not set use the simple class name instead.
     * @param clazz The annotated class
     * @param elasticsearchType The type annotation.
     * @return The type name.
     */
    private static String getTypeName(Class<?> clazz, ElasticsearchType elasticsearchType) {

        String typeName = elasticsearchType.type();

        if(typeName.equals(""))
            typeName = clazz.getSimpleName().toLowerCase();

        return typeName;
    }

    private static Map<String, ElasticsearchFieldMetadata> getElasticsearchFieldsMetadata(Class<?> clazz) {

        Field[] fieldsFromClass = clazz.getDeclaredFields();

        // Also get the super class fields
        Field[] fieldsFromSuper = new Field[0];

        if(clazz.getSuperclass() != null)
            fieldsFromSuper = clazz.getSuperclass().getDeclaredFields();

        Field[] fields = new Field[fieldsFromClass.length + fieldsFromSuper.length];

        if(fieldsFromClass.length > 0)
            System.arraycopy(fieldsFromClass,0,fields,0,fieldsFromClass.length);

        if(fieldsFromSuper.length > 0)
            System.arraycopy(fieldsFromSuper,0,fields,fieldsFromClass.length,fieldsFromSuper.length);

        Map<String, ElasticsearchFieldMetadata> elasticsearchFieldMappings = new HashMap<>();

        // Iterate over all the annotated fields
        for(Field field : fields) {

            Annotation[] annotations = field.getAnnotations();

            for(Annotation a : annotations){

                if(a instanceof ElasticsearchField) {

                    ElasticsearchField elasticsearchField = (ElasticsearchField) a;
                    ElasticsearchFieldMetadata elasticsearchFieldMetadata;
                    boolean isArray = false;

                    if(elasticsearchField.type().equals(ElasticsearchField.Type.GEO_POINT) || elasticsearchField.type().equals(ElasticsearchField.Type.OBJECT)) {

                        // If it is an array we need the component type
                        isArray = field.getType().isArray();

                        // Get the child class
                        Class childClass = (field.getType().isArray())
                                ? field.getType().getComponentType()
                                : field.getType();

                        // Set the children
                        elasticsearchFieldMetadata = new ElasticsearchFieldMetadata(field.getName(), elasticsearchField, isArray, getElasticsearchFieldsMetadata(childClass));

                    } else {
                        elasticsearchFieldMetadata = new ElasticsearchFieldMetadata(field.getName(), elasticsearchField, isArray, new HashMap<String, ElasticsearchFieldMetadata>());
                    }

                    // Add to the response list
                    elasticsearchFieldMappings.put(elasticsearchFieldMetadata.getFieldName(), elasticsearchFieldMetadata);
                }
            }
        }

        return elasticsearchFieldMappings;
    }

    /**
     *
     * @param clazz
     * @param elasticsearchType
     * @throws InvalidParentTypeSpecified
     * @throws IOException
     */
    private static void validateParentTypeReference(Class<?> clazz, ElasticsearchType elasticsearchType) throws InvalidParentTypeSpecified, IOException {

        // Test to see if we have a parent child relationship and set accordingly.
        if(!elasticsearchType.parent().getName().equals(TopLevelType.class.getName())) {

            ElasticsearchType parentType;

            try {
                parentType = getElasticsearchType(elasticsearchType.parent());

            } catch (ClassNotAnnotated e) {

                throw new InvalidParentTypeSpecified(elasticsearchType.parent(), "ClassNotAnnotated", elasticsearchType.index());
            }

            if(parentType != null) {

                if(!parentType.index().equals(elasticsearchType.index()))
                    throw new InvalidParentTypeSpecified(clazz, elasticsearchType.index(), parentType.index());
            }
        }
    }

    /**
     * Get the mapping based on the provided annotations.
     * @param clazz The class to inspect.
     * @return Returns the meta data used to describe this entity.
     * @throws InvalidParentTypeSpecified
     * @throws ClassNotAnnotated
     */
    public static ElasticsearchTypeMetadata getMapping(Class<?> clazz) throws InvalidParentTypeSpecified, ClassNotAnnotated {

        // Check the cache to see if we have already parsed this reference.
        if(mappingCache.containsKey(clazz.getCanonicalName()))
            return mappingCache.get(clazz.getCanonicalName());

        // Get the annotation.
        ElasticsearchType elasticsearchType = getElasticsearchType(clazz);

        try {
            // Test to see if we have a parent child relationship and set accordingly.
            validateParentTypeReference(clazz, elasticsearchType);

            // Add this item to the local cache for fast lookup.
            mappingCache.put(
                    clazz.getCanonicalName(),
                    new ElasticsearchTypeMetadata(clazz, elasticsearchType, getElasticsearchFieldsMetadata(clazz))
            );

            // Return the reference.
            return mappingCache.get(clazz.getCanonicalName());

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * Get the value specified as the parent ID.
     * @param obj The child object to get the referenced parent id from.
     * @return The ID if it is found else null.
     */
    public static String getParentId(Object obj) {

        Field[] fields = obj.getClass().getDeclaredFields();

        for(Field field : fields) {

            Annotation[] annotations = field.getAnnotations();

            for(Annotation a : annotations){

                if(a instanceof ElasticsearchField) {

                    ElasticsearchField elasticsearchField = (ElasticsearchField) a;
                    if(elasticsearchField.isParentId())
                        try {
                            field.setAccessible(true);
                            return field.get(obj).toString();
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage());
                            return null;
                        }
                }
            }
        }

        return null;
    }
}
