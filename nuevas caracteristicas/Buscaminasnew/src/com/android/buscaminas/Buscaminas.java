package com.android.buscaminas;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

@SuppressLint({ "DrawAllocation", "NewApi" })
public class Buscaminas extends Activity implements OnTouchListener{
	private Button ButtonBasico;
	private Button ButtonNormal;
	private Button ButtonExperto;
	
	private Tablero fondo;
	int x, y;
	private Celda[][] casillas;
	private boolean activo = true;
	private int filas=8;
	private int cols=8;
	private final int bombas=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buscaminas);
		// Eleccion de acuerdo al nivel escogido
				
		Bundle info = getIntent().getExtras();
		int i=1;
		if(info.getInt("choice")==i){
			filas=15;
			cols=15;
		}
		Toast.makeText(this,String.valueOf(info.getInt("nivel")),
				Toast.LENGTH_LONG).show();
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout2);
		fondo = new Tablero(this);
		fondo.setOnTouchListener(this);

		layout.addView(fondo);
		casillas = new Celda[filas][cols];
		for (int f = 0; f <filas; f++) {
			for (int c = 0; c < cols; c++) {
				casillas[f][c] = new Celda();
			}
		}
		this.disponerBombas();
		this.contarBombasPerimetro();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu., menu);
		return true;
	}

	public void presionado(View v) {
		casillas = new Celda[filas][cols];
		for (int f = 0; f < filas; f++) {
			for (int c = 0; c < filas; c++) {
				casillas[f][c] = new Celda();
			}
		}
		this.disponerBombas();
		this.contarBombasPerimetro();
		activo = true;

		fondo.invalidate();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (activo)
			for (int f = 0; f < filas; f++) {
				for (int c = 0; c < cols; c++) {
					if (casillas[f][c].dentro((int) event.getX(),
							(int) event.getY())) {
						casillas[f][c].destapado = true;
						if (casillas[f][c].contenido == 80) {
							Toast.makeText(this, "Booooooooommmmmmmmmmmm",
									Toast.LENGTH_LONG).show();
							//Thread t = new Thread();
							
							
							//Intent save = new Intent(this,IngresoNombre.class);
							//startActivity(save);
							activo = false;
						} else if (casillas[f][c].contenido == 0)
							recorrer(f, c);
						fondo.invalidate();
					}
				}
			}
		if (gano() && activo) {
			Toast.makeText(this, "Ganaste", Toast.LENGTH_LONG).show();
			activo = false;
		}

		return true;
	}

	class Tablero extends View {

		public Tablero(Context context) {
			super(context);
		}

		protected void onDraw(Canvas canvas) {
			canvas.drawRGB(0, 0, 0);
			int ancho = 0;
			if (canvas.getWidth() < canvas.getHeight())
				ancho = fondo.getWidth();
			else
				ancho = fondo.getHeight();
			int anchocua = ancho / filas;
			Paint paint = new Paint(20);
			Paint paint2 = new Paint();
			paint2.setTextSize(20);
			paint2.setTypeface(Typeface.DEFAULT_BOLD);
			paint2.setARGB(255, 0, 0, 255);
			Paint paintlinea1 = new Paint();
			//paintlinea1.setARGB(0, 255, 255, 255);
		
			int filaact = 0;
			for (int f = 0; f < filas; f++) {
				for (int c = 0; c < cols; c++) {
					casillas[f][c].fijarxy(c * anchocua, filaact, anchocua);
					if (casillas[f][c].destapado == false)
						//Visible
						paint.setARGB(255,230,230,250 ); //100,245,245,220
					else 						//BackGround
						
						//this.findViewById(R.id.button1);
						//Background square
						
						paint.setARGB(255,245,222,179); //200,227,168,87
					canvas.drawRect(c * anchocua, filaact, c * anchocua
							+ anchocua - 2, filaact + anchocua - 2, paint);
					
					
					// linea blanca
				/*canvas.drawLine(c * anchocua, filaact, c * anchocua
							+ anchocua, filaact, paintlinea1);
					
					canvas.drawLine(c * anchocua + anchocua - 1, filaact, c
							* anchocua + anchocua - 1, filaact + anchocua,
							paintlinea1);*/
						
					if (casillas[f][c].contenido >= 1
							&& casillas[f][c].contenido <= filas
							&& casillas[f][c].destapado)
						canvas.drawText(
								String.valueOf(casillas[f][c].contenido), c
										* anchocua + (anchocua / 2) - filas,
								filaact + anchocua / 2, paint2);
					
					//Aqui esta la bomba
					if (casillas[f][c].contenido == 80
							&& casillas[f][c].destapado) {
						Paint bomba = new Paint();
						/*bomba.setARGB(255, 255, 0, 0);
						canvas.drawCircle(c * anchocua + (anchocua / 2),
								filaact + (anchocua / 2), filas, bomba);*/
						Picture p = new Picture();
						canvas.drawPicture(new Picture());
					}

				}
				filaact = filaact + anchocua;
			}
		}
	}

	private void disponerBombas() {
		int cantidad = bombas;
		do {
			int fila = (int) (Math.random() * filas);
			int columna = (int) (Math.random() * cols);
			if (casillas[fila][columna].contenido == 0) {
				casillas[fila][columna].contenido = 80;
				cantidad--;
			}
		} while (cantidad != 0);
	}

	private boolean gano() {
		int cant = 0;
		for (int f = 0; f < filas; f++)
			for (int c = 0; c < cols; c++)
				if (casillas[f][c].destapado)
					cant++;
		if (cant == 56)
			return true;
		else
			return false;
	}

	private void contarBombasPerimetro() {
		for (int f = 0; f < filas; f++) {
			for (int c = 0; c < cols; c++) {
				if (casillas[f][c].contenido == 0) {
					int cant = contarCoordenada(f, c);
					casillas[f][c].contenido = cant;
				}
			}
		}
	}

	int contarCoordenada(int fila, int columna) {
		int total = 0;
		if (fila - 1 >= 0 && columna - 1 >= 0) {
			if (casillas[fila - 1][columna - 1].contenido == 80)
				total++;
		}
		if (fila - 1 >= 0) {
			if (casillas[fila - 1][columna].contenido == 80)
				total++;
		}
		if (fila - 1 >= 0 && columna + 1 < filas) {
			if (casillas[fila - 1][columna + 1].contenido == 80)
				total++;
		}

		if (columna + 1 < filas) {
			if (casillas[fila][columna + 1].contenido == 80)
				total++;
		}
		if (fila + 1 <filas && columna + 1 <filas) {
			if (casillas[fila + 1][columna + 1].contenido == 80)
				total++;
		}

		if (fila + 1 < filas) {
			if (casillas[fila + 1][columna].contenido == 80)
				total++;
		}
		if (fila + 1 < filas && columna - 1 >= 0) {
			if (casillas[fila + 1][columna - 1].contenido == 80)
				total++;
		}
		if (columna - 1 >= 0) {
			if (casillas[fila][columna - 1].contenido == 80)
				total++;
		}
		return total;
	}

	private void recorrer(int fil, int col) {
		if (fil >= 0 && fil < filas && col >= 0 && col < cols) {
			if (casillas[fil][col].contenido == 0) {
				casillas[fil][col].destapado = true;
				casillas[fil][col].contenido = 50;
				recorrer(fil, col + 1);
				recorrer(fil, col - 1);
				recorrer(fil + 1, col);
				recorrer(fil - 1, col);
				recorrer(fil - 1, col - 1);
				recorrer(fil - 1, col + 1);
				recorrer(fil + 1, col + 1);
				recorrer(fil + 1, col - 1);
			} else if (casillas[fil][col].contenido >= 1
					&& casillas[fil][col].contenido <= filas) {
				casillas[fil][col].destapado = true;
			}
		}
	}

}
