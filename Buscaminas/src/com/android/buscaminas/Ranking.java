package com.android.buscaminas;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class Ranking extends Activity {
	//private TextView tabla;
	private EditText tabla;
	private EditText tablaNormal;
	private EditText tablaExperto;
	private SQLiteDatabase db;
	//private ScrollView tableScroll;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking);
		//tabla = (TextView)findViewById(R.id.tablaValores);
		tabla = (EditText)findViewById(R.id.tablaValores);
		tabla.setMovementMethod(new ScrollingMovementMethod());
		tablaNormal = (EditText)findViewById(R.id.tablaValoresNormal);
		tablaNormal.setMovementMethod(new ScrollingMovementMethod());
		tablaExperto = (EditText)findViewById(R.id.tablaValoresExperto);
		tablaExperto.setMovementMethod(new ScrollingMovementMethod());
		//tableScroll = (ScrollView) findViewById(R.id.SCROLLER_ID);
		//scrollToBottom();
		
		
		JugadoresSQLite gamersdb =
	            new JugadoresSQLite(this, "DBJugadores", null, 3);
		 
		db = gamersdb.getWritableDatabase();
		 Cursor c = db.rawQuery("SELECT codigo,nombre,tiempo FROM JugadoresPuntajes WHERE tiempo='Principiante' ORDER BY tiempo,nombre", null);
		 Cursor c1 = db.rawQuery("SELECT codigo,nombre,tiempo FROM JugadoresPuntajes WHERE tiempo='Normal' ORDER BY tiempo,nombre", null);
		 Cursor c2 = db.rawQuery("SELECT codigo,nombre,tiempo FROM JugadoresPuntajes WHERE tiempo='Experto' ORDER BY tiempo,nombre", null);
		 //Cursor c = db.rawQuery("SELECT codigo,nombre,tiempo FROM JugadoresPuntajes ORDER BY tiempo,nombre", null);
			//Cursor c = db.rawQuery("SELECT codigo,nombre,tiempo FROM Usuarios", null);
			//Alternativa 2: método delete()		 
			//String[] campos = new String[] {"codigo", "nombre"};
			//Cursor c = db.query("Usuarios", campos, null, null, null, null, null);
			
			//Recorremos los resultados para mostrarlos en pantalla
			tabla.setText(" ");
			tablaNormal.setText(" ");
			tablaExperto.setText(" ");
			if (c.moveToFirst()) {
			     //Recorremos el cursor hasta que no haya más registros
			     do {
			          String cod = c.getString(0);
			          String nom = c.getString(1);
			          String tim =" ";// c.getString(2);
			          //tabla.append(" " + cod + " - " + nom + "\n");
			          tabla.append(" " + cod + " - " + nom + ""+ tim + "\n");
			     } while(c.moveToNext());
			}
			if (c1.moveToFirst()) {
			     //Recorremos el cursor hasta que no haya más registros
			     do {
			          String cod = c1.getString(0);
			          String nom = c1.getString(1);
			          String tim = " ";//c1.getString(2);
			          //tabla.append(" " + cod + " - " + nom + "\n");
			          tablaNormal.append(" " + cod + " - " + nom + ""+ tim + "\n");
			     } while(c1.moveToNext());
			}
			if (c2.moveToFirst()) {
			     //Recorremos el cursor hasta que no haya más registros
			     do {
			          String cod = c2.getString(0);
			          String nom = c2.getString(1);
			          String tim = " ";//c2.getString(2);
			          //tabla.append(" " + cod + " - " + nom + "\n");
			          tablaExperto.append(" " + cod + "  :  " + nom + ""+ tim + "\n");
			     } while(c2.moveToNext());
			}
	}

	
}