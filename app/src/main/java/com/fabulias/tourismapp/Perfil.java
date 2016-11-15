package com.fabulias.tourismapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
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

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Perfil extends AppCompatActivity {
    private static final int REQUEST_READ_CONTACTS = 0;
    public final static String EXTRA_MESSAGE = "com.fabulias.MESSAGE";

    private UserPerfilTask mAuthTask = null;

    private EditText mNameView;
    private EditText mSurnameView;
    private EditText mS_surnameView;
    private EditText mEmailView;


    private View mProgressView;
    private View mPerfilFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mNameView = (EditText) findViewById(R.id.name_perfil);
        mSurnameView = (EditText) findViewById(R.id.surname);
        mS_surnameView = (EditText) findViewById(R.id.s_surname);
        mEmailView = (EditText) findViewById(R.id.mail_perfil);

        mProgressView= findViewById(R.id.perfil_progress);
        //mPerfilFormView = findViewById(R.id.perfil_form);

        Button mPatchPerfilButton = (Button) findViewById(R.id.Update_Perfil);
        mPatchPerfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptPerfil();
            }
        });

    }

    private void attemptPerfil() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mNameView.setError(null);
        mSurnameView.setError(null);
        mS_surnameView.setError(null);
        mEmailView.setError(null);

        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString();
        String surname = mSurnameView.getText().toString();
        String s_surname = mS_surnameView.getText().toString();
        String email = mEmailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new Perfil.UserPerfilTask(name, surname, s_surname, email);
            String url = "https://ttourismapp.herokuapp.com/api/v1/customers";
            mAuthTask.execute(url);
        }
    }

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


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    public class UserPerfilTask extends AsyncTask<String, String, String> {

        private final String mName;
        private final String mSurname;
        private final String mS_surname;
        private final String mMail;

        UserPerfilTask(String name, String surname, String s_suname, String mail) {
            mName = name;
            mSurname = surname;
            mS_surname = s_suname;
            mMail = mail;
        }

        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("PATCH");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                JSONObject jsonPerfilObject = jsonDataUser(mEmailView);

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(jsonPerfilObject.toString());
                wr.flush();
                wr.close();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
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
                return "";
            }
            try {
                if (jsonObj.getString("status").equals("success")) {
                    JSONArray c = jsonObj.getJSONArray("data");
                    JSONObject jobj = c.getJSONObject(0);
                    System.out.println("Success from API Good :) !!!");
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
        protected void onPostExecute(final String success) {
            //TextView t = (TextView) findViewById(R.id.activity_main);
            //t.setText(success);
            //Existe la persona
            if (success.length() > 0 ) {
                finish();
                Toast.makeText(Perfil.this, "Perfil Actualizado",Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(Perfil.this, Perfil.class);
                EditText editText = (EditText) findViewById(R.id.email);
                String message = editText.getText().toString();
                myIntent.putExtra(EXTRA_MESSAGE, message);
                startActivity(myIntent);

            } else {
                Toast.makeText(Perfil.this, "Error al actualizas Perfil", Toast.LENGTH_SHORT).show();
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


    protected JSONObject jsonDataUser(EditText Email){
        StringBuilder result = new StringBuilder();

        HttpURLConnection urlConnection = null;

        try {
            String api = "https://ttourismapp.herokuapp.com/api/v1/customers";
            URL url = new URL(api + "/" + Email);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch( Exception e) {
            e.printStackTrace();
            return null ;
        }
        finally {
            urlConnection.disconnect();
        }
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        try {
            if (jsonObj.getString("status").equals("success")) {
                JSONArray c = jsonObj.getJSONArray("data");
                JSONObject jobj = c.getJSONObject(0);
                System.out.println("Success from API on jsonDataUser Good :) !!!");
                return jobj;


            } else {
                System.out.println("Error from API Bad :( !!!");
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }



    }


}
