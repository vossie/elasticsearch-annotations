package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.common.ElasticsearchDocumentMetadata;
import com.vossie.elasticsearch.annotations.enums.ElasticsearchType;
import com.vossie.elasticsearch.annotations.exceptions.ClassNotAnnotated;
import com.vossie.elasticsearch.annotations.exceptions.InvalidAttributeForType;
import com.vossie.elasticsearch.annotations.exceptions.InvalidParentDocumentSpecified;
import com.vossie.elasticsearch.annotations.util.ESTypeAttributeConstraints;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * com.vossie.test.User: cvosloo
 * Date: 09/12/2013
 * Time: 21:00
 */
public class TestElasticsearchMapping {

    private static Node node;

    @BeforeClass
    public static void init() {

        node = NodeBuilder.nodeBuilder()
                .node();

        ClusterHealthResponse clusterHealthResponse = node
                .client()
                .admin()
                .cluster()
                .prepareHealth()
                .setWaitForGreenStatus()
                .execute()
                .actionGet();
    }

    @Test(expected = ClassNotAnnotated.class)
    public void testForClassNotAnnotatedException() throws InvalidParentDocumentSpecified, ClassNotAnnotated, InvalidAttributeForType {

        ElasticsearchMapping.getMapping(Object.class);
    }

    @Test(expected = InvalidParentDocumentSpecified.class)
    public void testInvalidParentTypeSpecifiedException() throws InvalidParentDocumentSpecified, ClassNotAnnotated, InvalidAttributeForType {

        ElasticsearchMapping.getMapping(InvalidParentTypeAnnotationTestClass.class);
    }

    @Test
    public void testGettingTweetMapping() throws InvalidParentDocumentSpecified, ClassNotAnnotated, IOException, JSONException, InvalidAttributeForType {

        ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.getMapping(Tweet.class);
        String json = documentMetadata.toJson();

        String expected = "{\"tweet\":{\"_parent\":{\"type\":\"twitterUser\"},\"properties\":{\"user\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"postDate\":{\"type\":\"date\"},\"message\":{\"type\":\"string\"}}}}";
        JSONAssert.assertEquals(expected,json,false);
    }

    @Test
    public void testGettingParentMappingFromChild() throws InvalidParentDocumentSpecified, ClassNotAnnotated, IOException, JSONException, InvalidAttributeForType {

        String json = ElasticsearchMapping.getMapping(Tweet.class).getParent().toJson();
        String expected = "{\"twitterUser\":{\"properties\":{\"user\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"dateOfBirth\":{\"type\":\"date\"},\"location\":{\"type\":\"geo_point\",\"properties\":{\"lat\":{\"type\":\"double\"},\"lon\":{\"type\":\"double\"}}}}}}";
        JSONAssert.assertEquals(expected,json,false);
    }

    @Test
    public void testGettingTweetParentMapping() {

        Tweet tweet = new Tweet();
        tweet.setUser("abc");
        assertEquals("Parent ID retrieved", "abc", ElasticsearchMapping.getParentId(tweet));
    }

    @Test
    public void testRead() throws Exception {

        new ESTypeAttributeConstraints();
    }

    @Test
    public void testValidatingTypeAttributeTrue() throws Exception {

        assertTrue(new ESTypeAttributeConstraints().isValidAttributeForType(ElasticsearchType.STRING, "type"));
    }

    @Test
    public void testValidatingTypeAttributeFalse() throws Exception {

        assertFalse(new ESTypeAttributeConstraints().isValidAttributeForType(ElasticsearchType.GEO_POINT, "term_vector"));
    }

    @Test(expected = InvalidAttributeForType.class)
    public void testSettingAnInvalidAttributeForType() throws ClassNotAnnotated, InvalidParentDocumentSpecified, InvalidAttributeForType {

        ElasticsearchMapping.getMapping(UserWithInvalidAttribute.class);
    }

    @Test
    public void testSavingMappingToElasticInstance() throws InvalidAttributeForType, ClassNotAnnotated, InvalidParentDocumentSpecified, IOException, JSONException {

        ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.getMapping(User.class);
        assertTrue(createIndex(User.class));
        MappingMetaData json = getMapping(documentMetadata.getIndexName(), documentMetadata.getTypeName());
    }

    /******************
     * Helper Methods *
     ******************/

    public MappingMetaData getMapping(String indexName, String typeName) {

        ClusterState cs = node.client().admin().cluster().prepareState().setFilterIndices(indexName).execute().actionGet().getState();
        IndexMetaData imd = cs.getMetaData().index(indexName);

        return imd.mapping(typeName);
    }

    public boolean indexExists(String index) {

        return node.client().admin().indices().exists(new IndicesExistsRequest(index)).actionGet().isExists();
    }

    public boolean createIndex(Class<?> clazz) throws ClassNotAnnotated, InvalidParentDocumentSpecified, InvalidAttributeForType, IOException {

        ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.getMapping(clazz);
        createIndex(documentMetadata.getIndexName());

        node.client()
                .admin ()
                .indices()
                .preparePutMapping (documentMetadata.getIndexName())
                .setType (documentMetadata.getTypeName())
                .setSource(documentMetadata.toJson())
                .execute()
                .actionGet();

        return true;
    }

    public boolean createIndex(String index) {

        try {
            if(!indexExists(index)) {

                CreateIndexRequestBuilder createIndexRequestBuilder = node.client().admin().indices().prepareCreate(index);
                return createIndexRequestBuilder.execute().actionGet().isAcknowledged();
            }
        } catch (IndexAlreadyExistsException e) {
            return true;
        }
        return true;
    }
}
