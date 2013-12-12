package com.android.buscaminas;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class IngresoNombre extends Activity {
	private SQLiteDatabase db;
	EditText nombre;
	Button ingreso;
	String dificultad;
	String time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingresoname);
		nombre= (EditText)findViewById(R.id.editText1);
		ingreso= (Button)findViewById(R.id.ingresoGamer);
		
		//Inicializo , o llamo a la base de datos
		JugadoresSQLite gamersdb =
	            new JugadoresSQLite(this, "DBJugadores", null, 3);
		db = gamersdb.getWritableDatabase();//Abro la base de datos
		//Extraigo la informacion que me envian desde el tablero
		Bundle info = getIntent().getExtras();
		int escogido =info.getInt("dificultad");
		
		Bundle info1 = getIntent().getExtras();
		time= info1.getString("timer");
		dificultad = obtenerDificultad(escogido);
		
		
		ingreso.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				//Recuperamos los valores de los campos de texto
				String gamer = nombre.getText().toString();
				
				
				//Alternativa 1: método sqlExec()
				//String sql = "INSERT INTO Usuarios (codigo,nombre) VALUES ('" + cod + "','" + nom + "') ";
				//db.execSQL(sql);
				
				//Alternativa 2: método insert()
				ContentValues nuevoRegistro = new ContentValues();
				nuevoRegistro.put("codigo", gamer);
				nuevoRegistro.put("nombre", time);
				nuevoRegistro.put("tiempo", dificultad);
				//nuevoRegistro.put("tiempo", time);
				db.insert("JugadoresPuntajes", null, nuevoRegistro);
			}
		});
	}
	
	public String obtenerDificultad(int nivel){
		String level="";
		if(nivel==1)level="Principiante";
		if(nivel==2)level="Normal";
		if(nivel==3)level="Experto";
		if(nivel==4)level="Personalizado";
		return level;
	}
}