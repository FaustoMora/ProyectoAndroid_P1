package com.android.buscaminas;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.widget.Toast;
 
public class Personalizado extends DialogFragment {
	private EditText fils;
	private EditText columnas;
	private EditText mines;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
 
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
 
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
       fils = new EditText(getActivity().getBaseContext());
       fils.setId(R.id.rows);
       fils.setText("hola");
        
        	// Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        //filas = (Button)findViewById(R.id.beginnerButton);
        builder.setView(inflater.inflate(R.layout.custom, null))
                // Add action buttons
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity(), "Yes",
                                        Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(), String.valueOf(fils.getText()),
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
 
        // Create the AlertDialog object and return it
        return builder.create();
    }
 
}