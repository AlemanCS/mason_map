package com.example.mason_map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<Event>{

    public Context context;
    public int resource;
    private int lastPos;

    private static class EventHolder{
        TextView title;
        TextView description;
        TextView category;
        TextView start;
        TextView end;
        TextView location;
    }
    public ListAdapter(Context context, int resource, ArrayList<Event> objects){
        super(context, resource, objects);

        this.resource = resource;
        this.context = context;
        this.lastPos = 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String title = this.getItem(position).getTitle();
        //String description = this.getItem(position).getDescription();
        //List category = this.getItem(position).getCategory();
        String start = this.getItem(position).getStart();
        String end = this.getItem(position).getEnd();
        String location = this.getItem(position).getLocation();

        try {
            final EventHolder holder;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(this.context);
                convertView = inflater.inflate(this.resource, parent, false);

                holder = new EventHolder();

                holder.title = convertView.findViewById(R.id.eventTitle);
                holder.category = convertView.findViewById(R.id.eventCatagory);
                holder.start = convertView.findViewById(R.id.eventStart);
                holder.end = convertView.findViewById(R.id.eventEnd);
                holder.location = convertView.findViewById(R.id.eventLocation);

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
        }
        catch(Exception exception){
            Log.e("List Adapter Error", "getView: ", exception);
        }
        finally{
            return convertView;
        }
    }
}
