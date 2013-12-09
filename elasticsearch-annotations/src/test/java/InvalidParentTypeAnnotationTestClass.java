import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchType;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * User: cvosloo
 * Date: 09/12/2013
 * Time: 21:12
 */
@ElasticsearchType(index = "test", parent = Object.class)
public class InvalidParentTypeAnnotationTestClass {

    @ElasticsearchField(type = ElasticsearchField.Type.STRING)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
