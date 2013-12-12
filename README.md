elasticsearch-annotations
=========================

A simple module to create Elasticsearch mappings using annotated classes.

I created this project because I got tired of managing separate json mapping files for each object I indexed in Elasticsearch.

Usage:

    ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.getMapping(Tweet.class);
    String mapping = documentMetadata.toJson();

 Annotated class:

     @ElasticsearchDocument(
             index = "twitter",
             type = "tweet"      /** Optional, if not set it will use the simple class name in a lower hyphenated format */,
             source = true       /** Optional */,
             parent = User.class /** Optional */
     )
     public class Tweet {

         @ElasticsearchFieldProperties(
                 type = ElasticsearchType.STRING,
                 index = "not_analyzed"
         )
         private String user;

         @ElasticsearchFieldProperties(
                 type = ElasticsearchType.DATE,
                 format = "YYYY-MM-dd"
         )
         private String postDate;

         @ElasticsearchFieldProperties(
                 type = ElasticsearchType.STRING,
                 store = BooleanValue.TRUE,
                 index = "analyzed",
                 null_value = "na"
         )
         private String message;

         @ElasticsearchFieldProperties(
                 type = ElasticsearchType.BOOLEAN
         )
         private Boolean hes_my_special_tweet;

         @ElasticsearchFieldProperties(
                 type = ElasticsearchType.INTEGER
         )
         private Integer priority;

         @ElasticsearchFieldProperties(
                 type = ElasticsearchType.FLOAT
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
            "_parent": {
                "type": "twitterUser"
            },
            "properties": {
                "message": {
                    "index": "analyzed",
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
