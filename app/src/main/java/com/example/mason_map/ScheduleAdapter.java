package com.example.mason_map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.ArrayList;

public class ScheduleAdapter extends ArrayAdapter<Event>{

    private static final String TAG = "Schedule Adapter";

    private final Context context;
    private int resource;
    private int lastPos;
    private ArrayList<Event> events;
    private ReadCSV csvAccess;
    private FragmentManager f_manager;
    private MapsActivity map;

    // Interface that allows access to each element within User GUI
    private static class EventHolder{
        TextView title;
        TextView start;
        TextView end;
        TextView location;
        ImageButton link;
        ImageButton nav;
        ImageButton fav;
    }

    public ScheduleAdapter(Context context, int resource, ArrayList<Event> objects, FragmentManager f_manager, MapsActivity map){
        super(context, resource, objects);

        this.resource = resource;
        this.context = context;
        this.lastPos = 0;
        this.events = new ArrayList<>();
        this.map = map;
        this.f_manager = f_manager;
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

                //Change the Button to Fav Mode
                holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);

                convertView.setTag(holder);
            }
            else{
                holder = (EventHolder) convertView.getTag();
            }
            this.lastPos = position;

            //Set the text
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
                        if(csvAccess == null) {

                            //This accesses the data within the database.
                            csvAccess = new ReadCSV();

                            //This reads the data file
                            csvAccess.readFile(getContext().getResources().openRawResource(R.raw.buildings));

                            //Gets the location of the item.
                            Log.d(TAG, "Loaded Location Data.");
                            LatLng nav = csvAccess.getLatLng(getItem(position));

                            //Switches Views:
                            f_manager.beginTransaction().replace(R.id.fragment_container,
                                    map).commit();

                            //Sets the location required for navigation:
                            Log.d(TAG, "Title of Location is " + csvAccess.getTitle(nav));
                            map.setLocation(nav, csvAccess.getTitle(nav));
                        }
                        Log.d(TAG, csvAccess.getLatLng((Event)getItem(position)).toString());

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
            holder.fav.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    //Removes from schedule
                    ScheduleActivity.populateListView(getItem(position));
                    remove(getItem(position));
                }
            });
        }
        catch(Exception exception){
            Log.e(TAG, "getView: ", exception);
        }

        return convertView;

    }
}