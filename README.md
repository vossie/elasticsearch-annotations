elasticsearch-annotations
=========================

A simple module to create Elasticsearch mappings using annotated classes.

I created this project because I got tired of managing separate json files for each object I indexed in Elasticsearch.

Usage:
 Annotating a class:

     @ElasticsearchType(index = "twitter", type = "tweet", source = true, parent = User.class)
     public class Tweet {

         @ElasticsearchField(type = ElasticsearchField.Type.STRING, analyzer = "not_analyzed", isParentId = true)
         private String user;

         @ElasticsearchField(type = ElasticsearchField.Type.DATE, isDefaultSortByField = true)
         private String postDate;

         @ElasticsearchField(type = ElasticsearchField.Type.STRING)
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

