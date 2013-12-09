import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchType;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 12:28
 */
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
