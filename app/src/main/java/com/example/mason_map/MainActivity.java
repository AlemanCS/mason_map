package com.example.mason_map;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;

   // private static final String TAG = "MainActivity";



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFrag = null;
            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    selectedFrag = new FeedActivity();
                    break;
                case R.id.navigation_map:
                    selectedFrag = new MapsActivity();
                    break;
                case R.id.navigation_schedule:
                    selectedFrag = new ScheduleActivity();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFrag).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    /*private void openView(Class<?> destination){
        Intent intent = new Intent(this, destination);
        startActivity(intent);
    }
    */
}
