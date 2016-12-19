package com.fabulias.tourismapp;


import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.SeekBar.OnSeekBarChangeListener;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SharePlaceFragment extends Fragment implements OnSeekBarChangeListener {

    public SharePlaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup view=container;
        SeekBar seekBar = (SeekBar) container.findViewById(R.id.ratio);
        final TextView textViewSeekBar = (TextView) container.findViewById(R.id.distance);
        textViewSeekBar.setText("25");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                                private Toast toastStart = Toast.makeText(view.getContext(),getText(R.string.aux1),Toast.LENGTH_SHORT);
                                                private Toast toastStop = Toast.makeText(view.getContext(), getText(R.string.aux2), Toast.LENGTH_SHORT);
                                               @Override
                                               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                   textViewSeekBar.setText(progress + 20 + "");
                                               }

                                               @Override
                                               public void onStartTrackingTouch(SeekBar seekBar) {
                                                   toastStop.cancel();
                                                   toastStart.setGravity(Gravity.TOP| Gravity.LEFT, 60, 110);
                                                   toastStart.show();

                                               }

                                               @Override
                                               public void onStopTrackingTouch(SeekBar seekBar) {
                                                   toastStart.cancel();
                                                   toastStop.setGravity(Gravity.TOP|Gravity.RIGHT, 60, 110);
                                                   toastStop.show();

                                               }
                                           }

        );



        return inflater.inflate(R.layout.fragment_share_place, container, false);
    }


}
