import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.exceptions.ClassNotAnnotated;
import com.vossie.elasticsearch.annotations.exceptions.InvalidParentTypeSpecified;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * User: cvosloo
 * Date: 09/12/2013
 * Time: 21:00
 */
public class TestElasticsearchMapping {

    @Test(expected = ClassNotAnnotated.class)
    public void testForClassNotAnnotatedException() throws InvalidParentTypeSpecified, ClassNotAnnotated {

        ElasticsearchMapping.getMapping(Object.class);
    }

    @Test(expected = InvalidParentTypeSpecified.class)
    public void testInvalidParentTypeSpecifiedException() throws InvalidParentTypeSpecified, ClassNotAnnotated {

        ElasticsearchMapping.getMapping(InvalidParentTypeAnnotationTestClass.class);
    }

    @Test
    public void testGettingTweetMapping() throws InvalidParentTypeSpecified, ClassNotAnnotated, IOException {

        String s = ElasticsearchMapping.getMapping(Tweet.class).toJson();
        assertEquals(
                "Mapping retrieved",
                "{\"tweet\":{\"_parent\":{\"type\":\"twitterUser\"},\"properties\":{\"user\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"postDate\":{\"type\":\"date\"},\"message\":{\"type\":\"string\"}}}}",
                s);
    }

    @Test
    public void testGettingParentMappingFromChild() throws InvalidParentTypeSpecified, ClassNotAnnotated, IOException {

        String s = ElasticsearchMapping.getMapping(Tweet.class).getParent().toJson();
        assertEquals(
                "Parent mapping found",
                "{\"twitterUser\":{\"properties\":{\"user\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"dateOfBirth\":{\"type\":\"date\"},\"location\":{\"type\":\"geo_point\",\"properties\":{\"lat\":{\"type\":\"double\"},\"lon\":{\"type\":\"double\"}}}}}}",
                s);
    }

    @Test
    public void testGettingTweetParentMapping() {

        Tweet tweet = new Tweet();
        tweet.setUser("abc");
        assertEquals("Parent ID retrieved", "abc", ElasticsearchMapping.getParentId(tweet));
    }
}
