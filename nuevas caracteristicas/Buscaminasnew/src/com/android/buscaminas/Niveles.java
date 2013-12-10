package com.android.buscaminas;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Niveles extends Activity implements android.view.View.OnClickListener{
	private Button BotonBasico;
	private Button BotonNormal;
	private Button BotonExperto;
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
			normallevel.putExtra("normal",BotonNormal.getId());
			startActivity(normallevel);
			}
		if(vista.getId()==findViewById(R.id.advancedButton).getId()){
			Intent advancedlevel = new Intent(this,Buscaminas.class);
			advancedlevel.putExtra("experto",BotonExperto.getId());
			startActivity(advancedlevel);
			}
	}
}