package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.common.ElasticsearchDocumentMetadata;
import com.vossie.elasticsearch.annotations.enums.FieldType;
import com.vossie.elasticsearch.annotations.util.ESTypeAttributeConstraints;
import com.vossie.models.InvalidParentTypeAnnotationTestClass;
import com.vossie.models.LocationWithInnerClass;
import com.vossie.models.LocationWithLocalClass;
import com.vossie.models.LocationWithStaticInnerClass;
import com.vossie.models.MyTweet;
import com.vossie.models.Tweet;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.test.ESIntegTestCase;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Copyright © 2013 Carel Vosloo.
 * com.vossie.models.User: cvosloo
 * Date: 09/12/2013
 * Time: 21:00
 */
public class TestElasticsearchMapping extends ESIntegTestCase {

    private static TransportClient transportClient;

    @Test
    public void testForClassNotAnnotatedException() {

        ElasticsearchMapping.get(Object.class);
    }

    @Test
    public void testInvalidParentTypeSpecifiedException() {

        ElasticsearchMapping.get(InvalidParentTypeAnnotationTestClass.class);
    }

    @Test
    public void testGettingTweetMapping() throws IOException, JSONException {

        ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(Tweet.class);
        String json = documentMetadata.toMapping();

        String expected = "{\"tweet\":{\"_parent\":{\"type\":\"user\"},\"_source\":{\"enabled\":\"true\"},\"_all\":{\"enabled\":\"true\"},\"properties\":{\"postDate\":{\"format\":\"YYYY-MM-dd\",\"type\":\"date\"},\"hes_my_special_tweet\":{\"type\":\"boolean\"},\"rank\":{\"type\":\"float\"},\"message\":{\"index\":\"false\",\"store\":\"true\",\"type\":\"text\",\"fields\":{\"raw\":{\"index\":\"false\",\"type\":\"keyword\"}}},\"priority\":{\"type\":\"integer\"},\"user\":{\"index\":\"false\",\"type\":\"keyword\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }

    @Test
    public void testGettingMyTweetMapping() throws IOException,JSONException {
        ElasticsearchDocumentMetadata elasticsearchDocumentMetadata = ElasticsearchMapping.get(MyTweet.class);
        String json = elasticsearchDocumentMetadata.toMapping();

        String expected = "{\"tweet\":{\"_parent\":{\"type\":\"user\"},\"_source\":{\"enabled\":\"true\"},\"_all\":{\"enabled\":\"true\"},\"properties\":{\"postDate\":{\"format\":\"YYYY-MM-dd\",\"type\":\"date\"},\"hes_my_special_tweet\":{\"type\":\"boolean\"},\"rank\":{\"type\":\"float\"},\"message\":{\"index\":\"false\",\"store\":\"true\",\"type\":\"text\",\"fields\":{\"raw\":{\"index\":\"false\",\"type\":\"keyword\"}}},\"priority\":{\"type\":\"integer\"},\"user\":{\"index\":\"false\",\"type\":\"keyword\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }


    @Test
    public void testGettingLocationWithInnerClassMapping() throws IOException,JSONException {
        ElasticsearchDocumentMetadata elasticsearchDocumentMetadata = ElasticsearchMapping.get(LocationWithInnerClass.class);
        String json = elasticsearchDocumentMetadata.toMapping();

        String expected = "{\"anyType\":{\"_source\":{\"enabled\":\"true\"},\"properties\":{\"userName\":{\"index\":\"true\",\"type\":\"keyword\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }

    @Test
    public void testGettingInnerClassMapping() throws IOException,JSONException {
        ElasticsearchDocumentMetadata elasticsearchDocumentMetadata = ElasticsearchMapping.get(LocationWithInnerClass.Location.class);
        String json = elasticsearchDocumentMetadata.toMapping();

        String expected = "{\"locationType\":{\"_boost\":{\"null_value\":\"1.0\",\"name\":\"my_boost\"},\"_index\":{\"enabled\":\"false\"},\"_size\":{\"enabled\":\"false\",\"store\":\"yes\"},\"properties\":{\"lon\":{\"type\":\"double\"},\"lat\":{\"type\":\"double\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }

    @Test
    public void testGettingLocationWithLocalClassMapping() throws IOException,JSONException {
        ElasticsearchDocumentMetadata elasticsearchDocumentMetadata = ElasticsearchMapping.get(LocationWithLocalClass.class);
        String json = elasticsearchDocumentMetadata.toMapping();

        String expected = "{\"anyType\":{\"_source\":{\"enabled\":\"true\"},\"properties\":{\"userName\":{\"index\":\"true\",\"type\":\"keyword\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }

    @Test
    public void testGettingLocationWithStaticInnerClassMapping() throws IOException,JSONException {
        ElasticsearchDocumentMetadata elasticsearchDocumentMetadata = ElasticsearchMapping.get(LocationWithStaticInnerClass.class);
        String json = elasticsearchDocumentMetadata.toMapping();

        String expected = "{\"anyType\":{\"_source\":{\"enabled\":\"true\"},\"properties\":{\"userName\":{\"index\":\"true\",\"type\":\"keyword\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }

    @Test
    public void testGettingLocationOfStaticInnerClassMapping() throws IOException,JSONException {
        ElasticsearchDocumentMetadata elasticsearchDocumentMetadata = ElasticsearchMapping.get(LocationWithStaticInnerClass.Location.class);
        String json = elasticsearchDocumentMetadata.toMapping();

        String expected = "{\"locationType\":{\"_boost\":{\"null_value\":\"1.0\",\"name\":\"my_boost\"},\"_index\":{\"enabled\":\"true\"},\"properties\":{\"lon\":{\"type\":\"double\"},\"lat\":{\"type\":\"double\"}}}}";
        JSONAssert.assertEquals(expected,json,true);
    }

    @Test
    public void testGettingParentMappingFromChild() throws IOException, JSONException {

        String json = ElasticsearchMapping.get(Tweet.class).getParent().toMapping();
        String expected = "{\"user\":{\"properties\":{\"dateOfBirth\":{\"format\":\"dateOptionalTime\",\"type\":\"date\"},\"location\":{\"type\":\"geo_point\",\"properties\":{\"lon\":{\"type\":\"double\"},\"lat\":{\"type\":\"double\"}}},\"citiesVisited\":{\"type\":\"nested\",\"properties\":{\"location\":{\"type\":\"geo_point\",\"properties\":{\"lon\":{\"type\":\"double\"},\"lat\":{\"type\":\"double\"}}},\"name\":{\"type\":\"text\",\"fields\":{\"raw\":{\"index\":\"true\",\"type\":\"keyword\"}}}}},\"user\":{\"store\":\"true\",\"type\":\"keyword\"}}}}";
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
    public void testSavingMappingToElasticInstance() throws IOException, InterruptedException {

        ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(Tweet.class);
        createIndexAndMapping(documentMetadata);
        Thread.sleep(1000);
        String actualMapping = getMapping(documentMetadata);

        if(actualMapping!=null)
            deleteIndex(documentMetadata);

        assertTrue(actualMapping != null);
    }


    /******************
     * Helper Methods *
     ******************/

    private String getMapping(ElasticsearchDocumentMetadata documentMetadata) throws IOException {
        String typeMappingString = "http://localhost:9200/" + documentMetadata.getIndexName() + "/_mapping/" + documentMetadata.getTypeName();

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

    private void deleteIndex(ElasticsearchDocumentMetadata elasticsearchDocumentMetadata) throws IOException {
        URL url = new URL("http://localhost:9200/" + elasticsearchDocumentMetadata.getIndexName());
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("DELETE");
        httpCon.connect();
        System.out.println("=======Delete response is ======== " + httpCon.getResponseCode());
    }

    private void createIndexAndMapping(ElasticsearchDocumentMetadata documentMetadata) throws IOException, InterruptedException {
        String indexUrlString = "http://localhost:9200/" + documentMetadata.getIndexName();
        String typeMappingString = "http://localhost:9200/" + documentMetadata.getIndexName() + "/_mapping/" + documentMetadata.getTypeName();

        int indexResponse = doHttpPut(indexUrlString, null);
        System.out.println("========= Index creation response == " + indexResponse);

        Thread.sleep(1000);

        if( indexResponse == 200)
            System.out.println("========= Type creation response == " + doHttpPut(typeMappingString, documentMetadata.toMapping()));
    }

    private int doHttpPut(String urlString, String body) throws IOException  {
        URL url = new URL(urlString);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        httpCon.setRequestProperty("content-type", "application/json");
        httpCon.setRequestProperty("Accept", "application/json");
        OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
        if(body!= null)
            out.write(body);
        out.flush();
        out.close();

        return httpCon.getResponseCode();
    }
}
