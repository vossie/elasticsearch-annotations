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

     @ElasticsearchDocument /** required to be able to parse the annotations*/(
             index = "twitter", // name of the index
             //type = "tweet"    /** optional, if not set it will use the simple class name in a lower hyphenated format */,

             // Specify the elasticsearch fields for which you want to add the metadata for.
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

     //Class for which the above set of annotations will be applied
     public class Tweet {

         // Annotations applied to the instance variables, as well, for elastic search types
         @ElasticsearchField(
                 type = FieldType.STRING,
                 index = "not_analyzed"
         )
         private String user;

         @ElasticsearchField(
                 type = FieldType.DATE,
                 format = "YYYY-MM-dd"
         )
         private String postDate;

         @ElasticsearchField(
                 type = FieldType.STRING,
                 store = BooleanValue.TRUE,
                 index = "analyzed",
                 null_value = "na"
         )
         private String message;

         @ElasticsearchField(
                 type = FieldType.BOOLEAN
         )
         private Boolean hes_my_special_tweet;

         @ElasticsearchField(
                 type = FieldType.INTEGER
         )
         private Integer priority;

         @ElasticsearchField(
                 type = FieldType.FLOAT
         )
         private Float rank;

         public String getUser() {
             return user;
         }

         public void setUser(String user) {
             this.user = user;
         }

         // setter/getter methods for other variables
     }

### Output:

Above Tweet class annotations produces the following output when we try to 'get' the ElasticsearchMappings for the class:

First level of the json is a set of document metadata, followed by properties which provide the type metadata.

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


### Testing

The library has been unit tested with a combination of metadata usage, for example, saving to elasticsearch, inner class annotations, sub class annotations, geoshape annotations, etc.


### Version

Current stable version of elasticsearch-annotations is <a href="https://github.com/vossie/elasticsearch-annotations/commit/3dd7259b5d75b4d818ef19027802f8ab09caad5c" target="_blank">1.13</a>