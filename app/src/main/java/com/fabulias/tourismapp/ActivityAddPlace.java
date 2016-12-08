package com.fabulias.tourismapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.app.DialogFragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityAddPlace extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.fabulias.register.MESSAGE";
    private PlaceRegisterTask mAuthTask = null;

    private EditText mNamePlaceView;
    private EditText mPhoneView;
    private EditText mDescriptionView;
    private TextView mhourOpenWeekView;
    private TextView mhourOpenWeekndView;
    private TextView mhourCloseWeekView;
    private TextView mhourCloseWeekndView;

    private View mProgressView;
    private View mRegisterView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        Button mRegButton = (Button) findViewById(R.id.reg_button);
        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        mNamePlaceView = (EditText) findViewById(R.id.namePlace);
        mPhoneView = (EditText) findViewById(R.id.phone_place);
        mDescriptionView = (EditText) findViewById(R.id.description);
        mhourOpenWeekView = (TextView) findViewById(R.id.horaOpenS);
        mhourCloseWeekView = (TextView) findViewById(R.id.horaCloseS);
        mhourOpenWeekndView = (TextView) findViewById(R.id.horaOpenF);
        mhourCloseWeekndView = (TextView) findViewById(R.id.horaCloseF);

        mRegisterView = findViewById(R.id.addPlace_form);
        mProgressView = findViewById(R.id.addPlace_progress);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void save() {
        if (mAuthTask != null) {
            return;
        }
        mNamePlaceView.setError(null);
        mPhoneView.setError(null);
        mDescriptionView.setError(null);
        mhourOpenWeekView.setError(null);
        mhourCloseWeekView.setError(null);
        mhourOpenWeekndView.setError(null);
        mhourCloseWeekndView.setError(null);

        String name = mNamePlaceView.getText().toString();
        String phone = mPhoneView.getText().toString();
        String description = mDescriptionView.getText().toString();
        String hourOpenWeek = mhourOpenWeekView.getText().toString();
        String hourCloseWeek = mhourCloseWeekView.getText().toString();
        String hourOpenWeeknd = mhourOpenWeekndView.getText().toString();
        String hourCloseWeeknd = mhourCloseWeekndView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            mNamePlaceView.setError(getString(R.string.error_field_required));
            focusView = mNamePlaceView;
            cancel = true;
        } else if (TextUtils.isEmpty(description)) {
            mDescriptionView.setError(getString(R.string.error_field_required));
            focusView = mDescriptionView;
            cancel = true;
        } else if (TextUtils.isEmpty(hourOpenWeek)) {
            mhourOpenWeekView.setError(getString(R.string.error_field_required));
            focusView = mhourOpenWeekView;
            cancel = true;
        } else if (TextUtils.isEmpty(hourCloseWeek)) {
            mhourCloseWeekView.setError(getString(R.string.error_field_required));
            focusView = mhourCloseWeekView;
            cancel = true;
        } else if (TextUtils.isEmpty(hourOpenWeeknd)) {
            mhourOpenWeekndView.setError(getString(R.string.error_field_required));
            focusView = mhourOpenWeekndView;
            cancel = true;
        } else if (TextUtils.isEmpty(hourCloseWeeknd)) {
            mhourCloseWeekndView.setError(getString(R.string.error_field_required));
            focusView = mhourCloseWeekndView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new PlaceRegisterTask(name, phone, description, hourOpenWeek, hourCloseWeek, hourOpenWeeknd, hourCloseWeeknd);
            String url = "http://ttourismapp.herokuapp.com/api/v1/places";
            mAuthTask.execute(url);
        }
    }

    public void attemptRegister(View view) {
        Intent intent = new Intent(ActivityAddPlace.this, ActivityPerfil.class);
        startActivity(intent);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ActivityAddPlace Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class PlaceRegisterTask extends AsyncTask<String, String, String> {
        private final String mNamePlace;
        private final String mPhone;
        private final String mDescription;
        private final String mhourOpenWeek;
        private final String mhourCloseWeek;
        private final String mhourOpenWeeknd;
        private final String mhourCloseWeeknd;

        PlaceRegisterTask(String name, String phone, String description, String hourOpenWeek, String hourCloseWeek,
                          String hourOpenWeeknd, String hourCloseWeeknd) {
            mNamePlace = name;
            mPhone = phone;
            mDescription = description;
            mhourOpenWeek = hourOpenWeek;
            mhourCloseWeek = hourCloseWeek;
            mhourOpenWeeknd = hourOpenWeeknd;
            mhourCloseWeeknd = hourCloseWeeknd;

        }

        HttpURLConnection urlConnection;
        HttpURLConnection urlConnect;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();
            //StringBuilder resultado = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                JSONObject jsonPlace;
                //JSONObject jsonSchedule;

                jsonPlace = setJsonPlace(mNamePlace, mPhone, mDescription);
                System.out.println("INICIO");
                System.out.println(jsonPlace);
                System.out.println("FIN");//System.out.println(jsonPlace.toString());
                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(jsonPlace.toString());
                wr.flush();
                wr.close();




                InputStream in = null;
                try {
                    in = urlConnection.getInputStream();
                } catch(FileNotFoundException e) {
                    in = urlConnection.getErrorStream();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }


                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(result.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "";
                }
                System.out.println(jsonObj);
                JSONObject id_ = jsonObj.getJSONObject("data");
                System.out.println("--->" + id_);
                /*
                URL api = new URL("https://ttourismapp.herokuapp.com/api/v1/schedule");
                System.out.println("HOLA SCHEDULE");
                urlConnect = (HttpURLConnection) api.openConnection();

                urlConnect.setRequestMethod("POST");
                urlConnect.setRequestProperty("Content-Type", "application/json");


                jsonSchedule = setJsonSchedule(mhourOpenWeek, mhourCloseWeek, mhourOpenWeeknd, mhourCloseWeeknd, id_place);
                DataOutputStream esc = new DataOutputStream(urlConnect.getOutputStream());
                esc.writeBytes(jsonSchedule.toString());
                esc.flush();
                esc.close();

                InputStream en = new BufferedInputStream(urlConnect.getInputStream());
                BufferedReader lectura = new BufferedReader(new InputStreamReader(en));
                String linea;
                while ((linea = lectura.readLine()) != null) {
                    resultado.append(linea);
                }*/
            } catch (Exception e) {
                System.out.println("aqui entre a addplace");
                e.printStackTrace();
                return "";
            } finally {
                urlConnection.disconnect();
                /*
                if (urlConnect != null) {
                    urlConnect.disconnect();
                }
                */

            }
            JSONObject jsonObjPlace = null;
            //JSONObject jsonObjSchedule = null;
            try {
                jsonObjPlace = new JSONObject(result.toString());
                //jsonObjSchedule = new JSONObject(resultado.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }
            try {
                if (/*jsonObjSchedule.getString("status").equals("success") && */jsonObjPlace.getString("status").equals("success")) {
                    System.out.println("Success from API Good :) !!!");
                    return "";
                } else {
                    System.out.println("Error from API Bad :( !!!");
                    return "";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }

            //Continuar con la realización de insertSchedule ya que no esta funcionando arreglar
        }

        JSONObject setJsonPlace(String name, String phone, String description) {

            String nameUser;

            SharedPreferences preferences = getSharedPreferences("DataUser", Context.MODE_PRIVATE);
            nameUser = preferences.getString("mail", null);
            nameUser = "farid@mail.com";
            System.out.println("Aqui la obtiene " + nameUser);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Fecha: " + dateFormat.format(date));
            //Continuar agregando los campos que son requeridos para Agregar un place!!!
            JSONObject JsonPlace = new JSONObject();
            try {
                JsonPlace.put("name", name);
                JsonPlace.put("user", nameUser);
                JsonPlace.put("date", dateFormat.format(date));
                JsonPlace.put("description", description);
                JsonPlace.put("phone", phone);
                return JsonPlace;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        JSONObject setJsonSchedule(String hourOpenWeek, String hourCloseWeek, String hourOpenWeeknd, String hourCloseWeeknd, String id_place) {
            String hourOpenW = "2016-12-04T" + hourOpenWeek + "Z";
            String hourCloseW = "2016-12-04T" + hourCloseWeek + "Z";
            String hourOpenWnd = "2016-12-04T" + hourOpenWeeknd + "Z";
            String hourCloseWnd = "2016-12-04T" + hourCloseWeeknd + "Z";
            JSONObject JsonSchedule = new JSONObject();
            try {
                JsonSchedule.put("id", id_place);
                JsonSchedule.put("o1", hourOpenWeek);
                JsonSchedule.put("c1", hourCloseWeek);
                JsonSchedule.put("o2", hourOpenWeek);
                JsonSchedule.put("c2", hourCloseWeek);
                JsonSchedule.put("o3", hourOpenWeek);
                JsonSchedule.put("c3", hourCloseWeek);
                JsonSchedule.put("o4", hourOpenWeek);
                JsonSchedule.put("c4", hourCloseWeek);
                JsonSchedule.put("o5", hourOpenWeek);
                JsonSchedule.put("c5", hourCloseWeek);
                JsonSchedule.put("o6", hourOpenWeeknd);
                JsonSchedule.put("c6", hourCloseWeeknd);
                JsonSchedule.put("o7", hourOpenWeeknd);
                JsonSchedule.put("c7", hourCloseWeeknd);
                return JsonSchedule;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(final String success) {
            if (success.length() > 0) {
                System.out.println("Registrado");
                finish();
                Toast.makeText(ActivityAddPlace.this, "Has compartido este lugar!", Toast.LENGTH_SHORT).show();
                //Cambiar ActivityPerfil por Menu principal!!!!!
                Intent myIntent = new Intent(ActivityAddPlace.this, ActivityPerfil.class);
                EditText editText = (EditText) findViewById(R.id.email);
                String message = editText.getText().toString();
                myIntent.putExtra(EXTRA_MESSAGE, message);
                startActivity(myIntent);
            } else {
                System.out.println("Registro nuevamente");
                Toast.makeText(ActivityAddPlace.this, "Lugar ya fue compartido!!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(ActivityAddPlace.this, ActivityAddPlace.class);
                ActivityAddPlace.this.startActivity(myIntent);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }


    }


    public void onButtonClickedOpenS(View v) {
        DialogFragment newFragment = new TimePickerFragmentOpenS();
        newFragment.show(getFragmentManager(), "TimePicker");
    }

    public void onButtonClickedCloseS(View V) {
        DialogFragment newFragment = new TimePickerFragmentCloseS();
        newFragment.show(getFragmentManager(), "TimePicker");
    }

    public void onButtonClickedOpenF(View v) {
        DialogFragment newFragment = new TimePickerFragmentOpenF();
        newFragment.show(getFragmentManager(), "TimePicker");
    }

    public void onButtonClickedCloseF(View v) {
        DialogFragment newFragment = new TimePickerFragmentCloseF();
        newFragment.show(getFragmentManager(), "TimePicker");
    }


}
