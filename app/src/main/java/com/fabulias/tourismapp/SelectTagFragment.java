package com.fabulias.tourismapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Diálogo con checkboxes
 */
public class SelectTagFragment extends DialogFragment {

    public SelectTagFragment() {

    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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

        final String[] items = new String[11];

        items[0] = "Restaurant";
        items[1] = "Bar";
        items[2] = "Pub";
        items[3] = "Disco";
        items[4] = "Sushi";
        items[5] = "Sandwich";
        items[6] = "Peluqueria";
        items[7] = "Parque";
        items[8] = "Tienda de ropa";
        items[9] = "Bicicleta";
        items[10] = "Otros";

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
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences tagPref= getActivity().getSharedPreferences("DataTag",Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = tagPref.edit();
                edit.putInt("lenght",itemsSeleccionados.size());
                for(int i = 0; i<itemsSeleccionados.size(); i++){
                    String tag= items[itemsSeleccionados.get(i)];
                    String keytag = "tag"+i;
                    SharedPreferences.Editor editor = tagPref.edit();
                    editor.putString(keytag,tag);
                    editor.apply();
                }
                dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });


        return builder.create();
    }



}

