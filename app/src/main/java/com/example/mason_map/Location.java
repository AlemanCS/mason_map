package com.example.mason_map;

import com.google.android.gms.maps.model.LatLng;

public class Location {
    private String name;
    private String code;

    private String oldName;
    private String oldCode;

    private LatLng latlng;

    public Location() {
        this.name = "";
        this.code = "";

        //Likely Never to be set...
        this.oldName = "";
        this.oldCode = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public void setLatlng(LatLng latlng) {
        this.latlng = latlng;
    }

    /*
     This is used to remove words that would hurt the search results, such as Building, Hall, etc.
     */
    public String getParsedName(){
        String result = this.name.toLowerCase();


        result = result.toLowerCase();
        if(result.substring(0,2).equalsIgnoreCase("jc")){
            result = result.substring(0,2);
        }

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

    public String toString(){
        return String.format("Code: %s, Building: %s, at: LatLong: %s", this.code, this.name, this.latlng.toString());
    }
}
