package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.common.ElasticsearchDocumentMetadata;
import com.vossie.elasticsearch.annotations.enums.FieldType;
import com.vossie.elasticsearch.annotations.util.ESTypeAttributeConstraints;
import com.vossie.models.LocationWithInnerClass;
import com.vossie.models.LocationWithLocalClass;
import com.vossie.models.LocationWithStaticInnerClass;
import com.vossie.models.MyTweet;
import com.vossie.models.Tweet;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.test.ESIntegTestCase;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestElasticsearchMapping extends ESIntegTestCase {

    @Test
    public void testForClassNotAnnotatedException() {

        ElasticsearchMapping.get(Object.class);
    }

    @Test
    public void testGettingTweetMapping() throws IOException, JSONException {

        ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(Tweet.class);
        String json = documentMetadata.toMapping();

        String expected = "{\"_source\":{\"enabled\":\"true\"},\"properties\":{\"postDate\":{\"format\":\"YYYY-MM-dd\",\"index\":\"true\",\"store\":\"true\",\"type\":\"date\"},\"hes_my_special_tweet\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"boolean\"},\"rank\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"float\"},\"message\":{\"index\":\"false\",\"store\":\"true\",\"type\":\"text\",\"fields\":{\"raw\":{\"index\":\"false\",\"type\":\"keyword\"}}},\"priority\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"integer\"},\"user\":{\"index\":\"false\",\"store\":\"true\",\"type\":\"keyword\"}}}";
        JSONAssert.assertEquals(expected,json,true);
    }

    @Test
    public void testGettingMyTweetMapping() throws IOException,JSONException {
        ElasticsearchDocumentMetadata elasticsearchDocumentMetadata = ElasticsearchMapping.get(MyTweet.class);
        String json = elasticsearchDocumentMetadata.toMapping();

        String expected = "{\"my-tweet\":{\"_index\":{\"enabled\":\"true\"},\"properties\":{\"postDate\":{\"format\":\"YYYY-MM-dd\",\"index\":\"true\",\"store\":\"true\",\"type\":\"date\"},\"hes_my_special_tweet\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"boolean\"},\"rank\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"float\"},\"message\":{\"index\":\"false\",\"store\":\"true\",\"type\":\"text\",\"fields\":{\"raw\":{\"index\":\"false\",\"type\":\"keyword\"}}},\"priority\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"integer\"},\"user\":{\"index\":\"false\",\"store\":\"true\",\"type\":\"keyword\"},\"myMessage\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"text\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }


    @Test
    public void testGettingLocationWithInnerClassMapping() throws IOException,JSONException {
        ElasticsearchDocumentMetadata elasticsearchDocumentMetadata = ElasticsearchMapping.get(LocationWithInnerClass.class);
        String json = elasticsearchDocumentMetadata.toMapping();

        String expected = "{\"anyType\":{\"_source\":{\"enabled\":\"true\"},\"properties\":{\"userName\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"keyword\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }

    @Test
    public void testGettingInnerClassMapping() throws IOException,JSONException {
        ElasticsearchDocumentMetadata elasticsearchDocumentMetadata = ElasticsearchMapping.get(LocationWithInnerClass.Location.class);
        String json = elasticsearchDocumentMetadata.toMapping();

        String expected = "{\"locationType\":{\"_size\":{\"store\":\"yes\",\"enabled\":\"false\"},\"_boost\":{\"null_value\":\"1.0\",\"name\":\"my_boost\"},\"_index\":{\"enabled\":\"false\"},\"properties\":{\"lon\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"double\"},\"lat\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"double\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }

    @Test
    public void testGettingLocationWithLocalClassMapping() throws IOException,JSONException {
        ElasticsearchDocumentMetadata elasticsearchDocumentMetadata = ElasticsearchMapping.get(LocationWithLocalClass.class);
        String json = elasticsearchDocumentMetadata.toMapping();

        String expected = "{\"anyType\":{\"_source\":{\"enabled\":\"true\"},\"properties\":{\"userName\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"keyword\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }

    @Test
    public void testGettingLocationWithStaticInnerClassMapping() throws IOException,JSONException {
        ElasticsearchDocumentMetadata elasticsearchDocumentMetadata = ElasticsearchMapping.get(LocationWithStaticInnerClass.class);
        String json = elasticsearchDocumentMetadata.toMapping();

        String expected = "{\"anyType\":{\"_source\":{\"enabled\":\"true\"},\"properties\":{\"userName\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"keyword\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }

    @Test
    public void testGettingLocationOfStaticInnerClassMapping() throws IOException,JSONException {
        ElasticsearchDocumentMetadata elasticsearchDocumentMetadata = ElasticsearchMapping.get(LocationWithStaticInnerClass.Location.class);
        String json = elasticsearchDocumentMetadata.toMapping();

        String expected = "{\"locationType\":{\"_boost\":{\"null_value\":\"1.0\",\"name\":\"my_boost\"},\"_index\":{\"enabled\":\"true\"},\"properties\":{\"lon\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"double\"},\"lat\":{\"index\":\"true\",\"store\":\"true\",\"type\":\"double\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }

    @Test
    public void testRead() throws Exception {

        new ESTypeAttributeConstraints();
    }

    @Test
    public void testValidatingTypeAttributeTrue() throws Exception {

        assertTrue(new ESTypeAttributeConstraints().isValidAttributeForType(FieldType.KEYWORD.toString(), "type"));
    }

    @Test
    public void testValidatingTypeAttributeFalse() throws Exception {

        assertFalse(new ESTypeAttributeConstraints().isValidAttributeForType(FieldType.GEO_POINT.toString(), "term_vector"));
    }

    /**
     * In order to run this test, you will have to start ES instance independently, the directory can be found inside the "apps" directory.
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testSavingMappingToElasticInstance() throws IOException, InterruptedException, JSONException {

        ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(Tweet.class);

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

        // Create new index
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(documentMetadata.getIndexName());
        CreateIndexResponse indexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        assertEquals(documentMetadata.getIndexName(), indexResponse.index());

        // Put type mapping
        PutMappingRequest putMappingRequest = new PutMappingRequest(documentMetadata.getIndexName()).source(documentMetadata.toMapping(), XContentType.JSON);
        AcknowledgedResponse mappingResponse = client.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);
        assertTrue(mappingResponse.isAcknowledged());

        // Assert mappings
        String actualMapping = getMapping(documentMetadata);

        assertNotNull(actualMapping);

        // Delete existing index
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(documentMetadata.getIndexName());
        AcknowledgedResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        assertTrue(deleteIndexResponse.isAcknowledged());

        String expected = "{\"twitter1\":{\"mappings\":{\"properties\":{\"hes_my_special_tweet\":{\"type\":\"boolean\",\"store\":true},\"message\":{\"type\":\"text\",\"index\":false,\"store\":true,\"fields\":{\"raw\":{\"type\":\"keyword\",\"index\":false}}},\"postDate\":{\"type\":\"date\",\"store\":true,\"format\":\"YYYY-MM-dd\"},\"priority\":{\"type\":\"integer\",\"store\":true},\"rank\":{\"type\":\"float\",\"store\":true},\"user\":{\"type\":\"keyword\",\"index\":false,\"store\":true}}}}}\n";

        JSONAssert.assertEquals(expected,actualMapping,true);
    }


    /******************
     * Helper Methods *
     ******************/

    private String getMapping(ElasticsearchDocumentMetadata documentMetadata) throws IOException {
        String typeMappingString = "http://localhost:9200/" + documentMetadata.getIndexName() + "/_mapping/";

        URL url = new URL(typeMappingString);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("GET");
        httpCon.setRequestProperty("Content-length", "0");
        httpCon.connect();

        int response = httpCon.getResponseCode();

        if(response == 200) {
            System.out.println("=======Get response is ======== " + response);
            BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            String jsonString = sb.toString();
            System.out.println("=======Get response json is ======== " + jsonString);
            return jsonString;

        }

        return null;
    }
}
