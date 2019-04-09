package com.example.mason_map;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.net.Uri;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<Event>{

    public static final String TAG = "List Adapter";

    public final Context context;
    public int resource;
    private int lastPos;
    ArrayList<Event> events;

    private static class EventHolder{
        TextView title;
        TextView start;
        TextView end;
        TextView location;
        ImageButton link;
        ImageButton nav;
        ImageButton fav;
    }
    public ListAdapter(Context context, int resource, ArrayList<Event> objects){
        super(context, resource, objects);

        this.resource = resource;
        this.context = context;
        this.lastPos = 0;
        this.events = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
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

                holder.title = convertView.findViewById(R.id.eventTitle);
                holder.start = convertView.findViewById(R.id.eventStart);
                holder.end = convertView.findViewById(R.id.eventEnd);
                holder.location = convertView.findViewById(R.id.eventLocation);
                holder.link = convertView.findViewById(R.id.eventLink);
                holder.nav = convertView.findViewById(R.id.eventNavigation);
                holder.fav = convertView.findViewById(R.id.eventFavorite);

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
                    Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    context.startActivity(browser);
                }
            });
            holder.nav.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO: Add what happens when you click on NAV Button
                }
            });
            holder.fav.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //TODO: Add what happens when you click on SAVE Button
                }
            });
        }
        catch(Exception exception){
            Log.e(TAG, "getView: ", exception);
        }
        finally{
            return convertView;
        }
    }
    public void filtering(String input){
        ArrayList<Event> result = new ArrayList<>();
        Log.d(TAG, "Searched for: " + input);

        input = input.toLowerCase(Locale.getDefault());

        if(input.length() == 0){
            result.addAll(this.events);
        }
        else {
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
        }
        //Remove all of the current List Items.
        this.clear();

        //Repopulate all the list items from the search results.
        for(Event event : result){
            this.add(event);
        }
        notifyDataSetChanged();
    }
}