package com.example.mason_map;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.util.Arrays;
import android.widget.AutoCompleteTextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.Manifest;
import android.support.annotation.NonNull;
import android.location.Location;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.widget.SearchView;
import java.util.List;


public class MapsActivity extends Fragment implements OnMapReadyCallback, OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnPoiClickListener {


    private static final String TAG = "MapsActivity";

    private SearchView mSearchText;

    private static final float DEFAULT_ZOOM = 17f;

    private GoogleMap mMap;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean mPermissionDenied = false;

    private ReadCSV csvAccess;

    private LatLng local;

    private String localTitle;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
         View view = layoutInflater.inflate(R.layout.activity_map,viewGroup,false);
         Log.d("TAG","Map is created");

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

        if(this.localTitle == null){
            this.local = new LatLng(38.8315, -77.3115);
            this.localTitle = "George Mason University";
        }


        this.csvAccess = new ReadCSV();
        try {
             csvAccess.readFile(getResources().openRawResource(R.raw.buildings));

        }
        catch(Exception exception){
            Log.e(TAG, exception.toString());
        }
    }

    //Sets up Autocomplete Text View
    private void init(){

        int layoutItemId = android.R.layout.simple_dropdown_item_1line;
        String[] buildArr = csvAccess.locationsStrings();
        List<String> buildingList = Arrays.asList(buildArr);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), layoutItemId, buildingList);

        final AutoCompleteTextView autocompleteView = (AutoCompleteTextView) getView().findViewById(R.id.mapSearch);
        autocompleteView.setAdapter(adapter);
        autocompleteView.setThreshold(1);
        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Hide the keyboard since we no longer need it.
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(autocompleteView.getWindowToken(), 0);

                geoLocate(parent.getItemAtPosition(position).toString());
            }
        });
        autocompleteView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView view, int action, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (action == EditorInfo.IME_ACTION_DONE)) {
                    if (autocompleteView.getAdapter().getCount() > 0) {
                        //Hide the keyboard since we no longer need it.
                        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(autocompleteView.getWindowToken(), 0);

                        //Navigate
                        geoLocate(autocompleteView.getAdapter().getItem(0).toString());
                    }
                }
                return true;
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near George Mason University.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG,"Map is Ready");
        mMap = googleMap;

        LatLng georgeMason = new LatLng(38.8315, -77.3115);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(georgeMason,15f));

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
        mMap.setOnPoiClickListener(this);

        init();
        //When the User clicks the Maps Tab it shouldn't place a marker
        if(localTitle != "George Mason University"){
            moveCamera(local,DEFAULT_ZOOM,localTitle);
            Log.d(TAG,"Marker set to " + localTitle);
        }
    }

    private void geoLocate(String input){
        Log.d(TAG,"GeoLocate: GeoLocating");

        mMap.clear();

        String temp = input;
        input = input.replace(" ","");

        LatLng nav = csvAccess.getLatLng(input);

        setLocation(nav,input);
        moveCamera(local,DEFAULT_ZOOM,temp);

    }

    public void moveCamera(LatLng Latlng,float zoom, String title){
        //Log.d(TAG,"Move Camera: moving the camera to lat: " ,+ Latlng.latitude + ", Lng : " + Latlng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Latlng,zoom));

        Marker info = mMap.addMarker(new MarkerOptions().position(Latlng).title(title));
        ((Marker) info).showInfoWindow();

    }

    public void onPoiClick(PointOfInterest poi) {
        Toast.makeText(getActivity(), "Clicked: " +
                        poi.name + "\nPlace ID:" + poi.placeId +
                        "\nLatitude:" + poi.latLng.latitude +
                        " Longitude:" + poi.latLng.longitude,
                Toast.LENGTH_SHORT).show();
    }


    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getActivity(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    protected void onResumeFragments() {
        super.onResume();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getActivity().getSupportFragmentManager(), "dialog");
    }

    // Sets the location and title, for use within nav to button.
    public void setLocation(LatLng local, String localTitle){
        Log.d(TAG,"New Location is Set to " + localTitle);
        this.local = local;
        this.localTitle = localTitle;
    }
}

