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

    public static String TAG = "Schedule Activity";

    public static ArrayList<Event> events;

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
        this.generateEventList();
    }
    /*
      Generate the Event List
     */
    private void generateEventList(){
        ArrayList<Event> copy = (ArrayList<Event>) events.clone();
        ScheduleAdapter customListAdapter = new ScheduleAdapter(this.getActivity(), R.layout.feed_event, copy);
        ListView listView = this.getView().findViewById(R.id.faveView);
        listView.setAdapter(customListAdapter);
    }

    /*
        Add or remove an event to the Schedule...
     */
    public static void populateListView(Event event){
        if(events == null){
            events = new ArrayList<>();
        }

        if(events.contains(event)){
            events.remove(event);
            Log.d(TAG, "Removed event" + event.toString());
        }
        else {
            events.add(event);
            Log.d(TAG, "Added event" + event.toString());
        }
    }
}
