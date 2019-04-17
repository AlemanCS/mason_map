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

    public String toString(){
        return String.format("Code: %s, Building: %s, at: LatLong: %s", this.code, this.name, this.latlng.toString());
    }
}
