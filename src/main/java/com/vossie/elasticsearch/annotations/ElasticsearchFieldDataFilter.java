package com.vossie.elasticsearch.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * It is possible to control which field values are loaded into memory, which is particularly useful for faceting
 * on string fields, using fielddata filters, which are explained in detail in the Fielddata section.
 * Fielddata filters can exclude terms which do not match a regex, or which don’t
 * fall between a min and max frequency range:
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-core-types.html#fielddata-filters
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ElasticsearchFieldDataFilter {

    // Todo: implement the data filter.

    public String regexPattern();

    public double frequencyMin();

    public double frequencyMax();

    public double frequencyMinSegmentSize();
}
