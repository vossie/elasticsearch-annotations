package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.common.ElasticsearchDocumentMetadata;
import com.vossie.elasticsearch.annotations.enums.ElasticsearchType;
import com.vossie.elasticsearch.annotations.exceptions.ClassNotAnnotated;
import com.vossie.elasticsearch.annotations.exceptions.InvalidAttributeForType;
import com.vossie.elasticsearch.annotations.exceptions.InvalidParentDocumentSpecified;
import com.vossie.elasticsearch.annotations.util.ESTypeAttributeConstraints;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * com.vossie.test.User: cvosloo
 * Date: 09/12/2013
 * Time: 21:00
 */
public class TestElasticsearchMapping {

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
}
