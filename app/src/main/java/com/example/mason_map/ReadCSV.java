package com.example.mason_map;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ReadCSV {
    ArrayList<Location> readFile(InputStream input) throws IOException {
        ArrayList<Location> locations = new ArrayList<>();
        BufferedReader reader;

        reader = new BufferedReader(new InputStreamReader(input));

        String data;
        while((data = reader.readLine()) != null){

            //Get the data per comma...
            String[] row = data.split(",");

            Location location = new Location();

            location.setCode(row[0]);
            location.setName(row[1]);

            //Likely Blank Lines... That's Okay..
            location.setOldCode(row[2]);
            location.setOldName(row[3]);

            //TODO: ADD LAT LONG THING HERE

            locations.add(location);
        }

        input.close();

        return locations;
    }
}
