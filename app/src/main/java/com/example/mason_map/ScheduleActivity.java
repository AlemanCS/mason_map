package com.example.mason_map;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ScheduleActivity extends Fragment {

    private static String TAG = "Schedule Activity";

    private static ArrayList<Event> events;

    private static Calendar calendar;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.activity_schedule,viewGroup,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //It may already be defined...
        if(events == null) {
            events = new ArrayList<>();
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        calendar = new Calendar(this.getActivity());
        this.generateEventList();

        this.getView().findViewById(R.id.export).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // calendar.ExportEvents(events);
                //TODO: Fix Calendar, it currently cannot identify the system calendar.
            }
        });
        this.getView().findViewById(R.id.addEvent).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment newAct = new NewEventActivity();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newAct).commit();

            }
        });
    }
    /*
      Generate the Event List
     */
    private void generateEventList(){
        ArrayList<Event> copy = (ArrayList<Event>) events.clone();
        ScheduleAdapter customListAdapter = new ScheduleAdapter(this.getActivity(), R.layout.feed_event, copy, getActivity().getSupportFragmentManager(),new MapsActivity());
        ListView listView = this.getView().findViewById(R.id.faveView);
        listView.setAdapter(customListAdapter);
    }

    /*
        Add or remove an event to the Schedule...
     */
    public static boolean populateListView(Event event){
        if(events == null){
            events = new ArrayList<>();
        }

        if(events.contains(event)){
            events.remove(event);
            Log.d(TAG, "Removed event" + event.toString());
            return false;
        }
        else {
            events.add(event);
            Log.d(TAG, "Added event" + event.toString());
            return true;
        }
    }

    //Checks to see if there is an event within the schedule, based on title.
    public static boolean isPresent(Event event){
        if(events == null){
            return false;
        }
        for(Event e : events){
            if(e.getTitle().equals(event.getTitle())){
                return true;
            }
        }
        return false;
    }
}
