package com.fabulias.tourismapp;

/**
 * Created by fabulias on 12/17/16.
 */
public class Market {
    private int id;
    private double lat;
    private double lng;

    public Market(int i, double la, double ln) {
        id = i;
        lat = la;
        lng = ln;
    }


    public int getId() {
        return id;
    }

    public double getLat() {
        return this.lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }
}
