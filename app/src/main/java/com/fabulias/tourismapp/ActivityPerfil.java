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
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.view.View;
import android.view.ViewStructure;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class ActivityPerfil extends AppCompatActivity {
    private static final int REQUEST_READ_CONTACTS = 0;
    public final static String EXTRA_MESSAGE = "com.fabulias.MESSAGE";

    private UserPerfilTask mAuthTask = null;

    private EditText mNameView;
    private EditText mSurnameView;
    private EditText mS_surnameView;
    private EditText mPasswordView;


    private View mProgressView;
    private View mPerfilFormView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mNameView = (EditText) findViewById(R.id.name_perfil);
        mSurnameView = (EditText) findViewById(R.id.surname);
        mS_surnameView = (EditText) findViewById(R.id.s_surname);
        mPasswordView = (EditText) findViewById(R.id.password);

        mProgressView = findViewById(R.id.perfil_progress);

        SharedPreferences preferences= getSharedPreferences("DataUser", Context.MODE_PRIVATE);
        String pass=preferences.getString("pass", null);
        String name=preferences.getString("name", null);
        String surname=preferences.getString("surname",null);
        String s_surname=preferences.getString("s_surname",null);

        mPasswordView.setText(pass);
        mNameView.setText(name);
        mSurnameView.setText(surname);
        mS_surnameView.setText(s_surname);






        Button mPatchPerfilButton = (Button) findViewById(R.id.Update_Perfil);
        mPatchPerfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptPerfil();
            }
        });





        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
/*
    protected HttpURLConnection urlAddress;
    protected JSONObject setDataUser(String mail){

        // TODO: attempt authentication against a network service.
        StringBuilder result = new StringBuilder();



        try {
            URL url = new URL( "https://ttourismapp.herokuapp.com/api/v1/customers/" + mail);
            urlAddress = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlAddress.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch( Exception e) {
            e.printStackTrace();

        }
        finally {
            urlAddress.disconnect();
        }
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            e.printStackTrace();

        }
        try {
            assert jsonObj != null;
            if (jsonObj.getString("status").equals("success")) {
                System.out.println("Success from API Good :) !!!");
                JSONArray c = jsonObj.getJSONArray("data");
                return c.getJSONObject(0);

            } else {
                System.out.println("Error from API Bad :( !!!");
                return null;

            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;

        }

    } */




    private void attemptPerfil() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mNameView.setError(null);
        mSurnameView.setError(null);
        mS_surnameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString();
        String surname = mSurnameView.getText().toString();
        String s_surname = mS_surnameView.getText().toString();
        String password = mPasswordView.getText().toString();

            mAuthTask = new UserPerfilTask(name, surname, s_surname, password);
            String url = "https://ttourismapp.herokuapp.com/api/v1/customers";
            mAuthTask.execute(url);

    }
    /*@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mPerfilFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mPerfilFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mPerfilFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mPerfilFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
*/


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Perfil Page") // TODO: Define a title for the content shown.
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


    public class UserPerfilTask extends AsyncTask<String, String, String> {

        private final String mName;
        private final String mSurname;
        private final String mS_surname;
        private final String mPassword;

        UserPerfilTask(String name, String surname, String s_surname, String password) {
            mName = name;
            mSurname = surname;
            mS_surname = s_surname;
            mPassword = password;
        }

        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            StringBuilder result = new StringBuilder();
            try {
                //String mMail = getIntent().getStringExtra("usermail");

                JSONObject json = new JSONObject();
                json.put("name", mName);
                json.put("surname", mSurname);
                json.put("s_surname", mS_surname);
                json.put("pass", mPassword);
                SharedPreferences preferences= getSharedPreferences("DataUser", Context.MODE_PRIVATE);
                String mMail=preferences.getString("mail",null);
                URL url = new URL(params[0] + "/" + mMail);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("PATCH");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                System.out.println("AQUIIIIIIIIIIIIIII");

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(json.toString());
                wr.flush();
                wr.close();
                System.out.println("AQUIIIIIIIIIIIIIII2222222222222");
                InputStream in;
                in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (Exception e) {
                e.printStackTrace();

                return "";
            } finally {
                urlConnection.disconnect();
            }
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(result.toString());
            } catch (JSONException e) {
                e.printStackTrace();


            }
            try {
                if (jsonObj.getString("status").equals("success")) {
                    JSONObject jobj = jsonObj.getJSONObject("data");
                    //JSONObject jobj = c.getJSONObject(0);
                    System.out.println("Success from API Good :) !!!");
                    SetDataUser(jobj);
                    return result.toString();
                } else {
                    System.out.println("Error from API Bad :( !!!");
                    return "";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }

            // TODO: register the new account here!
        }

        protected void SetDataUser(JSONObject userData) throws JSONException {
            System.out.println("HOLA AQUI ESTOY JEJEJEJ");
            SharedPreferences preferences=getSharedPreferences("DataUser",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("pass", userData.getString("pass"));
            editor.putString("name", userData.getString("name"));
            editor.putString("surname", userData.getString("surname"));
            editor.putString("s_surname", userData.getString("s_surname"));
            editor.apply();
            finish();
        }
        protected void onPostExecute(final String success) {
            if (success.length() > 0) {
                System.out.println("EDITADO PERFIL");
                finish();
                Toast.makeText(Perfil.this, "Perfil Actualizado", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(Perfil.this, Perfil.class);
               /* EditText editText = (EditText) findViewById(R.id.email);
                String message = editText.getText().toString();
                myIntent.putExtra(EXTRA_MESSAGE, message); */
                startActivity(myIntent);


            } else {
                Toast.makeText(Perfil.this, "Error al actualizar Perfil", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(Perfil.this, Perfil.class);
                Perfil.this.startActivity(myIntent);
                /*
                setContentView(R.layout.activity_login);
                Toast.makeText(LoginActivity.this, "CONTRASEÑA INCORRECTA!!!", Toast.LENGTH_SHORT).show();
                System.out.println("NO existe  la persona!!!");
                */
            }
        }




    }




}
