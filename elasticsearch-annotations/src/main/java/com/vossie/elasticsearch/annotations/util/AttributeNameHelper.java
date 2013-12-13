package com.vossie.elasticsearch.annotations.util;

import com.vossie.elasticsearch.annotations.MethodToAttributeNameMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * User: cvosloo
 * Date: 13/12/2013
 * Time: 09:47
 */
public abstract class AttributeNameHelper {

    public static String getAttributeName(Annotation annotation, String attributeName) {

        Method[] methods = annotation.annotationType().getDeclaredMethods();

        for (Method method : methods) {

            if(!method.getName().equals(attributeName))
                continue;

            MethodToAttributeNameMapping nameMapping = method.getAnnotation(MethodToAttributeNameMapping.class);

            if(nameMapping == null)
                return attributeName;

            return nameMapping.mapsTo();
        }

        return attributeName;
    }
}
