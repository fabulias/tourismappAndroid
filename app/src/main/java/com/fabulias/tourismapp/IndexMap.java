package com.fabulias.tourismapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.maps.SupportMapFragment;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndexMap#newInstance} factory method to
 * create an instance of this fragment.
 */

public class IndexMap extends SupportMapFragment {

    public IndexMap() {
    }

    public static IndexMap newInstance() {
        return new IndexMap();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        return root;
    }


}

