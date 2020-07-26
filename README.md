# elasticsearch-annotations

Allows the creation of json mapping files based on class and field annotations.

This library can be used in any project which uses <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping.html" target="_blank">elasticsearch mappings</a> for managing data in the search engine.

Tested using Java 1.7 & Elasticsearch 0.95


### Set Up

Run 'mvn clean install' to build the library.

Add the packaged jar to your project to start using it.


### Usage:

You can generate json string for the ElasticSearch engine for a class annotated using the library, as shown below:

    // provide the name of the class which has been annotated using the library for which you need to 'get' the ElasticSearch mapping
    ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(Tweet.class);
    String mapping = documentMetadata.toMapping(); // Return the json mapping file

### Example Class:
An example of a class which uses <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-source-field.html" target="_blank">source field</a> metadata, as shown on the ElasticSearch guide:

    @ElasticsearchDocument(
            index = "my-index",
            type = "my_type",
            _elasticsearchFields = {
                    @ElasticsearchField(
                            _fieldName = FieldName._SOURCE,
                            includes = {
                                    "path1.*", "path2.*"
                            },
                            excludes = {
                                    "path3.*"
                            }
                    )
            }
    )

    public class myType {
        private String myValue;
    }



Below is another example of a Tweet class, which shows some of the possible combinations of ElasticSearch <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html" target="_blank">field</a> and <a href="http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-types.html" target="_blank">type</a> mappings which can be applied to a class.

    @ElasticsearchDocument /** required */(
            index = TweetIndex.class,
    //      type = "tweet"    /** optional, if not set it will use the simple class name in a lower hyphenated format */,
            _elasticsearchFields = {
                    @ElasticsearchField(
                        _fieldName = FieldName._SOURCE,
                        enabled = BooleanValue.TRUE
                    ),
                    @ElasticsearchField(
                            _fieldName = FieldName._ALL,
                            enabled = BooleanValue.TRUE
                    ),
                    @ElasticsearchField(
                            _fieldName = FieldName._PARENT,
                            type = User.class
                    ),
                    @ElasticsearchField(
                            _fieldName = FieldName._TIMESTAMP,
                            enabled = BooleanValue.FALSE,
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
                null_value = "na",
                fields = {
                        @ElasticsearchMultiFieldType(_name = "raw", index = "not_analyzed", type = FieldType.STRING)
                }
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


### Output:

Above Tweet class annotations produces the following output when we try to 'get' the ElasticsearchMappings for the class:

First level of the json is a set of document metadata, followed by properties which provide the type metadata.

    {
      "tweet": {
        "_ttl": {
          "default": "1d",
          "enabled": "true"
        },
        "_parent": {
          "type": "user"
        },
        "_source": {
          "enabled": "true"
        },
        "_timestamp": {
          "format": "dateOptionalTime",
          "enabled": "false"
        },
        "_all": {
          "enabled": "true"
        },
        "properties": {
          "postDate": {
            "format": "YYYY-MM-dd",
            "type": "date"
          },
          "hes_my_special_tweet": {
            "type": "boolean"
          },
          "rank": {
            "type": "float"
          },
          "message": {
            "null_value": "na",
            "index": "analyzed",
            "store": "true",
            "type": "string",
            "fields": {
              "raw": {
                "index": "not_analyzed",
                "type": "string"
              }
            }
          },
          "priority": {
            "type": "integer"
          },
          "user": {
            "index": "not_analyzed",
            "type": "string"
          }
        }
      }
    }

### Testing

The library has been unit tested with a combination of metadata usage, for example, saving to elasticsearch, inner class annotations, sub class annotations, geoshape annotations, etc.
