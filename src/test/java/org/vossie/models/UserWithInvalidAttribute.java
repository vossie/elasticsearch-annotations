package org.vossie.models;

import org.vossie.elasticsearch.annotations.ElasticsearchDocument;
import org.vossie.elasticsearch.annotations.ElasticsearchIndex;
import org.vossie.elasticsearch.annotations.ElasticsearchType;
import org.vossie.elasticsearch.annotations.enums.BooleanValue;
import org.vossie.elasticsearch.annotations.enums.FieldType;
import org.vossie.elasticsearch.annotations.enums.TermVector;

@ElasticsearchIndex(_indexName = "user")
@ElasticsearchDocument(type = "twitterUser")
public class UserWithInvalidAttribute {

    @ElasticsearchType(type = FieldType.KEYWORD, index = BooleanValue.TRUE)
    private String user;

    // Term vector is not valid for FieldType.DATE type.
    @ElasticsearchType(type = FieldType.DATE, term_vector = TermVector.NO)
    private String dateOfBirth;

    @ElasticsearchType(type = FieldType.GEO_POINT)
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
