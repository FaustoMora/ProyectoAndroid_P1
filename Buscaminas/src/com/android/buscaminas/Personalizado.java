package com.android.buscaminas;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
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
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class Personalizado extends Activity implements android.view.View.OnClickListener{
	private EditText fils;
	private EditText columnas;
	private EditText mines;
	private Button start;
       
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom);
		
		//View basico = findViewById(R.id.beginnerButton);
		fils = (EditText)findViewById(R.id.rows);
		
		//basico.setOnClickListener(this);
		columnas = (EditText)findViewById(R.id.colums);
		mines = (EditText)findViewById(R.id.mines);
		
		View startlevel = findViewById(R.id.startCustom);
		start = (Button)findViewById(R.id.startCustom);
		startlevel.setOnClickListener(this);
    }


	@Override
	public void onClick(View vista) {
		if(vista.getId()==findViewById(R.id.startCustom).getId()){
			Intent customlevel = new Intent(this,Buscaminas.class);
			//Bundle bun = new Bundle();
			//bun.putLong("nivel", new Integer(1));
			//basiclevel.putExtras(bun);
			int i=4;
			int f= Integer.parseInt(fils.getText().toString());
			int c= Integer.parseInt(columnas.getText().toString());
			int m= Integer.parseInt(columnas.getText().toString());
			customlevel.putExtra("choice",4);
			customlevel.putExtra("filas", f);
			customlevel.putExtra("colums", c);
			customlevel.putExtra("minas", Integer.parseInt(mines.getText().toString()));
			startActivity(customlevel);
			}
		
	}
	
	@Override
	public void onBackPressed() {
		Intent regreso = new Intent(this,Principal.class);
		
		startActivity(regreso);
	return;
	}

  

 
}