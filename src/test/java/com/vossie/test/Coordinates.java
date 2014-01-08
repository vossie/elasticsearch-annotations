package com.vossie.test;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchType;
import com.vossie.elasticsearch.types.ElasticsearchGeoShape;
import com.vossie.elasticsearch.types.ElasticsearchGeoShapeEnvelope;
import com.vossie.elasticsearch.types.ElasticsearchGeoShapeMultipolygon;
import com.vossie.elasticsearch.types.ElasticsearchGeoShapePoint;
import com.vossie.elasticsearch.types.ElasticsearchGeoShapePolygon;
import com.vossie.elasticsearch.annotations.enums.FieldType;

/**
 * Created by rpatadia on 03/01/2014.
 */
@ElasticsearchDocument(
        index = "myIndex"
)
public class Coordinates {

    @ElasticsearchType(type = FieldType.GEO_SHAPE, tree = "quadtree", precision = "1m")
    private ElasticsearchGeoShape shapePoint;

    @ElasticsearchType(type = FieldType.GEO_SHAPE, tree = "quadtree", precision = "1m")
    private ElasticsearchGeoShape shapePolygon;

    @ElasticsearchType(type = FieldType.GEO_SHAPE, tree = "quadtree", precision = "1m")
    private ElasticsearchGeoShape shapeEnvelope;

    @ElasticsearchType(type = FieldType.GEO_SHAPE, tree = "quadtree", precision = "1m")
    private ElasticsearchGeoShape shapeMultiPolygon;


    public ElasticsearchGeoShape getShapePoint() {
        return shapePoint;
    }

    public void setShapePoint(ElasticsearchGeoShape shapePoint) {
        this.shapePoint = shapePoint;
    }

    public void setShapePoint(double[] coordinates) {
        ElasticsearchGeoShape s = new ElasticsearchGeoShapePoint();
        s.setCoordinates(coordinates);
        this.shapePolygon = s;
    }

    public ElasticsearchGeoShape getShapePolygon() {
        return shapePolygon;
    }

    public void setShapePolygon(ElasticsearchGeoShape shapePolygon) {
        this.shapePolygon = shapePolygon;
    }

    public void setShapePolygon(double[][][] coordinates) {
        ElasticsearchGeoShape s = new ElasticsearchGeoShapePolygon();
        s.setCoordinates(coordinates);
        this.shapePolygon = s;
    }

    public ElasticsearchGeoShape getShapeEnvelope() {
        return shapeEnvelope;
    }

    public void setShapeEnvelope(ElasticsearchGeoShape shapeEnvelope) {
        this.shapeEnvelope = shapeEnvelope;
    }

    public void setShapeEnvelope(double[][] coordinates) {
        ElasticsearchGeoShape s = new ElasticsearchGeoShapeEnvelope();
        s.setCoordinates(coordinates);
        this.shapeEnvelope = s;
    }

    public ElasticsearchGeoShape getShapeMultiPolygon() {
        return shapeMultiPolygon;
    }

    public void setShapeMultiPolygon(ElasticsearchGeoShape shapeMultiPolygon) {
        this.shapeMultiPolygon = shapeMultiPolygon;
    }


    public void setShapeMultiPolygon(double[][][][] coordinates) {
        ElasticsearchGeoShape s = new ElasticsearchGeoShapeMultipolygon();
        s.setCoordinates(coordinates);
        this.shapeMultiPolygon = s;
    }

}
