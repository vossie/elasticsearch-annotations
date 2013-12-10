package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.exceptions.ClassNotAnnotated;
import com.vossie.elasticsearch.annotations.exceptions.InvalidParentDocumentSpecified;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * com.vossie.test.User: cvosloo
 * Date: 09/12/2013
 * Time: 21:00
 */
public class TestElasticsearchMapping {

    @Test(expected = ClassNotAnnotated.class)
    public void testForClassNotAnnotatedException() throws InvalidParentDocumentSpecified, ClassNotAnnotated {

        ElasticsearchMapping.getMapping(Object.class);
    }

    @Test(expected = InvalidParentDocumentSpecified.class)
    public void testInvalidParentTypeSpecifiedException() throws InvalidParentDocumentSpecified, ClassNotAnnotated {

        ElasticsearchMapping.getMapping(InvalidParentTypeAnnotationTestClass.class);
    }

    @Test
    public void testGettingTweetMapping() throws InvalidParentDocumentSpecified, ClassNotAnnotated, IOException, JSONException {

        String actual = ElasticsearchMapping.getMapping(Tweet.class).toJson();
        String expected = "{\"tweet\":{\"_parent\":{\"type\":\"twitterUser\"},\"properties\":{\"user\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"postDate\":{\"type\":\"date\"},\"message\":{\"type\":\"string\"}}}}";
        JSONAssert.assertEquals(expected,actual,false);
    }

    @Test
    public void testGettingParentMappingFromChild() throws InvalidParentDocumentSpecified, ClassNotAnnotated, IOException, JSONException {

        String actual = ElasticsearchMapping.getMapping(Tweet.class).getParent().toJson();
        String expected = "{\"twitterUser\":{\"properties\":{\"user\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"dateOfBirth\":{\"type\":\"date\"},\"location\":{\"type\":\"geo_point\",\"properties\":{\"lat\":{\"type\":\"double\"},\"lon\":{\"type\":\"double\"}}}}}}";
        JSONAssert.assertEquals(expected,actual,false);
    }

    @Test
    public void testGettingTweetParentMapping() {

        Tweet tweet = new Tweet();
        tweet.setUser("abc");
        assertEquals("Parent ID retrieved", "abc", ElasticsearchMapping.getParentId(tweet));
    }
}
