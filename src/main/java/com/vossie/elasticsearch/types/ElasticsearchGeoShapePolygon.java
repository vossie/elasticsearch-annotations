package com.vossie.elasticsearch.types;

import com.vossie.elasticsearch.annotations.enums.FieldType;
import com.vossie.elasticsearch.annotations.enums.GeoShapeType;

/**
 * Created by rpatadia on 03/01/2014.
 */
public class ElasticsearchGeoShapePolygon extends ElasticsearchGeoShape<double[][][]> {

    public ElasticsearchGeoShapePolygon() {
        super(GeoShapeType.POLYGON.toString());
    }

    public void setCoordinates (double[][][] coordinates){
        validatePolygonCoordinates(coordinates);
        super.setCoordinates(coordinates);
    }

    /**
     *
     * @param coordinates
     * @return
     */
    private void validatePolygonCoordinates (double [][][] coordinates){
        int lengthFirstArray = coordinates.length;
        int lengthSecondArray = coordinates[0].length;
        int lengthThirdArray = coordinates[0][0].length;
        if (!(lengthSecondArray>2)) {
            throw new RuntimeException("Polygon should have more than 2 sets of co-ordinates.");
        }
        if (!(lengthThirdArray==2)){
            throw new RuntimeException("Coordinates should be expressed as longitude and latitude only.");
        }
        for (int i =0; i<lengthFirstArray;i++){
            if(!(coordinates[i][0][0]==coordinates[i][lengthSecondArray-1][0]&&coordinates[i][0][1]==coordinates[i][lengthSecondArray-1][1])){
                throw new RuntimeException("Polygon should be closed. The first and last points in each list must be the same.");
            }

        }
    }
}
