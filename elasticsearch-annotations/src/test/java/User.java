import com.vossie.elasticsearch.annotations.ElasticsearchField;
import com.vossie.elasticsearch.annotations.ElasticsearchType;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 12:32
 */
@ElasticsearchType(index = "twitter", source = true, type = "twitterUser")
public class User {

    @ElasticsearchField(type = ElasticsearchField.Type.STRING, analyzer = "not_analyzed", isDefaultSortByField = true)
    private String user;

    @ElasticsearchField(type = ElasticsearchField.Type.DATE)
    private String dateOfBirth;

    @ElasticsearchField(type = ElasticsearchField.Type.GEO_POINT)
    private Location location;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
