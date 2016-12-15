package com.fabulias.tourismapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.app.DialogFragment;
import android.app.AlertDialog;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Diálogo con checkboxes
 */

public class TagSelectedFragment extends DialogFragment {

    private CharSequence[] items = new CharSequence[10];
    private TagSelectedTask mAuthTask = null;

    public TagSelectedFragment() {

    }


    public void attemptTag(){
        if (mAuthTask!=null){return;}
        System.out.println("ALOJAAAAAAAAAAAA EN EL attemptTag !!!!!!!!!!!!!!!!");
        mAuthTask = new TagSelectedTask();
        String url = "http://ttourismapp.heroku.com/api/v1/tags";
        mAuthTask.execute(url);
            }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        attemptTag();
        return createMultipleListDialog();
    }

    /**
     * Crea un diálogo con una lista de checkboxes
     * de selección multiple
     *
     * @return Diálogo
     */




    public AlertDialog createMultipleListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final ArrayList<Integer> itemsSeleccionados = new ArrayList<Integer>();

        items[0]= "wena cabros";
        for (int i = 0; i<10; i++){
            System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println(items[i]);
        }

        builder.setTitle("Intereses")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // Guardar indice seleccionado
                            final boolean add = itemsSeleccionados.add(which);
                            if (add) {
                                Toast.makeText(
                                        getActivity(),
                                        "Checks seleccionados:(" + itemsSeleccionados.size() + ")",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }

                        } else if (itemsSeleccionados.contains(which)) {
                            // Remover indice sin selección
                            itemsSeleccionados.remove(Integer.valueOf(which));
                        }
                    }
                });

        return builder.create();
    }

  protected class TagSelectedTask extends AsyncTask<String,String,String>{



      HttpURLConnection urlConnection;

      @Override
      protected String doInBackground(String... params){
          StringBuilder result = new StringBuilder();
          try {
              URL url = new URL(params[0]);
              urlConnection = (HttpURLConnection) url.openConnection();
              urlConnection.setRequestMethod("GET");
              urlConnection.setRequestProperty("Content-Type", "application/json");

              System.out.println("HOLA ENTRE RECIEN EL DOINBACKGROUND!!!!!");

              InputStream in = new BufferedInputStream(urlConnection.getInputStream());
              BufferedReader reader = new BufferedReader(new InputStreamReader(in));
              String line;
              while ((line = reader.readLine()) != null) {
                  result.append(line);
              }

          }catch( Exception e) {

              e.printStackTrace();

          }
          finally {
              urlConnection.disconnect();
          }

          JSONObject jsonObj=null;
          JSONArray jsonArray;
          try{
              jsonObj= new JSONObject(result.toString());
              jsonArray= jsonObj.getJSONArray("data");

              System.out.println("AQUI ANTES DEL FOR ");

              for (int i=1; i<jsonArray.length();i++){
                  System.out.println("VOY BIEN HASTA AHORA");
                  JSONObject json_data=jsonArray.getJSONObject(i);
                  System.out.println("antes de ingresar los datos a items!!!!!!!!!!!!!!!!");


                  items[i] = json_data.getString("Name");
                  System.out.println("HOLAAAAAAAAAAAAA");
                  System.out.println(items[i]);
                  System.out.println("CHAOOOOOOOOOOOOO");

              }
          }catch (JSONException e){
              System.out.println("HOLIWIS MEN");
                   e.printStackTrace();
          }
          return result.toString();


      }
  }


}


