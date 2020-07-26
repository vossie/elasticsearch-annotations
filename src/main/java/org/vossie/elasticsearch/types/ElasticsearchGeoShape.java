package org.vossie.elasticsearch.types;

import org.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Created by rpatadia on 03/01/2014.
 */
public abstract class ElasticsearchGeoShape<T> {

    private String type;
    private T coordinates;

    public ElasticsearchGeoShape(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public void setCoordinates(T coordinates) {
        this.coordinates = coordinates;
    }

    public T getCoordinates(){
        return this.coordinates;
    }
}
