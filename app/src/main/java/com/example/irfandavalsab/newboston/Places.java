package com.example.irfandavalsab.newboston;

import android.location.Location;

import java.io.Serializable;
import com.google.api.client.util.Key;

/**
 * Created by Irfandavalsab on 8/1/2015.
 */
public class Places implements Serializable{
    @Key
    public String id;

    @Key
    public String name;

    @Key
    public String reference;

    @Key
    public String icon;

    @Key
    public String vicinity;

    @Key
    public String geometry;

    @Key
    public String formatted_address;

    @Key
    public String formatted_phone_number;

    @Override
    public String toString(){
        return name + "-" + id + "-" + reference;
    }

    public static class Geometry implements Serializable{
        @Key
        public Location location;
    }

    public static class Location implements Serializable{
        @Key
        public double lat;

        @Key
        public double lng;
    }
}
