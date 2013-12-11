elasticsearch-annotations
=========================

A simple module to create Elasticsearch mappings using annotated classes.

I created this project because I got tired of managing separate json mapping files for each object I indexed in Elasticsearch.

Usage:

    ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.getMapping(Tweet.class);
    String mapping = documentMetadata.toJson();

 Annotated class:

     @ElasticsearchDocument(index = "twitter", source = true, parent = User.class)
     public class Tweet {

         @ElasticsearchField(type = ElasticsearchType.STRING, index = "not_analyzed", isParentId = BooleanValue.TRUE)
         private String user;

         @ElasticsearchField(type = ElasticsearchType.DATE, isDefaultSortByField = BooleanValue.TRUE)
         private String postDate;

         @ElasticsearchField(type = ElasticsearchType.STRING)
         private String message;

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
     }

 Produces:

    { "tweet" : {
        "_parent" : { "type" : "twitterUser" },
        "properties" : {
            "message" : { "type" : "string" },
            "postDate" : { "type" : "date" },
            "user" : { "index" : "not_analyzed", "type" : "string" }
            }
        }
    }
