package com.example.mason_map;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ReadCSV {



    public ReadCSV(){


    }
    public ArrayList<Location> readFile(InputStream input) throws IOException {
        ArrayList<Location> locations = new ArrayList<>();
        BufferedReader reader;

        reader = new BufferedReader(new InputStreamReader(input));

        String data;
        while((data = reader.readLine()) != null){

            //Get the data per comma...
            String[] row = data.split(",");


            Log.d("Length" ,"len = "+ row.length);

            Location location = new Location();


            Log.d("Building",row[0]);

            location.setCode(row[0]);
            location.setName(row[1]);

            //Likely Blank Lines... That's Okay..
            //.setOldCode(row[2]);
            //location.setOldName(row[3]);

            //TODO: ADD LAT LONG THING HERE
            if(row[4]!="" && row[5]!="") {
                double lat = Double.parseDouble(row[4]);
                double lon = Double.parseDouble(row[5]);

                Log.d("Lat Long", "lat : " + lat + " long : " + lon);
            }


            locations.add(location);
        }

        input.close();
        reader.close();

        return locations;
    }
}
