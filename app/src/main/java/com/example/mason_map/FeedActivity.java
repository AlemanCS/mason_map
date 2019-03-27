package com.example.mason_map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.mason_map.model.RSS;
import com.example.mason_map.model.item.Item;

import java.util.List;
import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class FeedActivity extends FragmentActivity {

    private static final String TAG = "FeedActivity";

    private static final String baseUrl = "https://gmu.campuslabs.com/engage/";

    private List<Item> items;
    private ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        this.loadFeed();
    }

    /* Load the items from the provided RSS Feed */
    void loadFeed(){
        @SuppressWarnings( "deprecation" )
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        ChannelAPI channelAPI = retrofit.create(ChannelAPI.class);

        Call<RSS> call = channelAPI.getChannel();

        call.enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(Call<RSS> call, Response<RSS> response) {
                Log.d(TAG, "onResponce: rss: " + response.body().getItems());
                Log.d(TAG, "onResponce: Server Responce: " + response.toString());

                items = response.body().getItems();

                createEvents();
            }

            @Override
            public void onFailure(Call<RSS> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to fetch RSS Feed: " + t.getMessage());
                Toast.makeText(FeedActivity.this, "An Error Occured", Toast.LENGTH_SHORT).show();

                //TODO: need to add what happens when we cannot connect to feed here.
            }
        });
    }


    /*
      Create an event for each item within the RSS feed.
      Only contains useful information.
     */
    private void createEvents(){
        this.events = new ArrayList<Event>();

        for(Item item : this.items){
            Event event = new Event();

            event.setTitle(item.getTitle());
            event.setCategory(item.getCategory());
            event.setLocation(item.getLocation());
            event.setLink(item.getLink());
            event.setDescription(item.getDescription());
            event.setStart(item.getStart());
            event.setEnd(item.getEnd());

            this.events.add(event);
        }

    }
}
