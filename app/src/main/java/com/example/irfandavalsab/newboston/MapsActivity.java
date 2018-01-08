package com.example.irfandavalsab.newboston;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.app.Service;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements LocationListener{

    //private final Context mContext;

    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location = null;
    double latitude;
    double longitude;

    private static final long MIN_TIME_BW_UPDATES = 1000*60*1;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    protected LocationManager locationManager;

    // this method shows the current location of the user.
    //http://www.codeproject.com/Articles/825942/Flirting-with-Google-Maps-on-Android
    private void showCurrentLocation(Location location){
        mMap.clear();
        LatLng currentPosition = new LatLng(location.getLatitude(),location.getLongitude());

        mMap.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .snippet("Lat:" +location.getLatitude() + "Long:" +location.getLongitude())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
                    .flat(true)
                    .title("I am Here")
        );

        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 18));
        // to get the list of supported places : https://developers.google.com/places/documentation/supported_types
        //http://stackoverflow.com/questions/8428209/show-current-location-and-nearby-places-and-route-between-two-places-using-googl
        mMap.getMaxZoomLevel();
    }
    /*public MapsActivity(Context context){
        //this.mContext = context;
        //getLocation();
    }*/

    /***
     * To get the Lat and Longitude points through GPS
     * ***/
    public Location getLocation(){
        try{
            locationManager = (LocationManager)getSystemService(Activity.LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled){
                // Do nothing
            }else{
                this.canGetLocation = true;
                if(isNetworkEnabled){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
                    Log.d("Network", "Network Enabled");
                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            showCurrentLocation(location);
                        }
                    }
                }

                if(isGPSEnabled){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
                    Log.d("GPS", "GPS Enabled");
                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if(location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return location;
    }

    /***
     * Stop using GPS Listener calling, this function will stop using GPS in the app
     * ***/

    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(MapsActivity.this);
        }
    }

    /**
     * Function to get Latitude
     * **/

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    /**
     * Function to get Longitude
     * **/

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    /**
     * Function to check if GPS/WiFi is enabled
     * **/

    public Boolean canGetLocation(){
        return this.canGetLocation;
    }

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getLocation();
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();

        if(mMap!=null){
            mMap.setMyLocationEnabled(true);
            mMap.getMyLocation();
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);    //to change the type of the map, it can be terrain, normal, hybrid or satellite
            //Alternatively, you can make the changes for the map in the xml file as well.
            //http://www.techotopia.com/index.php/Working_with_the_Google_Maps_Android_API_in_Android_Studio
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
      * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            /*if (mMap != null) {
                setUpMap();
            }*/
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    /*private void setUpMap() {

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        String provider = locationManager.getBestProvider(criteria, true);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(provider,2000,0,locationListener);
        //getting initial location
        Location location = locationManager.getLastKnownLocation(provider);

        // show initial location
        if(location != null){
            showCurrentLocation(location);
        }
    }*/

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
