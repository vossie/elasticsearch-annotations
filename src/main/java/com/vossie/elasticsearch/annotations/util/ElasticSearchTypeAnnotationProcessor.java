package com.vossie.elasticsearch.annotations.util;

import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.ElasticsearchAnnotationTypeNames;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
* Created by rpatadia on 17/12/2013.
*/
@SupportedAnnotationTypes({ElasticsearchAnnotationTypeNames.ELASTICSEARCH_TYPE})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ElasticSearchTypeAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> typeElements,
                           RoundEnvironment roundEnv) {

        boolean response = false;

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ElasticsearchType.class);

        for (Element element : elements) {

            ElasticsearchType elasticsearchType = element.getAnnotation(ElasticsearchType.class);

            if(elasticsearchType == null)
                continue;

            verifyDuplicateElasticsearchFieldAnnotations(elasticsearchType);

            response = true;

        }
        return response;//claim the annotations
    }

    /**
     * This operation is used to validate the use of ElasticSearchTypes in the annotations
     * @param elasticsearchType - this is an instance of the ElasticSearchDocument type
     * @return
     */
    private void verifyDuplicateElasticsearchFieldAnnotations(ElasticsearchType elasticsearchType) {

        Map<String, Object> allAttributes = Collections.unmodifiableMap(AnnotationUtils.getAnnotationAttributes(elasticsearchType));

        ESTypeAttributeConstraints constraints = new ESTypeAttributeConstraints();

        for(String key : allAttributes.keySet()) {

            String attributeName = AttributeNameHelper.getAttributeName(elasticsearchType, key);
            Object value = allAttributes.get(key);

            if(value.toString().equals(Empty.NULL))
                continue;

            if(value.getClass().isArray() && ((Object[]) value).length == 0)
                continue;

            if(value.toString().equals("0") || value.toString().equals("0.0"))
                continue;

            if(value.toString().equals(Empty.class.toString()))
                continue;

            else if(!constraints.isValidAttributeForType(elasticsearchType.type().toString(), attributeName)) {

                if(!constraints.getAttributeNames().contains(attributeName))
                    continue;

                throw new RuntimeException();
            }
        }
    }

}
