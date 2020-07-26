package org.vossie.elasticsearch.annotations.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

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
