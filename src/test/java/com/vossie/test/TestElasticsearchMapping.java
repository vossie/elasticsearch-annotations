package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.common.ElasticsearchDocumentMetadata;
import com.vossie.elasticsearch.annotations.enums.FieldType;
import com.vossie.elasticsearch.annotations.util.ESTypeAttributeConstraints;
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
import java.net.URISyntaxException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Copyright © 2013 Carel Vosloo.
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

        node
                .client()
                .admin()
                .cluster()
                .prepareHealth()
                .setWaitForGreenStatus()
                .execute()
                .actionGet();
    }

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

        String expected = "{\"tweet\":{\"_parent\":{\"type\":\"user\"},\"properties\":{\"user\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"postDate\":{\"type\":\"date\"},\"message\":{\"type\":\"string\"}}}}";
        JSONAssert.assertEquals(expected,json,false);
    }

    @Test
    public void testGettingParentMappingFromChild() throws IOException, JSONException {

        String json = ElasticsearchMapping.get(Tweet.class).getParent().toMapping();
        String expected = "{\"user\":{\"properties\":{\"dateOfBirth\":{\"format\":\"dateOptionalTime\",\"type\":\"date\"},\"location\":{\"type\":\"geo_point\",\"properties\":{\"lon\":{\"type\":\"double\"},\"lat\":{\"type\":\"double\"}}},\"citiesVisited\":{\"type\":\"nested\",\"properties\":{\"location\":{\"type\":\"geo_point\",\"properties\":{\"lon\":{\"type\":\"double\"},\"lat\":{\"type\":\"double\"}}},\"name\":{\"type\":\"string\"}}},\"user\":{\"type\":\"string\"}}}}";
        JSONAssert.assertEquals(expected,json,false);
    }

    @Test
    public void testRead() throws Exception {

        new ESTypeAttributeConstraints();
    }

    @Test
    public void testValidatingTypeAttributeTrue() throws Exception {

        assertTrue(new ESTypeAttributeConstraints().isValidAttributeForType(FieldType.STRING.toString(), "type"));
    }

    @Test
    public void testValidatingTypeAttributeFalse() throws Exception {

        assertFalse(new ESTypeAttributeConstraints().isValidAttributeForType(FieldType.GEO_POINT.toString(), "term_vector"));
    }

    @Test
    public void testSettingAnInvalidAttributeForType() {

        ElasticsearchMapping.get(UserWithInvalidAttribute.class);
    }

//    @Test
//    public void testSavingMappingToElasticInstance() throws InvalidAttributeForType, ClassNotAnnotated, InvalidParentDocumentSpecified, IOException, JSONException, UnableToLoadConstraints {
//
//        ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(User.class);
//        assertTrue(createIndex(User.class));
//        MappingMetaData json = getMapping(documentMetadata.getIndexName(), documentMetadata.getTypeName());
//    }



    @Test
    public void testMyTypeDocument () throws URISyntaxException {

        ElasticsearchMapping.get(myType.class);
    }

//    @Test
//    public void testMockEntityDao() {
//        assertCompilationSuccessful(compileTestCase(MockEntity.class));
//    }

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

    public boolean createIndex(Class<?> clazz) throws IOException {

        ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(clazz);
        createIndex(documentMetadata.getIndexName());

        node.client()
                .admin ()
                .indices()
                .preparePutMapping (documentMetadata.getIndexName())
                .setType (documentMetadata.getTypeName())
                .setSource(documentMetadata.toMapping())
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
