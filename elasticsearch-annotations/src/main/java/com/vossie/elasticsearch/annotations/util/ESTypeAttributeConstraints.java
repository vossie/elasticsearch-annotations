package com.vossie.elasticsearch.annotations.util;

import com.vossie.elasticsearch.annotations.enums.ElasticsearchType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 11/12/2013
 * Time: 09:44
 */
public class ESTypeAttributeConstraints {

    public static final String sourceFileName = "/ESAttributeConstraints.csv";

    public static Map<String, Integer> columnHeader = new HashMap<>();
    public static Map<String, String[]> dataRows = new HashMap<>();

    public ESTypeAttributeConstraints() {

        // Load the descriptor file
        if(columnHeader.size() < 1 || dataRows.size() < 1)
            parseCSV(new InputStreamReader(
                    ESTypeAttributeConstraints.class.getResourceAsStream(sourceFileName)
            ));
    }

    private void parseCSV(Reader file){

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(file);
            int rowCount = 0;

            while ((line = br.readLine()) != null) {

                String[] row = line.split(cvsSplitBy,-1);

                if(rowCount == 0) {

                    for(int i=1; i<row.length;i++)
                        columnHeader.put(row[i],i);

                } else {

                    for(int i=1; i<row.length;i++)
                        dataRows.put(row[0],row);
                }

                rowCount++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isValidAttributeForType(ElasticsearchType typeName, String attributeName) {

        // Get the column number
        Integer column = columnHeader.get(typeName.toString());

        if(!dataRows.containsKey(attributeName))
            return false;

        // Get the value
        String val = dataRows.get(attributeName)[column];

        return (!val.isEmpty());
    }

    public Set<String> getAttributeNames() {

        return Collections.unmodifiableSet(dataRows.keySet());
    }
}
