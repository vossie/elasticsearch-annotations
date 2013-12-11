package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.*;
import org.elasticsearch.search.sort.SortOrder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ElasticsearchField {

    /* >>>>>>> START: Helper <<<<<<<< */

    /**
     * Does this field contain a reference key to the parent document.
     * @return Boolean
     */
    public BooleanValue isParentId() default BooleanValue.NULL;

    /**
     * Should we use this field as the default sort by for queries if none is specified.
     * @return Boolean
     */
    public BooleanValue isDefaultSortByField() default BooleanValue.NULL;

    /**
     * The default sort order to use if no sort order is specified.
     * @return
     */
    public SortOrder defaultSortOrder() default SortOrder.ASC;

    /* >>>>>>> End: Helper <<<<<<<< */

    /* >>>>>>> START: Common <<<<<<<< */

    /**
     * The type of field this is.
     * @return
     */
    public ElasticsearchType type();

    /**
     * From version 0.90.Beta1 Elasticsearch includes changes from Lucene 4 that allows you to configure a
     * similarity (scoring algorithm) per field. Allowing users a simpler extension beyond the usual TF/IDF algorithm.
     * As part of this, new algorithms have been added including BM25. Also as part of the changes,
     * it is now possible to define a Similarity per field, giving even greater control over scoring.
     *
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-core-types.html#similarity
     * @return
     */
    public String similarity() default Empty.NULL;

    /**
     * Posting formats define how fields are written into the index and how fields are represented into memory.
     * Posting formats can be defined per field via the postings_format option. Postings format are configurable
     * since version 0.90.0.Beta1. Elasticsearch has several builtin formats:
     *
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-core-types.html#postings
     */
    public PostingsFormat postings_format() default PostingsFormat.NULL;


    /* >>>>>>> END: Common <<<<<<<< */

    /**
     * The name of the field that will be stored in the index. Defaults to the property/field name if __null.
     * @return
     */
    public String index_name() default Empty.NULL;

    /**
     * Set to yes to store actual field in the index, no to not store it.
     * Defaults to no (note, the JSON document itself is stored, and it can be retrieved from it).
     */
    public BooleanValue store() default BooleanValue.NULL;

    /**
     * Set to analyzed for the field to be indexed and searchable after being broken down into token using an analyzer.
     * not_analyzed means that its still searchable, but does not go through any analysis process or broken down into
     * tokens. no means that it won’t be searchable at all (as an individual field; it may still be included in _all).
     * Setting to no disables include_in_all. Defaults to analyzed.
     * @return
     */
    public String index() default Empty.NULL;

    /**
     * Possible values are no, yes, with_offsets, with_positions, with_positions_offsets. Defaults to no.
     */
    public String boost() default Empty.NULL;

    /**
     * When there is a (JSON) null value for the field, use the null_value as the field value.
     * Defaults to not adding the field at all.
     */
    public String null_value() default Empty.NULL;

    /* >>>>>>> End: Common <<<<<<<< */

    /**
     * Possible values are no, yes, with_offsets, with_positions, with_positions_offsets. Defaults to no.
     * @return
     */
    public TermVector term_vector() default TermVector.NULL;

    /**
     * Boolean value if norms should be omitted or not. Defaults to false for analyzed fields,
     * and to true for not_analyzed fields.
     */
    public BooleanValue omit_norms() default BooleanValue.NULL;

    /**
     * Available since 0.20. Allows to set the indexing options, possible values are docs
     * (only doc numbers are indexed), freqs (doc numbers and term frequencies), and positions
     * (doc numbers, term frequencies and positions). Defaults to positions for analyzed fields,
     * and to docs for not_analyzed fields. Since 0.90 it is also possible to set it to offsets
     * (doc numbers, term frequencies, positions and offsets).
     */
    public IndexOption index_options() default IndexOption.NULL;

    /**
     * The analyzer used to analyze the text contents when analyzed during indexing and when searching using
     * a query string. Defaults to the globally configured analyzer.
     */
    public String analyzer() default Empty.NULL;

    /**
     * The analyzer used to analyze the text contents when analyzed during indexing.
     */
    public String index_analyzer() default Empty.NULL;

    /**
     * The analyzer used to analyze the field when part of a query string. Can be updated on an existing field.
     */
    public String search_analyzer() default Empty.NULL;

    /**
     * The analyzer will ignore strings larger than this size. Useful for generic not_analyzed fields that should
     * ignore long text. (since @0.19.9).
     */
    public int ignore_above() default 0;

    /**
     * Position increment gap between field instances with the same field name. Defaults to 0.
     */
    public double position_offset_gap() default 0d;

    //numbers
    /**
     * The precision step (number of terms generated for each number value). Defaults to 4.
     */
    public double precision_step() default 0d;

    /**
     * Ignored a malformed number. Defaults to false. (Since @0.19.9).
     */
    public BooleanValue ignore_malformed() default BooleanValue.NULL;

    // date
    /**
     * The date format. Defaults to dateOptionalTime.
     * "format" : "YYYY-MM-dd"
     */
    public String format() default Empty.NULL;

}
