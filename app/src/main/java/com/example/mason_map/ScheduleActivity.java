package com.example.mason_map;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ScheduleActivity extends Fragment {


    public ArrayList<Event> events;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.activity_schedule,viewGroup,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.events = new ArrayList<>();

        //this.generateEventList();
    }

    /*
      Generate the Event List
     */
    private void generateEventList(){
        ListView listView = this.getView().findViewById(R.id.listView);
        ListAdapter customListAdapter = new ListAdapter(this.getActivity(), R.layout.feed_event, this.events);
        listView.setAdapter(customListAdapter);
    }
}
