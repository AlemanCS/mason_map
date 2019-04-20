package com.example.mason_map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.net.Uri;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.model.LatLng;

public class ListAdapter extends ArrayAdapter<Event>{

    private static final String TAG = "List Adapter";

    private final Context context;
    private int resource;
    private int lastPos;
    private ArrayList<Event> events;
    private ReadCSV csvAccess = new ReadCSV();
    private FragmentManager f_manager;
    private MapsActivity map;

    private static class EventHolder{
        TextView title;
        TextView start;
        TextView end;
        TextView location;
        ImageButton link;
        ImageButton nav;
        ImageButton fav;
    }
    public ListAdapter(Context context, int resource, ArrayList<Event> objects, FragmentManager f_manager, MapsActivity map){
        super(context, resource, objects);

        this.resource = resource;
        this.f_manager = f_manager;
        this.context = context;
        this.lastPos = 0;
        this.events = new ArrayList<>();
        this.map = map;

        this.csvAccess = null;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        //When the event isn't already in the list, add to list.
        if(!events.contains(this.getItem(position))) {
            this.events.add(this.getItem(position));
        }

        //Get event data
        String title = this.getItem(position).getTitle();
        String start = this.getItem(position).getStart();
        String end = this.getItem(position).getEnd();
        String location = this.getItem(position).getLocation();
        final String link = this.getItem(position).getLink();

        try {
            final EventHolder holder;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(this.context);
                convertView = inflater.inflate(this.resource, parent, false);

                holder = new EventHolder();

                //Get the required Elements:
                holder.title = convertView.findViewById(R.id.eventTitle);
                holder.start = convertView.findViewById(R.id.eventStart);
                holder.end = convertView.findViewById(R.id.eventEnd);
                holder.location = convertView.findViewById(R.id.eventLocation);
                holder.link = convertView.findViewById(R.id.eventLink);
                holder.nav = convertView.findViewById(R.id.eventNavigation);
                holder.fav = convertView.findViewById(R.id.eventFavorite);

                //If its Favrioted, change icon:
                if(ScheduleActivity.isPresent(getItem(position))){
                    holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                }

                convertView.setTag(holder);
            }
            else{
                holder = (EventHolder) convertView.getTag();
            }
            this.lastPos = position;

            holder.title.setText(title);
            holder.start.setText(start);
            holder.end.setText(end);
            holder.location.setText(location);
            holder.link.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Navigate to web page
                    Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    context.startActivity(browser);
                }
            });
            holder.nav.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        if(csvAccess == null){
                            csvAccess = new ReadCSV();
                            csvAccess.readFile(getContext().getResources().openRawResource(R.raw.buildings));
                            Log.d(TAG, "Loaded Location Data.");
                            LatLng nav = csvAccess.getLatLng(getItem(position));

                            //map.moveCamera(nav,15f,"Place");



                            f_manager.beginTransaction().replace(R.id.fragment_container,
                                    map).commitNow();

                            //map.moveCamera(nav,15f,"Place");

                            if(map.readyMap()){
                                map.moveCamera(nav,15f,"Place");
                            }

                        }

                        Log.d(TAG, csvAccess.getLatLng((Event)getItem(position)).toString());

                        

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
            holder.fav.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO: Add what happens when you click on SAVE Button
                    ImageButton button = (ImageButton) v;
                    if(ScheduleActivity.populateListView(getItem(position))){
                        button.setImageResource(R.drawable.ic_favorite_black_24dp);
                    }
                    else{
                        button.setImageResource(R.drawable.ic_unfavorite_black_24dp);
                    }
                }
            });
        }
        catch(Exception exception){
            Log.e(TAG, "getView: ", exception);
        }

        return convertView;
    }

    public void filtering(String input){
        ArrayList<Event> result = new ArrayList<>();

        //Remove all of the current List Items.
        if(!this.isEmpty()) {
            this.clear();
        }

        input = input.toLowerCase(Locale.getDefault());

        //When there isn't something to search for, show all...
        if(input.length() == 0){
            this.addAll(this.events);
        }
        else {
            //Search for events matching input terms
            for (Event event : this.events) {
                if (event.getTitle().toLowerCase(Locale.getDefault()).contains(input)) {
                    result.add(event);
                } else if (event.getLocation().toLowerCase(Locale.getDefault()).contains(input)) {
                    result.add(event);
                } else if (event.getDescription().toLowerCase(Locale.getDefault()).contains(input)) {
                    result.add(event);
                } else if (event.getEnd().toLowerCase(Locale.getDefault()).contains(input)) {
                    result.add(event);
                } else if (event.getStart().toLowerCase(Locale.getDefault()).contains(input)) {
                    result.add(event);
                } else if (event.getLink().toLowerCase(Locale.getDefault()).contains(input)) {
                    result.add(event);
                }
            }
            //Add them to the system.
            this.addAll(result);
        }

        this.notifyDataSetChanged();
    }
}