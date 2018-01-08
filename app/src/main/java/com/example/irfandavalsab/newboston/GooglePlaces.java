package com.example.irfandavalsab.newboston;

import org.apache.http.client.HttpResponseException;

import android.util.Log;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.jackson.JacksonFactory;

import java.io.IOException;

/**
 * Created by Irfandavalsab on 7/30/2015.
 */
@SuppressWarnings("deprecation")
public class GooglePlaces {

    /* Global instance of the HTTP Transport*/
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    //Google API Key
    private static final  String API_KEY = "@string/google_maps_key";

    //Google places search urls
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";

    private double _latitude;
    private double _longitude;
    private double _radius;

    public PlacesList search(double latitude, double longitude, double radius, String types) throws Exception{

        this._latitude = latitude;
        this._longitude = longitude;
        this._radius = radius;

        try{
            HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
            HttpRequest request = httpRequestFactory.buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
            request.getUrl().put("key", API_KEY);
            request.getUrl().put("location", _latitude + "," + _longitude);
            request.getUrl().put("radius", _radius);
            request.getUrl().put("sensor", "false");

            if(types != null)
                request.getUrl().put("types", types);

            PlacesList list = request.execute().parseAs(PlacesList.class);

            //Check the log for places response status
            Log.d("Places Status", "" + list.Status);
            return list;
        }catch(HttpResponseException e){
            Log.e("Error", e.getMessage());
            return null;
        }

    }

/**  Creating HTTP Request Factory**/

    public static HttpRequestFactory createRequestFactory(final HttpTransport transport){
        return transport.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
                GoogleHeaders headers = new GoogleHeaders();
                headers.setApplicationName("Masjid Locator");
                httpRequest.setHeaders(headers);
                JsonHttpParser parser = new JsonHttpParser(new JacksonFactory());
                httpRequest.addParser(parser);
            }
        });
    }


}
