package com.android.buscaminas;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Ranking extends Activity {
	private TextView tabla;
	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking);
		tabla = (TextView)findViewById(R.id.tablaValores);

		JugadoresSQLite gamersdb =
	            new JugadoresSQLite(this, "DBJugadores", null, 3);
		 
		db = gamersdb.getWritableDatabase();
		 Cursor c = db.rawQuery("SELECT codigo,nombre,tiempo FROM JugadoresPuntajes", null);
			//Cursor c = db.rawQuery("SELECT codigo,nombre,tiempo FROM Usuarios", null);
			//Alternativa 2: método delete()		 
			//String[] campos = new String[] {"codigo", "nombre"};
			//Cursor c = db.query("Usuarios", campos, null, null, null, null, null);
			
			//Recorremos los resultados para mostrarlos en pantalla
			tabla.setText("");
			if (c.moveToFirst()) {
			     //Recorremos el cursor hasta que no haya más registros
			     do {
			          String cod = c.getString(0);
			          String nom = c.getString(1);
			          String tim = c.getString(2);
			          //tabla.append(" " + cod + " - " + nom + "\n");
			          tabla.append(" " + cod + " - " + nom + "-"+ tim + "\n");
			     } while(c.moveToNext());
			}
	 
	}
}