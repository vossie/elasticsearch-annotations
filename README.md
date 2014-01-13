elasticsearch-annotations
=========================

A simple module to create Elasticsearch mappings using annotated classes.

I created this project because I got tired of managing separate json mapping files for each object I indexed in Elasticsearch.

Usage:

    ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(Tweet.class);
    String mapping = documentMetadata.toMapping();

 Annotated class:

@ElasticsearchDocument /** required */(
        index = "twitter",
//        type = "tweet"    /** optional, if not set it will use the simple class name in a lower hyphenated format */,
        _elasticsearchFields = {
                @ElasticsearchField(
                        _fieldName = FieldName._ID,
                        index = "not_analyzed",
                        store = "yes"
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._TYPE,
                        index = "no",
                        store = "yes"
                ),
                @ElasticsearchField(
                    _fieldName = FieldName._SOURCE,
                    enabled = BooleanValue.TRUE
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._ALL,
                        enabled = BooleanValue.TRUE
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._ANALYZER,
                        path = "user"
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._BOOST,
                        name = "my_boost",
                        null_value = "1.0"
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._PARENT,
                        type = User.class
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._ROUTING,
                        required = BooleanValue.FALSE,
                        path = "blog.post_id"
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._INDEX,
                        enabled = BooleanValue.FALSE
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._SIZE,
                        enabled = BooleanValue.FALSE,
                        store = "yes"
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._TIMESTAMP,
                        enabled = BooleanValue.FALSE,
                        path = "post_date",
                        format = "dateOptionalTime"
                ),
                @ElasticsearchField(
                        _fieldName = FieldName._TTL,
                        enabled = BooleanValue.TRUE,
                        defaultValue = "1d"
                )
        }
)
public class Tweet {

    @ElasticsearchType(
            type = FieldType.STRING,
            index = "not_analyzed"
    )
    private String user;

    @ElasticsearchType(
            type = FieldType.DATE,
            format = "YYYY-MM-dd"
    )
    private String postDate;

    @ElasticsearchType(
            type = FieldType.STRING,
            store = BooleanValue.TRUE,
            index = "analyzed",
            null_value = "na"
    )
    private String message;

    @ElasticsearchType(
            type = FieldType.BOOLEAN
    )
    private Boolean hes_my_special_tweet;

    @ElasticsearchType(
            type = FieldType.INTEGER
    )
    private Integer priority;

    @ElasticsearchType(
            type = FieldType.FLOAT
    )
    private Float rank;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getHes_my_special_tweet() {
        return hes_my_special_tweet;
    }

    public void setHes_my_special_tweet(Boolean hes_my_special_tweet) {
        this.hes_my_special_tweet = hes_my_special_tweet;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Float getRank() {
        return rank;
    }

    public void setRank(Float rank) {
        this.rank = rank;
    }
}


 Produces:

    {
                "tweet": {
                    "_boost": {
                        "null_value": "1.0",
                        "name": "my_boost"
                    },
                    "_type": {
                        "index": "no",
                        "store": "yes"
                    },
                    "_source": {
                        "enabled": "true"
                    },
                    "_id": {
                        "index": "not_analyzed",
                        "store": "yes"
                    },
                    "_routing": {
                        "path": "blog.post_id",
                        "required": "false"
                    },
                    "_analyzer": {
                        "path": "user"
                    },
                    "_timestamp": {
                        "enabled": "false",
                        "path": "post_date",
                        "format": "dateOptionalTime"
                    },
                    "_index": {
                        "enabled": "false"
                    },
                    "_ttl": {
                        "enabled": "true",
                        "default": "1d"
                    },
                    "_size": {
                        "enabled": "false",
                        "store": "yes"
                    },
                    "_all": {
                        "enabled": "true"
                    },
                    "_parent": {
                        "type": "user"
                    },
                    "properties": {
                        "message": {
                            "index": "analyzed",
                            "store": "true",
                            "null_value": "na",
                            "type": "string"
                        },
                        "rank": {
                            "type": "float"
                        },
                        "priority": {
                            "type": "integer"
                        },
                        "hes_my_special_tweet": {
                            "type": "boolean"
                        },
                        "postDate": {
                            "format": "YYYY-MM-dd",
                            "type": "date"
                        },
                        "user": {
                            "index": "not_analyzed",
                            "type": "string"
                        }
                    }
                }
            }