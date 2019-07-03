package com.vossie.elasticsearch.annotations.util;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.ElasticsearchAnnotationTypeNames;
import com.vossie.elasticsearch.annotations.enums.FieldName;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
* Created by rpatadia on 17/12/2013.
*/
@SupportedAnnotationTypes({ElasticsearchAnnotationTypeNames.ELASTICSEARCH_DOCUMENT})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ElasticSearchDocumentAnnotationProcessor extends AbstractProcessor {

    public ElasticSearchDocumentAnnotationProcessor() {
        super();
    }

    @Override
    public boolean process(Set<? extends TypeElement> typeElements,
                           RoundEnvironment roundEnv) {

        boolean response = false;

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ElasticsearchDocument.class);

        for (Element element : elements) {

            ElasticsearchDocument elasticsearchDocument = element.getAnnotation(ElasticsearchDocument.class);

            if(elasticsearchDocument == null)
                continue;

            verifyDuplicateElasticsearchFieldAnnotations(elasticsearchDocument, element.toString());

            response = true;

        }
        return response;//claim the annotations
    }

    /**
     * This operation will check for duplicate occurrences of ElasticsearchFields in the annotations
     * @param elasticsearchDocument - this is an instance of the ElasticSearchDocument type
     * @param className
     * @return
     */
    private boolean verifyDuplicateElasticsearchFieldAnnotations(ElasticsearchDocument elasticsearchDocument, String className) {

        List<FieldName> fieldNames = new ArrayList<>();

        for(ElasticsearchField elasticsearchField : elasticsearchDocument._elasticsearchFields()) {

            if(fieldNames.contains(elasticsearchField._fieldName())) {
                throw new RuntimeException(String.format("Class %s is using duplicate annotated Elasticsearch fields %s. ", className, elasticsearchField._fieldName().toString()));
            }
            fieldNames.add(elasticsearchField._fieldName());
        }

        return true;
    }

}
