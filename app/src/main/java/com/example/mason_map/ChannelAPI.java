package com.example.mason_map;

import retrofit2.Call;
import retrofit2.http.GET;

import com.example.mason_map.model.RSS;

/*
  This is used by Retrofit, there is no need to add anything else.
 */
public interface ChannelAPI {

    String baseUrl = "https://gmu.campuslabs.com/engage/events.rss";

    @GET("events.rss")
    Call<RSS> getChannel();

}
