package com.android.buscaminas;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Niveles extends FragmentActivity implements android.view.View.OnClickListener{
	private Button BotonBasico;
	private Button BotonNormal;
	private Button BotonExperto;
	private Button BotonPersonalizado;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectorniveles);
		
		View basico = findViewById(R.id.beginnerButton);
		BotonBasico = (Button)findViewById(R.id.beginnerButton);
		basico.setOnClickListener(this);
		
		View normallevel = findViewById(R.id.normalButton);
		BotonNormal = (Button)findViewById(R.id.normalButton);
		normallevel.setOnClickListener(this);
		
		View advancedlevel = findViewById(R.id.advancedButton);
		BotonExperto = (Button)findViewById(R.id.advancedButton);
		advancedlevel.setOnClickListener(this);
		
		View customlevel = findViewById(R.id.customButton);
		BotonPersonalizado = (Button)findViewById(R.id.customButton);
		customlevel.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View vista) {
		if(vista.getId()==findViewById(R.id.beginnerButton).getId()){
			Intent basiclevel = new Intent(Niveles.this,Buscaminas.class);
			//Bundle bun = new Bundle();
			//bun.putLong("nivel", new Integer(1));
			//basiclevel.putExtras(bun);
			int i=1;
			basiclevel.putExtra("choice",i);
			startActivity(basiclevel);
			}
		if(vista.getId()==findViewById(R.id.normalButton).getId()){
			Intent normallevel = new Intent(this,Buscaminas.class);
			int i=2;
			normallevel.putExtra("choice",i);			
			startActivity(normallevel);
			}
		if(vista.getId()==findViewById(R.id.advancedButton).getId()){
			Intent advancedlevel = new Intent(this,Buscaminas.class);
			int i=3;
			advancedlevel.putExtra("choice",i);
			startActivity(advancedlevel);
			}
		if(vista.getId()==findViewById(R.id.customButton).getId()){
			Intent personalizadolevel = new Intent(this,Personalizado.class);
			int i=4;
			personalizadolevel.putExtra("choice",i);
			startActivity(personalizadolevel);
			}
	}
}