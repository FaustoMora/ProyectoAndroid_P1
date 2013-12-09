package com.example.archivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	EditText ingreso;
	Button enter,consulta;
	File archivo;
	TextView lbl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		View leer = findViewById(R.id.button2);
		leer.setOnClickListener(this);
		
		ingreso = (EditText)findViewById(R.id.editText1);
		enter =(Button)findViewById(R.id.button1);
		//Toast.makeText(this, "prueba", Toast.LENGTH_LONG).show();
		consulta = (Button)findViewById(R.id.button2);
		TextView lbl = (TextView) findViewById(R.id.textView1);
		MainActivity fm = new MainActivity();
		fm.writeFile("holamundo.txt", "nosinmiubuntu");
		lbl.setText(fm.readFile("holamundo.txt"));
		
		
		consulta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View vista) {
			//Intent mostrar = new Intent(this,Lectura.class);
			
			}
				
			
		});
		
		enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editable data;
				data = ingreso.getText();
				String texto = data.toString();
				//writeFile("Nuevo.txt", texto);		
				TextView lbl = (TextView) findViewById(R.id.textView1);
				MainActivity fm = new MainActivity();
				//archivo = new File("");
				fm.writeFile("holamundo.txt", texto);
				lbl.setText(fm.readFile("holamundo.txt"));
				//Toast.makeText(this, "prueba", Toast.LENGTH_LONG).show();
				
				
				
			}
		});		
	
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void writeFile(String filename, String textfile){
		try {
		  if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) { 
		   File file = new File(Environment.getExternalStorageDirectory(), filename );
		   OutputStreamWriter outw = new OutputStreamWriter(new FileOutputStream(file));
		   outw.write(textfile);
		   outw.append("Esto es extra");
		   
		   outw.close();
		}
	} catch (Exception e) {}  
	}
	public static boolean isExternalStorageReadOnly() {  
		 String extStorageState = Environment.getExternalStorageState();  
		     if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {  
		         return true;  
		     }  
		     return false;  
		}  
		   
		public static boolean isExternalStorageAvailable() {  
		     String extStorageState = Environment.getExternalStorageState();  
		     if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {  
		         return true;  
		     }  
		     return false;  
		}

		@Override
		public void onClick(View vista) {
			if(vista.getId()==findViewById(R.id.button2).getId()){
				Intent seleccnivel = new Intent(this,Lectura.class);
				startActivity(seleccnivel );
				}
			
		}
		public String readFile(String filename){
			 try{
			  if(isExternalStorageAvailable()){
			   File file = new File(Environment.getExternalStorageDirectory(), filename);
			   BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			   String t = br.readLine();
			   
			   br.close();
			   return t;
			  } else {return "";}   
			 } catch(Exception e){return "";}
			}
}

