package com.example.mason_map;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ReadCSV {
    ArrayList<String[]> readFile(InputStream input) throws IOException {
        ArrayList<String[]> locations = new ArrayList<>();
        BufferedReader reader;

        reader = new BufferedReader(new InputStreamReader(input));

        String data;
        while((data = reader.readLine()) != null){
            String[] row = data.split(",");
            locations.add(row);
        }

        input.close();

        return locations;
    }
}
