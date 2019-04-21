package com.example.mason_map;

import java.util.List;
import java.util.Locale;

public class Event {
    //All of the things a event is composed of:
    private String title;
    private String link;
    private String description;
    private List<String> category;
    private String start;
    private String end;
    private String location;
    private boolean cancelled;


    public Event(){
        this.cancelled = false;
    }

    public Event(String title, String link, String description, List<String> category, String start, String end, String location) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.category = category;
        this.start = this.parseTime(start);
        this.end = this.parseTime(end);
        this.location = location;
        this.cancelled = false;
    }

    /*
      Remove the Seconds and GMT from the time slot along with adding AM/ PM and Changing form Military time.
     */
    private String parseTime(String time){
        String prefix, hour, min;
        int h;

        //Remove the Seconds and GMT from the time slot.
        time = time.replace(":00 GMT", "");

        //Grab each section of the date.
        prefix = time.substring(0, time.length() - 5);
        hour = time.substring(time.length()-5, time.length() - 3);
        min = time.substring(time.length()-3);

        h = Integer.parseInt(hour);

        //Change From GMT to our time zone.
        h = ((h + 43 ) % 24) + 1;


        //Add PM or AM
        if(h > 12){
            min = min.concat(" PM");
        }
        else{
            min = min.concat(" AM");
        }

        //Change from Military Time to Standard.
        h = (h % 12);

        if(h == 0){
            h = 12;
        }
        return String.format(Locale.getDefault(), "%s %d%s", prefix, h, min);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title.contains("Cancelled")){
            this.cancelled = true;
        }
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = this.parseTime(start);
    }

    public void setRawStart(String start) { this.start = start; }
    public void setRawEnd(String end) { this.end = end; }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = this.parseTime(end);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public boolean isCancelled(){
        return this.cancelled;
    }
}
