package org.vossie.models;

import org.vossie.elasticsearch.annotations.ElasticsearchDocument;
import org.vossie.elasticsearch.annotations.ElasticsearchIndex;
import org.vossie.elasticsearch.annotations.ElasticsearchType;
import org.vossie.elasticsearch.annotations.enums.BooleanValue;
import org.vossie.elasticsearch.annotations.enums.FieldType;

import java.util.List;

@ElasticsearchIndex(_indexName = "user")
@ElasticsearchDocument()
public class User {

    @ElasticsearchType(type = FieldType.KEYWORD, store = BooleanValue.TRUE)
    private String user;

    @ElasticsearchType(type = FieldType.DATE, format = "dateOptionalTime")
    private String dateOfBirth;

    @ElasticsearchType(type = FieldType.GEO_POINT)
    private Location location;

    @ElasticsearchType( type = FieldType.NESTED)
    private List<Cities> citiesVisited;

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

    public List<Cities> getCitiesVisited() {
        return citiesVisited;
    }

    public void setCitiesVisited(List<Cities> citiesVisited) {
        this.citiesVisited = citiesVisited;
    }
}
