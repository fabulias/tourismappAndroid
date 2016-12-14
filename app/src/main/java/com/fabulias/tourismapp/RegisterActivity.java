//Creating Simple User Forms User Interface (UI) Design in Android
package com.fabulias.tourismapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.fabulias.register.MESSAGE";


    private UserRegisterTask mAuthTask = null;

    // UI references.
    private TextView mNameView;
    private EditText mSurView;
    private EditText mS_SurView;
    private EditText mRutView;
    private EditText mEmailView;
    private EditText mPhoneView;
    private EditText mPassView;

    private View mRegisterView;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mNameView = (TextView) findViewById(R.id.name);
        mSurView = (EditText) findViewById(R.id.surname);
        mS_SurView = (EditText) findViewById(R.id.s_surname);
        mRutView = (EditText) findViewById(R.id.rut);
        mEmailView = (EditText) findViewById(R.id.email);
        mPhoneView = (EditText) findViewById(R.id.phone);
        mPassView = (EditText) findViewById(R.id.pass);
        //populateAutoComplete();
        mPassView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register || id == EditorInfo.IME_NULL) {
                    save();
                    return true;
                }
                return false;
            }
        });
        Button mRegButton = (Button) findViewById(R.id.reg_button);
        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        mRegisterView = findViewById(R.id.reg_form);
        mProgressView = findViewById(R.id.reg_progress);
    }

    private void save() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mNameView.setError(null);
        mSurView.setError(null);
        mS_SurView.setError(null);
        mRutView.setError(null);
        mEmailView.setError(null);
        mPhoneView.setError(null);
        mPassView.setError(null);


        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString();
        String surname = mSurView.getText().toString();
        String s_surname = mS_SurView.getText().toString();
        String rut = mRutView.getText().toString();
        String email = mEmailView.getText().toString();
        String phone = mPhoneView.getText().toString();
        String pass = mPassView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        } else if (TextUtils.isEmpty(surname)) {
            mSurView.setError(getString(R.string.error_field_required));
            focusView = mSurView;
            cancel = true;
        } else if (TextUtils.isEmpty(s_surname)) {
            mS_SurView.setError(getString(R.string.error_field_required));
            focusView = mS_SurView;
            cancel = true;
        } else if (TextUtils.isEmpty(rut)) {
            mRutView.setError(getString(R.string.error_field_required));
            focusView = mRutView;
            cancel = true;
        } else if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        } else if (TextUtils.isEmpty(pass)) {
            mPassView.setError(getString(R.string.error_field_required));
            focusView = mPassView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        } else if (!isPasswordValid(pass)) {
            mPassView.setError(getString(R.string.error_invalid_password));
            focusView = mPassView;
            cancel = true;
        } else if (!TextUtils.isDigitsOnly(phone)) {
            mPhoneView.setError(getString(R.string.error_incorrect));
            focusView = mPhoneView;
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
            mAuthTask = new UserRegisterTask(name, surname, s_surname, rut, email, phone, pass);
            String url = "https://ttourismapp.herokuapp.com/api/v1/customers";
            mAuthTask.execute(url);
        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        String regexp = "^(?=.*[0-9]).{3,10}$"; //(?=.*[a-z])(?=.*[A-Z])
        return password.matches(regexp);
    }

    public void attemptRegister(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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

    public class UserRegisterTask extends AsyncTask<String, String, String> {

        private final String mName;
        private final String mSurname;
        private final String mS_Surname;
        private final String mRut;
        private final String mEmail;
        private final String mPhone;
        private final String mPass;

        UserRegisterTask(String name, String surname, String s_surname, String rut, String email, String phone, String pass) {
            mName = name;
            mSurname = surname;
            mS_Surname = s_surname;
            mRut = rut;
            mEmail = email;
            mPhone = phone;
            mPass = pass;
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
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                JSONObject json = new JSONObject();
                json.put("name", mName);
                json.put("surname", mSurname);
                json.put("s_surname", mS_Surname);
                json.put("rut", mRut);
                json.put("mail", mEmail);
                json.put("pass", mPass);
                json.put("status", true);

                System.out.println("INICIO");
                System.out.println(json);
                System.out.println("FIN");
                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(json.toString());
                wr.flush();
                wr.close();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            }catch( Exception e) {
                e.printStackTrace();
                return "";
            }
            finally {
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
                    System.out.println("Success from API Good :) !!!");
                    return ".";
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

        @Override
        protected void onPostExecute(final String success) {
            if (success.length() > 0 ) {
                System.out.println("Registrado");
                finish();
                Toast.makeText(RegisterActivity.this, "Ahora estas registrado", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                EditText editText = (EditText) findViewById(R.id.email);
                String message = editText.getText().toString();
                myIntent.putExtra(EXTRA_MESSAGE, message);
                startActivity(myIntent);
            } else {
                System.out.println("Registro nuevamente");
                Toast.makeText(RegisterActivity.this, "Usuario existente!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(RegisterActivity.this, RegisterActivity.class);
                RegisterActivity.this.startActivity(myIntent);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

