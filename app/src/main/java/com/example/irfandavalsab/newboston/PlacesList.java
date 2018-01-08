package com.example.irfandavalsab.newboston;

import java.io.Serializable;
import java.util.List;

import com.google.api.client.util.Key;
/**
 * Created by Irfandavalsab on 8/1/2015.
 */
public class PlacesList implements Serializable{
    @Key
    public String Status;

    @Key
    public List<Places> results;
}
