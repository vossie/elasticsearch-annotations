package com.vossie.test;

import com.google.gson.Gson;
import com.vossie.models.Coordinates;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

/**
 * Created by rpatadia on 03/01/2014.
 */
public class GeoShapeTest {

    @Test
    public void parsePolygonCoordinatesValid() throws IOException, JSONException {

        Coordinates coordinates = new Coordinates();
        double[][][] arr = new double[2][5][2];

        arr[0][0][0] = 100.0;
        arr[0][0][1] = 0.0;

        arr[0][1][0] = 101.0;
        arr[0][1][1] = 0.0;

        arr[0][2][0] = 101.0;
        arr[0][2][1] = 1.0;

        arr[0][3][0] = 100.0;
        arr[0][3][1] = 0.0;

        arr[0][4][0] = 100.0;
        arr[0][4][1] = 0.0;

        coordinates.setShapePolygon(arr);
        String json = new Gson().toJson(coordinates);

        String expectedJson = "{\"shapePolygon\":{\"type\":\"polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,0.0],[100.0,0.0]],[[0.0,0.0],[0.0,0.0],[0.0,0.0],[0.0,0.0],[0.0,0.0]]]}}}";

        JSONAssert.assertEquals(expectedJson, json, false);

    }

    @Test(expected = NullPointerException.class)
    public void parsePolygonCoordinatesIncompleteInitialisation() throws IOException {

        Coordinates coordinates = new Coordinates();
        double [][][] arr = new double[2][][];

        arr[0][0][0] = 1.0;
        arr[1][1][1] = 1.1;

        coordinates.setShapePolygon(arr);

        String json = new Gson().toJson(coordinates);

        System.out.println("Array not initialised correctly");
    }

    @Test(expected = RuntimeException.class)
    public void parsePolygonCoordinatesOnlyTwoValues() throws IOException {
        Coordinates coordinates = new Coordinates();
        double [][][] arr = new double[1][1][2];
        arr [0][0][0] = 0.0;
        arr [0][0][1] = 1.0;

        coordinates.setShapePolygon(arr);
    }

    @Test(expected = RuntimeException.class)
    public void parsePolygonCoordinatesNotClosedPolygon() throws IOException {
        Coordinates coordinates = new Coordinates();
        double [][][] arr = new double[2][5][2];
        arr[0][0][0] = 100.0;
        arr[0][0][1] = 0.0;

        arr[0][1][0] = 101.0;
        arr[0][1][1] = 0.0;

        arr[0][2][0] = 101.0;
        arr[0][2][1] = 1.0;

        arr[0][3][0] = 100.0;
        arr[0][3][1] = 0.0;

        arr[0][4][0] = 101.0;
        arr[0][4][1] = 0.1;

        coordinates.setShapePolygon(arr);
    }

    @Test(expected = RuntimeException.class)
    public void parsePolygonCoordinatesIncorrectThirdArraySize() throws IOException {
        Coordinates coordinates = new Coordinates();
        double [][][] arr = new double[2][5][1];
        arr[0][0][0] = 100.0;

        arr[0][1][0] = 101.0;

        arr[0][2][0] = 101.0;

        arr[0][3][0] = 100.0;

        arr[0][4][0] = 100.0;

        coordinates.setShapePolygon(arr);
    }

    @Test
    public void parseMultiPolygonCoordinatesValid() throws IOException, JSONException {

        Coordinates coordinates = new Coordinates();
        double[][][][] arr = new double[2][2][5][2];

        arr[0][0][0][0] = 100.0;
        arr[0][0][0][1] = 0.0;

        arr[0][0][1][0] = 101.0;
        arr[0][0][1][1] = 0.0;

        arr[0][0][2][0] = 101.0;
        arr[0][0][2][1] = 1.0;

        arr[0][0][3][0] = 100.0;
        arr[0][0][3][1] = 0.0;

        arr[0][0][4][0] = 100.0;
        arr[0][0][4][1] = 0.0;

        coordinates.setShapeMultiPolygon(arr);
        String json = new Gson().toJson(coordinates);

        String expectedJson = "{\"shapeMultiPolygon\":{\"type\":\"multipolygon\",\"coordinates\":[[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,0.0],[100.0,0.0]],[[0.0,0.0],[0.0,0.0],[0.0,0.0],[0.0,0.0],[0.0,0.0]]],[[[0.0,0.0],[0.0,0.0],[0.0,0.0],[0.0,0.0],[0.0,0.0]],[[0.0,0.0],[0.0,0.0],[0.0,0.0],[0.0,0.0],[0.0,0.0]]]]}}";

        JSONAssert.assertEquals(expectedJson,json,false);

    }

