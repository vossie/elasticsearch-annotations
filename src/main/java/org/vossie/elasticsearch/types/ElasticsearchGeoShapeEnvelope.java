package org.vossie.elasticsearch.types;

import org.vossie.elasticsearch.annotations.enums.FieldType;
import org.vossie.elasticsearch.annotations.enums.GeoShapeType;

/**
 * Created by rpatadia on 03/01/2014.
 */
public class ElasticsearchGeoShapeEnvelope extends ElasticsearchGeoShape<double[][]> {

    public ElasticsearchGeoShapeEnvelope() {
        super(GeoShapeType.ENVELOPE.toString());
    }

    public void setCoordinates (double[][] coordinates){
        validateEnvelopeCoordinates(coordinates);
        super.setCoordinates(coordinates);
    }

    /**
     *
     * @param coordinates
     * @return
     */
    private void validateEnvelopeCoordinates (double [][] coordinates){
        if (!(coordinates.length==2)) {
            throw new RuntimeException("Envelope should have only 2 sets of co-ordinates.");
        }
        if (!(coordinates[0].length==2)){
            throw new RuntimeException("Coordinates should be expressed as longitude and latitude only.");
        }
    }

}
