elasticsearch-annotations
=========================

Allows the creation of json mapping files based on class and field annotations.

Tested using Java 1.7 & Elasticsearch 0.95

Usage:

    ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(Tweet.class);
    String mapping = documentMetadata.toMapping(); // Return the json mapping file

 Example class:

     @ElasticsearchDocument /** required */(
             index = "twitter",
             //type = "tweet"    /** optional, if not set it will use the simple class name in a lower hyphenated format */,
             _rootFields = {
                     @ElasticsearchRootField(
                             _rootFieldName = SystemField._ID,
                             index = "not_analyzed",
                             store = "yes"
                     ),
                     @ElasticsearchRootField(
                             _rootFieldName = SystemField._TYPE,
                             index = "no",
                             store = "yes"
                     ),
                     @ElasticsearchRootField(
                         _rootFieldName = SystemField._SOURCE,
                         enabled = BooleanValue.TRUE
                     ),
                     @ElasticsearchRootField(
                             _rootFieldName = SystemField._ALL,
                             enabled = BooleanValue.TRUE
                     ),
                     @ElasticsearchRootField(
                             _rootFieldName = SystemField._ANALYZER,
                             path = "user"
                     ),
                     @ElasticsearchRootField(
                             _rootFieldName = SystemField._BOOST,
                             name = "my_boost",
                             null_value = "1.0"
                     ),
                     @ElasticsearchRootField(
                             _rootFieldName = SystemField._PARENT,
                             type = User.class
                     ),
                     @ElasticsearchRootField(
                             _rootFieldName = SystemField._ROUTING,
                             required = BooleanValue.FALSE,
                             path = "blog.post_id"
                     ),
                     @ElasticsearchRootField(
                             _rootFieldName = SystemField._INDEX,
                             enabled = BooleanValue.FALSE
                     ),
                     @ElasticsearchRootField(
                             _rootFieldName = SystemField._SIZE,
                             enabled = BooleanValue.FALSE,
                             store = "yes"
                     ),
                     @ElasticsearchRootField(
                             _rootFieldName = SystemField._TIMESTAMP,
                             enabled = BooleanValue.FALSE,
                             path = "post_date",
                             format = "dateOptionalTime"
                     ),
                     @ElasticsearchRootField(
                             _rootFieldName = SystemField._TTL,
                             enabled = BooleanValue.TRUE,
                             defaultValue = "1d"
                     )
             }
     )
     public class Tweet {

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
