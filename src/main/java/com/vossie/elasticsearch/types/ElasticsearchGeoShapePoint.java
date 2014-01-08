package com.vossie.elasticsearch.types;

import com.vossie.elasticsearch.annotations.enums.GeoShapeType;

/**
 * Created by rpatadia on 07/01/2014.
 */
public class ElasticsearchGeoShapePoint extends ElasticsearchGeoShape<double []> {

    public ElasticsearchGeoShapePoint() {
        super(GeoShapeType.POINT.toString());
    }

    public void setCoordinates (double[] coordinates){
        validatePointCoordinates(coordinates);
        super.setCoordinates(coordinates);
    }

    /**
     *
     * @param coordinates
     * @return
     */
    private void validatePointCoordinates (double [] coordinates){
        if (!(coordinates.length==2)) {
            throw new RuntimeException("Point should be expressed as longitude and latitude only.");
        }
    }
}
