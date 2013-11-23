package com.android.buscaminas;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Niveles extends Activity implements android.view.View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectorniveles);
		
		View basico = findViewById(R.id.beginnerButton);
		basico.setOnClickListener(this);
		
		View normallevel = findViewById(R.id.normalButton);
		normallevel.setOnClickListener(this);
		
		View advancedlevel = findViewById(R.id.advancedButton);
		advancedlevel.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View vista) {
		if(vista.getId()==findViewById(R.id.beginnerButton).getId()){
			Intent basiclevel = new Intent(this,Buscaminas.class);
			startActivity(basiclevel);
			}
		if(vista.getId()==findViewById(R.id.normalButton).getId()){
			Intent normallevel = new Intent(this,Buscaminas.class);
			startActivity(normallevel);
			}
		if(vista.getId()==findViewById(R.id.advancedButton).getId()){
			Intent advancedlevel = new Intent(this,Buscaminas.class);
			startActivity(advancedlevel);
			}
	}
}