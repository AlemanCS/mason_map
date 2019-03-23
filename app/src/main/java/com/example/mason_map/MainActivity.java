package com.example.mason_map;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import com.example.mason_map.model.RSS;
import com.example.mason_map.model.item.Item;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private static final String TAG = "MainActivity";

    private static final String baseUrl = "https://gmu.campuslabs.com/engage/";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    mTextMessage.setText(R.string.title_feed);
                    return true;
                case R.id.navigation_map:
                    mTextMessage.setText(R.string.title_map);
                    openMaps();
                    return true;
                case R.id.navigation_schedule:
                    mTextMessage.setText(R.string.title_schedule);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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

                List<Item> items = response.body().getItems();

                Log.d(TAG, "onResponce: items: " + response.body().getItems());

                // Testing
                Log.d(TAG, "onResponce: location:" + items.get(0).getLocation());
                Log.d(TAG, "onResponce: start:" + items.get(0).getStart());
            }

            @Override
            public void onFailure(Call<RSS> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to fetch RSS Feed: " + t.getMessage());
                Toast.makeText(MainActivity.this, "An Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openMaps(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
