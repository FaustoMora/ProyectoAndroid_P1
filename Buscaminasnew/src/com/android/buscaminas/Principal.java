package com.android.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Principal extends Activity implements android.view.View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		View newGame = findViewById(R.id.NewGame);
		newGame.setOnClickListener(this);
		
		View help = findViewById(R.id.helpButton);
		help.setOnClickListener(this);
		
		View rankbutton = findViewById(R.id.rankingButton);
		rankbutton.setOnClickListener(this);
		
		View developers = findViewById(R.id.infoMakersButton);
		developers.setOnClickListener(this);
		
	}
	
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}*/



	public void onClick(View vista) {
	if(vista.getId()==findViewById(R.id.NewGame).getId()){
		Intent seleccnivel = new Intent(this,Niveles.class);
		startActivity(seleccnivel );
		}
		
	
	if(vista.getId()==findViewById(R.id.rankingButton).getId()){
		Intent ranking = new Intent(this,Ranking.class);
		startActivity(ranking);
		}
	
	if(vista.getId()==findViewById(R.id.helpButton).getId()){
		Intent helpbutton= new Intent(this,Instrucciones.class);
		startActivity(helpbutton);
		}
		
	if(vista.getId()==findViewById(R.id.infoMakersButton).getId()){
		Intent desarrolladores = new Intent(this,Desarrolladores.class);
		startActivity(desarrolladores);
		}
	}


}
