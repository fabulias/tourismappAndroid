package com.fabulias.tourismapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Index extends AppCompatActivity implements com.google.android.gms.maps.OnMapReadyCallback {

    private IndexMap mIndex;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mIndex = IndexMap.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, mIndex)
                .commit();

        //IndexMap aux = (IndexMap) getFragmentManager().findFragmentById(R.id.map);
         mIndex.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
            LatLng pastos = new LatLng(-33.452672, -70.661285);
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(pastos)
                    .title("PAstos Donde se Fuma MOTA EVERY DAY FUCK THE POLICE!!!"));

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(pastos)
                    .zoom(10)
                    .build();

            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }
