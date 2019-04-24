package com.example.mason_map;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ReadCSV {

    private static final String TAG = "ReadCSV";

    private ArrayList<Location> locations;

    public ReadCSV(){
        this.locations = new ArrayList<>();
    }

    public void readFile(InputStream input) throws IOException {
        locations = new ArrayList<>();
        BufferedReader reader;

        //Fetch File
        reader = new BufferedReader(new InputStreamReader(input));

        String data;
        while((data = reader.readLine()) != null){

            //Get the data per comma...
            String[] row = data.split(",");

            //Make a new location:
            Location location = new Location();

            //Set the data from the file
            location.setCode(row[0]);
            location.setName(row[1]);

            //Likely Blank Lines... That's Okay..
            location.setOldCode(row[2]);
            location.setOldName(row[3]);

            if(row[4]!="" && row[5]!="") {
                //Get the location
                double lat = Double.parseDouble(row[4]);
                double lon = Double.parseDouble(row[5]);

                location.setLatlng(new LatLng(lat,lon));
            }
            //Add the new location
            locations.add(location);
        }

        //Close stuff down
        input.close();
        reader.close();

    }

    public ArrayList<Location> getLocations() {
        return this.locations;
    }

    //Finds the title of a Lat/Lng match.
    public String getTitle(LatLng search){

        for(Location location : this.locations){
            if(location.getLatlng().equals(search)){
                //Location found, return name
                return location.getName();
            }
        }
        //Cannot find name.
        return "Event";
    }

    //Gets the Lat Lng from a given event
    /*
      Currently not fully implemented, it needs some work, but it is operational.
     */
    public LatLng getLatLng(Event event) {
        Location location = null;
        LatLng result;
        String eventLocation = getParsedName(event.getLocation());
        Log.d(TAG,"Parsed Name is : " + eventLocation);

        //First check to see if it matches the building name::
        for (Location local : this.locations) {
            if (eventLocation.contains(local.getParsedName()) ||
                    eventLocation.equalsIgnoreCase(local.getCode())) {
                location = local;
                break;
            }

        }

        // Was still not found, lets set it to GMU itself....
        if (location == null) {
            Log.e(TAG, "Could not find location, " + event.getLocation());
            result = new LatLng(38.8315, -77.3115);
        }
        //Location was found, set result to location.
        else {
            Log.d(TAG, "Location Assumed: " + location.getName() + "; Real Location: " + event.getLocation());
            result = location.getLatlng();

            //Just a Null Condition, never reached unless file is missing data.
            if (result == null) {
                Log.e(TAG, "Could not find coordinates, " + location.getName());
                result = new LatLng(38.8315, -77.3115);
            }
        }

        return result;
    }
    //Gets the Lat Lng from a given string from search bar of map
    public LatLng getLatLng(String location) {
        Location location1 = null;
        LatLng result;
        String eventLocation = getParsedName(location);

        //First check to see if it matches the building name::
        for (Location local : this.locations) {
            if (eventLocation.contains(local.getParsedName()) ||
                    eventLocation.equals(local.getCode())) {
                location1 = local;
                break;
            }
        }

        // Was still not found, lets set it to GMU itself....
        if (location1 == null) {
            Log.e(TAG, "Could not find location, " + location);
            result = new LatLng(38.8315, -77.3115);
        }
        //Location was found, set result to location.
        else {
            Log.d(TAG, "Location Assumed: " + location1.getName() + "; Real Location: " + location);
            result = location1.getLatlng();

            //Just a Null Condition, never reached unless file is missing data.
            if (result == null) {
                Log.e(TAG, "Could not find coordinates, " + location1.getName());
                result = new LatLng(38.8315, -77.3115);
            }
        }

        return result;
    }

    public String[] locationsStrings(){
        String[] locations = new String[this.locations.size()];
        int i = 0;
        for(Location local:this.locations){
            locations[i] = local.getName();
            i++;
        }
        return locations;
    }

    private String getParsedName(String result){

        result = result.toLowerCase();
        if(result.contains("room")){
            result = result.substring(0,result.indexOf("room"));
        }
        result = result.replace("building", "");
        result = result.replace("library", "");
        result = result.replace("hall", "");
        result = result.replace("park", "");
        result = result.replace("the", "");
        result = result.replace("  ", " ");
        result = result.replace(" ", "");
        result = result.replace("-","");
        result = result.replace("room","");
        result = result.replace("Nguyen","");
        result = result.replace("meeting","");

        return result;
    }
}
