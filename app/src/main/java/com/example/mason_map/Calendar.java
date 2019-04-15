package com.example.mason_map;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;

public  class Calendar {
    public static final String CALANDER_ID = "content://com.android.calendar/events";

    private static Context context;

    public Calendar(Context _context){
        context = _context;
    }

    public static void ExportEvents(ArrayList<Event> events) {

        if(events.size() == 0){
            return;
        }

        Uri calendar = Uri.parse("content://calendar/calendars");

        for(Event event : events) {
            ContentValues list = new ContentValues();

            list.put(CALANDER_ID, 1);
            list.put("title", event.getTitle());
            list.put("eventLocation", event.getLocation());
            list.put("dtstart", event.getStart());
            list.put("dtend", event.getEnd());
            list.put("eventStatus", !event.isCancelled());

            context.getContentResolver().insert(calendar, list);
        }

    }
}
