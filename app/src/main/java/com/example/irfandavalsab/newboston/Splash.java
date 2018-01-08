package com.example.irfandavalsab.newboston;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Irfandavalsab on 2/9/2015.
 */
public class Splash extends Activity {
    // flag to check Internet connection status
    Boolean isInternetPresent = false;

    GooglePlaces googlePlaces;

    PlacesList nearPlaces;

    GPSTracker gps;

    //Connection Detector Class
    ConnectionDetector cd;

    //Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    MediaPlayer ourSong;
    Button click;


    // KEY Strings

    public static String KEY_REFERENCE = "reference"; //id of the place
    public static String KEY_NAME = "name"; // name of the place
    public static String KEY_VICINITY = "vicinity"; //Place area name


    @Override
    protected void onCreate(Bundle NafridIrfan){
        super.onCreate(NafridIrfan);
        setContentView(R.layout.display_screen);
        click = (Button) findViewById(R.id.bClick);

        ourSong = MediaPlayer.create(Splash.this,R.raw.bismillah_background);
        ourSong.start();

        cd = new ConnectionDetector(getApplicationContext());

        //Check if Internet is present

        isInternetPresent = cd.isConnectingToInternet();
        if(!isInternetPresent){
            //Internet connection is not present
            alert.showAlertDialog(Splash.this, "Internet connection Error", "Please connect to working Internet connection", false);
            //Stop executing code by return
            return;
        }

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openStartingPoint = new Intent(Splash.this,MapsActivity.class);
                startActivity(openStartingPoint);
            }
        });

        ourSong = MediaPlayer.create(Splash.this,R.raw.bismillah_background);
        ourSong.start();
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(10000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {

                    /*Intent openStartingPoint = new Intent("com.example.irfandavalsab.newboston.IRFAN");. This line isn't working,
                     http://stackoverflow.com/questions/10880026/how-to-solve-no-activity-found-to-handle-intent-error*/

                    //Intent openStartingPoint = new Intent(Splash.this,Irfan.class);
                    //startActivity(openStartingPoint);
                }
            }
        };
        timer.start();
    }

    /***
     * Code to Locate near by Masjids and Cemetery.
     ***/

    protected String doInBackground(String... args){
        googlePlaces = new GooglePlaces();
        try{
            String types = "Mosque|Cemetery";
            double radius = 2000;
            nearPlaces = googlePlaces.search(gps.getLatitude(),gps.getLongitude(),radius,types);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPause(){
        super.onPause();
        ourSong.release();
    }
}
