package org.vossie.test;

import org.vossie.elasticsearch.annotations.ElasticsearchDocument;
import org.vossie.elasticsearch.annotations.ElasticsearchField;
import org.vossie.elasticsearch.annotations.ElasticsearchIndex;
import org.vossie.elasticsearch.annotations.ElasticsearchType;
import org.vossie.elasticsearch.annotations.enums.FieldName;
import org.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Created by rpatadia on 16/12/2013.
 */
@ElasticsearchIndex(_indexName = "my-index")
@ElasticsearchDocument(
        type = "my_type",
        _elasticsearchFields = {
                @ElasticsearchField(
                        _fieldName = FieldName._SOURCE,
                        includes = {
                                "path1.*", "path2.*"
                        },
                        excludes = {
                                "pat3.*"
                        }
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._SOURCE,
                        includes = {
                                "path1.*", "path2.*"
                        },
                        excludes = {
                                "pat3.*"
                        }
                )
        }
)
public class ClassWithDuplicateFields {

    @ElasticsearchType(type = FieldType.KEYWORD)
    private String myValue;
}
