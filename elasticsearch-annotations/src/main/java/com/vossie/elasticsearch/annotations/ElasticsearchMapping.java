package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.exceptions.ClassNotAnnotated;
import com.vossie.elasticsearch.annotations.exceptions.InvalidParentTypeSpecified;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:33
 */
public abstract class ElasticsearchMapping {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ElasticsearchMapping.class);
    private static HashMap<String, ElasticsearchTypeMetaData> mappingCache = new HashMap<>();

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
    private static ElasticsearchType getElasticsearchType(Class clazz) throws ClassNotAnnotated {

        for(Annotation a : clazz.getAnnotations())
            if(a instanceof ElasticsearchType)
                return (ElasticsearchType) a;

        throw new ClassNotAnnotated(clazz);
    }

    /**
     * Get the mapping based on the provided annotations.
     * @param clazz The class to inspect.
     * @return Returns the meta data used to describe this entity.
     * @throws InvalidParentTypeSpecified
     * @throws ClassNotAnnotated
     */
    public static ElasticsearchTypeMetaData getMapping(Class clazz) throws InvalidParentTypeSpecified, ClassNotAnnotated {

        ElasticsearchType elasticsearchType = getElasticsearchType(clazz);

        if(mappingCache.containsKey(clazz.getName()))
            return mappingCache.get(clazz.getName());

        try {
            String typeName = elasticsearchType.type();

            if(typeName.equals(""))
                typeName = clazz.getSimpleName().toLowerCase();

            XContentBuilder xbMapping = jsonBuilder()
                    .startObject()
                    .startObject(typeName); /** Set the object name */

            // Set the object expiry
            if(!elasticsearchType.ttl().equals("")) {
                xbMapping
                        .startObject(OBJECT_TTL)
                        .field(FIELD_ENABLED, true)
                        .field(FIELD_DEFAULT, elasticsearchType.ttl())
                        .endObject();
            }

            // Test to see if we have a parent child relationship and set accordingly.
            if(!elasticsearchType.parent().getName().equals(TopLevelType.class.getName())) {

                ElasticsearchType parentType;

                try {
                    parentType = getElasticsearchType(elasticsearchType.parent());
                } catch (ClassNotAnnotated e) {
                    throw new InvalidParentTypeSpecified(elasticsearchType.parent(), "", elasticsearchType.index());
                }

                if(parentType != null) {

                    String parentTypeName = parentType.type();

                    if(parentTypeName.equals(""))
                        parentTypeName = elasticsearchType.parent().getSimpleName().toLowerCase();

                    if(!parentType.index().equals(elasticsearchType.index()))
                        throw new InvalidParentTypeSpecified(clazz, elasticsearchType.index(), parentType.index());

                    xbMapping.startObject(OBJECT_PARENT).field(FIELD_TYPE, parentTypeName).endObject();
                }
            }

            // Set the _source mapping.
            if(!elasticsearchType.source())
                xbMapping.startObject(OBJECT_SOURCE).field(FIELD_ENABLED, elasticsearchType.source()).endObject();

            // Add the child fields.
            ArrayList<ElasticsearchFieldMetaData> elasticsearchFieldMappings = addFields(clazz, xbMapping);

            xbMapping
                    .endObject()
                    .endObject();


            ElasticsearchTypeMetaData elasticsearchTypeMapping = new ElasticsearchTypeMetaData(clazz, xbMapping, elasticsearchType).putAllElasticsearchFields(elasticsearchFieldMappings);
            mappingCache.put(clazz.getName(), elasticsearchTypeMapping);

            return elasticsearchTypeMapping;

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private static ArrayList<ElasticsearchFieldMetaData> addFields(Class clazz, XContentBuilder xbMapping) throws IOException {

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

        ArrayList<ElasticsearchFieldMetaData> elasticsearchFieldMappings = new ArrayList<>();

        xbMapping.startObject(OBJECT_PROPERTIES);

        // Iterate over all the annotated fields
        for(Field field : fields) {

            Annotation[] annotations = field.getAnnotations();

            for(Annotation a : annotations){

                if(a instanceof ElasticsearchField) {

                    ElasticsearchField elasticsearchField = (ElasticsearchField) a;
                    ElasticsearchFieldMetaData fieldMapping = new ElasticsearchFieldMetaData(field.getName(), elasticsearchField);

                    xbMapping.startObject(field.getName());

                    xbMapping.field(FIELD_TYPE, elasticsearchField.type().toString().toLowerCase());

                    if(!elasticsearchField.analyzer().equals(""))
                        xbMapping.field(FIELD_INDEX, elasticsearchField.analyzer());

                    if(elasticsearchField.type().equals(ElasticsearchField.Type.GEO_POINT) || elasticsearchField.type().equals(ElasticsearchField.Type.OBJECT)) {

                        /** If it is an array we need the component type */
                        fieldMapping.setArray(field.getType().isArray());
                        Class childClass = (field.getType().isArray())
                                ? field.getType().getComponentType()
                                : field.getType();

                        fieldMapping.setChildren(
                                addFields(childClass, xbMapping)
                        );
                    }

                    xbMapping.endObject();
                    elasticsearchFieldMappings.add(fieldMapping);
                }
            }
        }

        xbMapping.endObject();
        return elasticsearchFieldMappings;
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
