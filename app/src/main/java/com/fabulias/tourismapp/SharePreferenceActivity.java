package com.fabulias.tourismapp;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class SharePreferenceActivity extends AppCompatActivity {

    private int radio = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aux);

        SeekBar seekBar = (SeekBar) findViewById(R.id.ratio);
        final TextView textViewSeekBar = (TextView) findViewById(R.id.distance);
        textViewSeekBar.setText("0");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                              /* private Toast toastStart = Toast.makeText(SharePreferenceActivity.this,getText(R.string.aux1),Toast.LENGTH_SHORT);
                                               private Toast toastStop = Toast.makeText(SharePreferenceActivity.this, getText(R.string.aux2), Toast.LENGTH_SHORT); */
                                               @Override
                                               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                   textViewSeekBar.setText(progress + "");
                                                   radio=progress;


                                               }

                                               @Override
                                               public void onStartTrackingTouch(SeekBar seekBar) {
                                                  /* toastStop.cancel();
                                                   toastStart.setGravity(Gravity.TOP| Gravity.LEFT, 60, 110);
                                                   toastStart.show(); */

                                               }

                                               @Override
                                               public void onStopTrackingTouch(SeekBar seekBar) {
                                                   /*toastStart.cancel();
                                                   toastStop.setGravity(Gravity.TOP|Gravity.RIGHT, 60, 110);
                                                   toastStop.show();*/

                                               }
                                           }

        );
        Button mBuscar = (Button) findViewById(R.id.share_preference);
        mBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref= getSharedPreferences("ratio",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("ratio", radio);
                editor.apply();
                finish();

                Intent myIntent = new Intent(SharePreferenceActivity.this, MainActivity.class);
                SharePreferenceActivity.this.startActivity(myIntent);



            }
        });

    }
    public void onButtonClickedSelectedTags(View v){

        DialogFragment newFragment = new SelectTagFragment();
        newFragment.show(getFragmentManager(),"DialogTag");
    }
}
