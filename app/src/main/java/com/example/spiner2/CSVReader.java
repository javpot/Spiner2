package com.example.spiner2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public List<String[]> readCSV(InputStream inputStream) throws IOException {
        List<String[]> resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            resultList.add(row);
        }
        reader.close();
        return resultList;
    }
}