    @Test(expected = NullPointerException.class)
    public void parseMultiPolygonCoordinatesIncompleteInitialisation() throws IOException {

        Coordinates coordinates = new Coordinates();
        double [][][][] arr = new double[2][][][];

        arr[0][0][0][0] = 1.0;
        arr[1][1][1][1] = 1.1;

        coordinates.setShapeMultiPolygon(arr);

        String json = new Gson().toJson(coordinates);

        System.out.println("Array not initialised correctly");
    }

    @Test(expected = RuntimeException.class)
    public void parseMultiPolygonCoordinatesOnlyTwoValues() throws IOException {
        Coordinates coordinates = new Coordinates();
        double [][][][] arr = new double[1][1][1][2];
        arr [0][0][0][0] = 0.0;
        arr [0][0][0][1] = 1.0;

        coordinates.setShapeMultiPolygon(arr);
    }

    @Test(expected = RuntimeException.class)
    public void parseMultiPolygonCoordinatesIncorrectFourthArraySize() throws IOException {
        Coordinates coordinates = new Coordinates();
        double [][][][] arr = new double[2][2][5][1];
        arr[0][0][0][0] = 100.0;

        arr[0][0][1][0] = 101.0;

        arr[0][0][2][0] = 101.0;

        arr[0][0][3][0] = 100.0;

        arr[0][0][4][0] = 100.0;

        coordinates.setShapeMultiPolygon(arr);
    }

    @Test(expected = RuntimeException.class)
    public void parseMultiPolygonCoordinatesNotClosedPolygon() throws IOException {
        Coordinates coordinates = new Coordinates();
        double [][][][] arr = new double[2][2][5][2];
        arr[0][0][0][0] = 100.0;
        arr[0][0][0][1] = 0.0;

        arr[0][0][1][0] = 101.0;
        arr[0][0][1][1] = 0.0;

        arr[0][0][2][0] = 101.0;
        arr[0][0][2][1] = 1.0;

        arr[0][0][3][0] = 100.0;
        arr[0][0][3][1] = 0.0;

        arr[0][0][4][0] = 101.0;
        arr[0][0][4][1] = 0.1;

        coordinates.setShapeMultiPolygon(arr);
    }

    @Test
    public void parseEnvelopeCoordinatesValid() throws IOException, JSONException {

        Coordinates coordinates = new Coordinates();
        double[][] arr = new double[2][2];

        arr[0][0] = 100.0;
        arr[0][1] = 0.0;

        arr[1][0] = 101.0;
        arr[1][1] = 0.0;

        coordinates.setShapeEnvelope(arr);
        String json = new Gson().toJson(coordinates);

        String expectedJson = "{\"shapeEnvelope\":{\"type\":\"envelope\",\"coordinates\":[[100.0,0.0],[101.0,0.0]]}}";

        JSONAssert.assertEquals(expectedJson,json,false);

    }

    @Test(expected = NullPointerException.class)
    public void parseEnvelopeCoordinatesIncompleteInitialisation() throws IOException {

        Coordinates coordinates = new Coordinates();
        double [][] arr = new double[2][];

        arr[0][0] = 1.0;
        arr[1][1] = 1.1;

        coordinates.setShapeEnvelope(arr);

        String json = new Gson().toJson(coordinates);

        System.out.println("Array not initialised correctly");
    }

    @Test(expected = RuntimeException.class)
    public void parseEnvelopeCoordinatesSingleValue() throws IOException {
        Coordinates coordinates = new Coordinates();
        double [][] arr = new double[1][2];
        arr [0][0] = 0.0;
        arr [0][1] = 1.0;

        coordinates.setShapeEnvelope(arr);
    }

    @Test(expected = RuntimeException.class)
    public void parsePolygonCoordinatesIncorrectSecondArraySize() throws IOException {
        Coordinates coordinates = new Coordinates();
        double [][] arr = new double[2][1];
        arr[0][0] = 100.0;

        arr[1][0] = 101.0;

        coordinates.setShapeEnvelope(arr);
    }

    @Test
    public void parsePointCoordinatesValid() throws IOException, JSONException {

        Coordinates coordinates = new Coordinates();
        double[] arr = new double [2];

        arr[0] = 100.0;
        arr[1] = 0.0;

        coordinates.setShapePoint(arr);
        String json = new Gson().toJson(coordinates);

        String expectedJson = "{\"shapePolygon\":{\"type\":\"point\",\"coordinates\":[100.0,0.0]}}";

        JSONAssert.assertEquals(expectedJson,json,false);

    }

    @Test(expected = RuntimeException.class)
    public void parsePointCoordinatesIncorrectArraySize() throws IOException {
        Coordinates coordinates = new Coordinates();
        double [] arr = new double[3];
        arr[0] = 100.0;

        arr[1] = 101.0;

        arr[2] = 100.0;

        coordinates.setShapePoint(arr);
    }

}
