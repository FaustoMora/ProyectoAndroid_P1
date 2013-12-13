package com.android.buscaminas;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IngresoNombre extends Activity {
	private SQLiteDatabase db;
	EditText nombre;
	Button ingreso,verRanking;
	String dificultad;
	String time;
	TextView puntaje;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//getWindow().setFlags(WindowManager.
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingresoname);
		
		Bundle getTime = getIntent().getExtras();
		puntaje = (TextView)findViewById(R.id.tiempoObtenido);
		nombre= (EditText)findViewById(R.id.editText1);
		ingreso= (Button)findViewById(R.id.ingresoGamer);
		verRanking=(Button)findViewById(R.id.checkRanking);
		puntaje.setText(getTime.getString("timer"));
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
				Toast.makeText(IngresoNombre.this, "Has almacenado tu puntaje",
                        Toast.LENGTH_LONG).show();
				try{
					Thread.sleep(200);
					IngresoNombre.this.finish();
					
				}catch(Exception e){}
				Intent back = new Intent(IngresoNombre.this,Principal.class);
				startActivity(back);
			}
		});
		
		verRanking.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent verRanking = new Intent(IngresoNombre.this,Ranking.class);
				startActivity(verRanking);
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
	@Override
	public void onBackPressed() {
		Intent regreso = new Intent(this,Principal.class);
		startActivity(regreso);                                                                        
	return;
	}
}