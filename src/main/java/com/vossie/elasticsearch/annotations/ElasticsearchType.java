package com.vossie.elasticsearch.annotations;

import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:28
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-types.html
 */

//TODO add attributes for all ElasticsearchTypes
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ElasticsearchType {

    /**
     * The type of field this is.
     * @return
     */
    public FieldType type();

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

    /**
     *Boolean value if term freq and positions should be omitted.
     * Defaults to false. Deprecated since 0.20, see index_options.
     * @return
     */
    public BooleanValue omit_term_freq_and_positions() default BooleanValue.NULL;


    /**
     *Should the field be included in the _all field (if enabled).
     * If index is set to no this defaults to false, otherwise, defaults to true or
     * to the parent object type setting.
     * @return
     */
    public BooleanValue include_in_all() default BooleanValue.NULL;

    /**
     * The multi_field type allows to map several core_types of the same value.
     * This can come very handy, for example, when wanting to map a string type,
     * once when it’s analyzed and once when it’s not_analyzed.
     * Each JSON field can be mapped to a specific core type. JSON itself already
     * provides us with some typing, with its support for string, integer/long, float/double,
     * boolean, and null.
     * @return ElasticsearchMultiFieldType Array
     */
    public ElasticsearchMultiFieldType[] fields() default {};

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

    // geo_shape
    /**
     * Name of the PrefixTree implementation to be used
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-shape-type.html
     * @return
     */
    public String tree() default Empty.NULL;

    /**
     * Maximum number of layers to be used by the PrefixTree. Elasticsearch only uses the tree_levels parameter
     * internally and this is what is returned via the mapping API even if you use the precision parameter.
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-shape-type.html
     * @return
     */
    public String precision() default Empty.NULL;

    /**
     * Used as a hint to the PrefixTree about how precise it should be. Defaults to 0.025 (2.5%) with 0.5 as
     * the maximum supported value.
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-shape-type.html
     */
    public double distance_error_pct() default 0d;

    /**
     * This parameter may be used instead of tree_levels to set an appropriate value for the tree_levels parameter.
     * The value specifies the desired precision and Elasticsearch will calculate the best tree_levels value
     * to honor this precision.
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-shape-type.html
     * @return
     */
    public String tree_levels() default Empty.NULL;


    //object attributes
    /**
     * This feature is by default turned on, and it’s the dynamic nature of each object mapped.
     * Each object mapped is automatically dynamic, though it can be explicitly turned off
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-object-type.html
     * @return
     */
    public BooleanValue dynamic() default BooleanValue.NULL;

    /**
     * The enabled flag allows to disable parsing and indexing a named object completely.
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-object-type.html
     * @return
     */
    public BooleanValue enabled() default BooleanValue.NULL;

    /**
     * path specifies the index_name for non-root elements when used inside a root element
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-object-type.html
     * @return
     */
    public String path() default Empty.NULL;


    //geo point attributes
    /**
     * Set to true to also index the .lat and .lon as fields. Defaults to false.
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-point-type.html
     * @return
     */
    public BooleanValue lat_lon() default BooleanValue.NULL;

    /**
     * Set to true to also index the .geohash as a field. Defaults to false.
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-point-type.html
     * @return
     */
    public BooleanValue geohash() default BooleanValue.NULL;

    /**
     * Sets the geohash precision. It can be set to an absolute geohash length or a distance value (eg 1km, 1m, 1ml)
     * defining the size of the smallest cell. Defaults to an absolute length of 12.
     */
    //TODO need to confirm whether it should be double or string
    public double geohash_precision() default 0d;

    /**
     * If this option is set to true, not only the geohash but also all its parent cells
     * (true prefixes) will be indexed as well. Defaults to false.
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-point-type.html
     * @return
     */
    public BooleanValue geohash_prefix() default BooleanValue.NULL;

    /**
     * Set to true to reject geo points with invalid latitude or longitude (default is false)
     * Note: Validation only works when normalization has been disabled.
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-point-type.html
     * @return
     */
    public BooleanValue validate() default BooleanValue.NULL;

    /**
     * Set to true to reject geo points with an invalid latitude
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-point-type.html
     * @return
     */
    public BooleanValue validate_lat() default BooleanValue.NULL;

    /**
     * Set to true to reject geo points with an invalid longitude
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-point-type.html
     * @return
     */
    public BooleanValue validate_lon() default BooleanValue.NULL;

    /**
     * Set to true to normalize latitude and longitude (default is true)
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-point-type.html
     * @return
     */
    public BooleanValue normalize() default BooleanValue.NULL;

    /**
     * Set to true to normalize latitude
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-point-type.html
     * @return
     */
    public BooleanValue normalize_lat() default BooleanValue.NULL;

    /**
     * Set to true to normalize longitude
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-point-type.html
     * @return
     */
    public BooleanValue normalize_lon() default BooleanValue.NULL;

    /**
     * attribute to specify the _boost field name
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-boost-field.html
     * @return
     */
    public String name() default Empty.NULL;

    /**
     * Another aspect of the _routing mapping is the ability to define it as required by setting required to true.
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-routing-field.html
     * @return
     */
    public BooleanValue routing() default BooleanValue.NULL;

    /**
     * You can provide a per index/type default _ttl value
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-ttl-field.html
     * @return
     */
    public String default_ttl() default Empty.NULL;

    /**
     * You can provide a per index/type default _ttl value
     * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-ttl-field.html
     * @return
     */
    public BooleanValue compress() default BooleanValue.NULL;


    /**
     * Usage 1: new String[]{
     *                      "[
     *                          [100.0, 0.0],
     *                          [101.0, 0.0],
     *                          [101.0, 1.0],
     *                          [100.0, 1.0],
     *                          [100.0, 0.0]
     *                      ],
     *                      [
     *                          [100.2, 0.2],
     *                          [100.8, 0.2],
     *                          [100.8, 0.8],
     *                          [100.2, 0.8],
     *                          [100.2, 0.2]
     *                      ]"
     *                   };
     *
     * Usage 2: new String[]{
     *                      "[
     *                          [100.0, 0.0],
     *                          [101.0, 0.0],
     *                          [101.0, 1.0],
     *                          [100.0, 1.0],
     *                          [100.0, 0.0]
     *                      ],
     *                      [
     *                          [100.2, 0.2],
     *                          [100.8, 0.2],
     *                          [100.8, 0.8],
     *                          [100.2, 0.8],
     *                          [100.2, 0.2]
     *                      ]"
     *                   };
     * @return
     */
    public String coordinates() default Empty.NULL;


}
