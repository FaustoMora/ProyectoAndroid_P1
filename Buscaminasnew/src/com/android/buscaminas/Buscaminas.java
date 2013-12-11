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
import android.os.Handler;
import android.text.format.Formatter;
import android.text.format.Time;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

@SuppressLint({ "DrawAllocation", "NewApi" })
public class Buscaminas extends Activity implements OnTouchListener{
Button boton_reinicio;
ToggleButton banderaOn;
ToggleButton banderaOff;
	
	private Tablero tableroCeldas;
	private Celda[][] celdas;
	private boolean activo = true;
	private int filas=0;
	private int cols=0;
	int id=0;
	long startTime=0;
	boolean firstEvent=false;
	private int minas=0;
    int condicionganar = (filas*cols)-minas;
    TextView cronometroText;
    TextView banderaText;
    boolean banderaon=false;
    boolean banderaoff=false;
    int cantBandera=0; //la bandera debe estar al doble es decir ahora pone 10
	private LinearLayout panel;
	Handler timerHandler = new Handler();
	Runnable timerRunnable = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			long millis = System.currentTimeMillis() - startTime;
			int seconds = (int)(millis/1000);
			int minutes = seconds/60;
			seconds = seconds%60;
			
			Buscaminas.this.cronometroText.setText(String.format("%d:%02d",minutes,seconds));
			timerHandler.postDelayed(this,500);
		}
    	
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buscaminas);
		// Eleccion de acuerdo al nivel escogido
				
		Bundle info = getIntent().getExtras();
		int i=1,j=2,k=3,l=4;
		if(info.getInt("choice")==i){filas=8;cols=8;minas=10;this.cantBandera=minas;this.id=1;} //Se escoje el nivel FACIL
		if(info.getInt("choice")==j){filas=16;cols=16;minas=40;this.cantBandera=minas;this.id=2;} // Se escoje el nivel NORMAL
		if(info.getInt("choice")==k){filas=16;cols=30;minas=99;this.cantBandera=minas;this.id=3;} // Se escoje el nivel AVANZADO O EXPERTO
		
		
		
		panel = (LinearLayout) findViewById(R.id.layout2);
		tableroCeldas = new Tablero(this);
		tableroCeldas.setOnTouchListener(this);
		
                this.boton_reinicio = (Button) findViewById(R.id.button_reinicio);
                this.banderaOn = (ToggleButton)findViewById(R.id.button_banderaOn);
                this.banderaOff = (ToggleButton)findViewById(R.id.button_banderaOff);
                
                
                this.cronometroText = (TextView)findViewById(R.id.cronometroText);
                this.banderaText = (TextView)findViewById(R.id.banderatext);
                this.banderaText.setText(String.format("%02d",cantBandera));                
                
                boton_reinicio.setBackgroundResource(R.drawable.caritas);
                boton_reinicio.setOnClickListener(new OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                                // TODO Auto-generated method stub
                                reiniciarActividad(Buscaminas.this,Buscaminas.this.id);
    
                        }
                });
                
               
                this.banderaOn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						banderaOn.setSelectAllOnFocus(true);
						Buscaminas.this.banderaon=true;
						Buscaminas.this.banderaoff=false;
						banderaOff.setSelected(false);
						
					}
				});
                
                this.banderaOff.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						banderaOff.setSelected(false);
						Buscaminas.this.banderaon=true;
						Buscaminas.this.banderaoff=false;
						banderaOn.setSelected(false);
					}
				});
                
                

		panel.addView(tableroCeldas);
		celdas = new Celda[filas][cols];
		for (int f = 0; f <filas; f++) {
			for (int c = 0; c < cols; c++) {
				celdas[f][c] = new Celda();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu., menu);
		return true;
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (activo)
			
			for (int f = 0; f < filas; f++) {
				for (int c = 0; c < cols; c++) {
                //comprobacion de la casilla hacia si misma
					if (celdas[f][c].limites((int) event.getX(),
							(int) event.getY())) {
                                    //if para poner bandera
                                        if(this.banderaon){
                                                        if(this.cantBandera<minas && !this.celdas[f][c].descubierto){
                                                                this.colocarBanderaCelda(f,c);
                                                                this.updateBandera(true);                                                             
                                                        }
                                        }
                                        if(this.banderaoff){

                                                if(this.celdas[f][c].banderaCelda && !this.celdas[f][c].descubierto){
                                                        this.removBanderaCelda(f, c);
                                                        this.updateBandera(false);
                                                }
                                        }else{
                                        if(!this.banderaon && !this.banderaoff){
                                        //validamos el primer click evento
                                        if(!this.firstEvent){
                                                this.firstEvent=true;
                                                startTime = System.currentTimeMillis();
                                    			timerHandler.postDelayed(timerRunnable,	500);
                                                this.setMinas(f,c);
                                                this.setNumMinas();
                                                }

                                        
                                        //destapamos de todas formas
                                        if(!this.celdas[f][c].banderaCelda){
                                                celdas[f][c].descubierto = true;
                                                
                                                if (celdas[f][c].content == 80) {
                                                	boton_reinicio.setBackgroundResource(R.drawable.muerto);
                                                		timerHandler.removeCallbacks(timerRunnable);
                                                        Toast.makeText(this, "Booooooooommmmmmmmmmmm",
                                                                        Toast.LENGTH_LONG).show();
                                                        //activo = false;
                                                }
                                                
                                                if (celdas[f][c].content == 0){
                                                        checkAround(f, c);
                                                }
                                                
                                        }
                                        }

                                        } //end bool bandera
                                        tableroCeldas.invalidate();
                                        
                                        }

                                }//for col
                        }// for fil
			tableroCeldas.invalidate();
			
            /*try{
            Thread.sleep(2000);
            }catch(Exception e){}
            */
                
                if (checkWin()) {
                        //aqui deberia ir la de forma para almacenar el ranking
                        activo = false;
                }

                
                return true;
        }
        //funcion que chequea si gana
        private boolean checkWin() {
                
                int acum=0;
                for(int fil=0;fil<this.filas;fil++){
                        for(int col=0;col<this.filas;col++){
                                if(this.celdas[fil][col].content!=80 && this.celdas[fil][col].descubierto){
                                        acum++;
                                }
                        }
                }
                if(acum-this.condicionganar==0){
                        return true;
                }else{
                        return false;
                }
        }

        public void reiniciarActividad(Activity actividad, int id){
         Intent intent = getIntent();
         intent.putExtra("reinicioNivel",id);
         actividad.finish();
         //llamamos a la actividad
         actividad.startActivity(intent);
         //finalizamos la actividad actual
        }
        

        public void updateBandera(boolean a){
        	if(a){
        		this.cantBandera++;
        	}else{
        		this.cantBandera--;
        	}
        }
  
        
	class Tablero extends View {

		public Tablero(Context context) {
			super(context);
		}

		protected void onDraw(Canvas canvas) {
			canvas.drawRGB(0, 0, 0);
			int size = 0;
			if (canvas.getWidth() < canvas.getHeight())
				size = tableroCeldas.getWidth();
			else
				size = tableroCeldas.getHeight();
			int sizecell = size / filas;
			int tolerancia;
			Paint paint = new Paint(20);
			Paint paint2 = new Paint();
			if(id==1){
				paint2.setTextSize(20);tolerancia=0;}
			else {
				paint2.setTextSize(10);
				tolerancia=10;}
			//paint2.setTypeface(Typeface.DEFAULT_BOLD);
			paint2.setARGB(255, 0, 0, 255);
			Paint paintlinea1 = new Paint();
			//paintlinea1.setARGB(0, 255, 255, 255);
		
			int filaact = 0;
			for (int f = 0; f < filas; f++) {
				for (int c = 0; c < cols; c++) {
					celdas[f][c].fijarxy(c * sizecell, filaact, sizecell);
					if (celdas[f][c].descubierto == false){
						//Visible
						paint.setARGB(255,230,230,250 ); //100,245,245,220
					}else 						//BackGround
						
						//this.findViewById(R.id.button1);
						//Background square
						
						paint.setARGB(255,245,222,179); //200,227,168,87
					canvas.drawRect(c * sizecell, filaact, c * sizecell
							+ sizecell - 2, filaact + sizecell - 2, paint);
					
					if (celdas[f][c].content >= 1
							&& celdas[f][c].content <= filas
							&& celdas[f][c].descubierto){
						
						canvas.drawText(String.valueOf(celdas[f][c].content), c	* sizecell + (sizecell / 2) - filas + tolerancia,filaact + sizecell / 2, paint2);//}
					}
					
					//Aqui esta la bomba
					if (celdas[f][c].content == 80
							&& celdas[f][c].descubierto) {
						Paint bomba = new Paint();
						/*bomba.setARGB(255, 255, 0, 0);
						canvas.drawCircle(c * anchocua + (anchocua / 2),
								filaact + (anchocua / 2), filas, bomba);*/
						Picture p = new Picture();
						canvas.drawPicture(new Picture());
					}

				}
				filaact = filaact + sizecell;
			}
		}
	}
        //colocar bandera en celda y remover bandera en celda
        
        private void removBanderaCelda(int fil,int co){
                celdas[fil][co].banderaCelda=false;
        }
        
        private void colocarBanderaCelda(int fil, int co){
                
                celdas[fil][co].banderaCelda=true;
        }

        private void setMinas(int fil , int co) {

                do {
                        int fila = (int) (Math.random() * filas);
                        int columna = (int) (Math.random() * filas);
                        //le agrego posicion actual para primer click
                        if(fila!= fil || columna !=co){
                                if (celdas[fila][columna].content == 0) {
                                        //80 significa que tiene bomba
                                        celdas[fila][columna].content = 80;
                                        minas--;
                                }

                        }
                } while (minas != 0);
                
        }

	private void setNumMinas() {
		for (int f = 0; f < filas; f++) {
			for (int c = 0; c < cols; c++) {
				if (celdas[f][c].content == 0) {
					int cant = verificarMinas(f, c);
					celdas[f][c].content = cant;
				}
			}
		}
	}

	int verificarMinas(int row, int colum) {
		int cantidad = 0;
		if (row - 1 >= 0 && colum - 1 >= 0) {
			if (celdas[row - 1][colum - 1].content == 80)cantidad++;
		}
		if (row - 1 >= 0) {
			if (celdas[row - 1][colum].content == 80)cantidad++;
		}
		if (row - 1 >= 0 && colum + 1 < filas) {
			if (celdas[row - 1][colum + 1].content == 80)cantidad++;
		}

		if (colum + 1 < filas) {
			if (celdas[row][colum + 1].content == 80)cantidad++;
		}
		if (row + 1 <filas && colum + 1 <filas) {
			if (celdas[row + 1][colum + 1].content == 80)cantidad++;
		}

		if (row + 1 < filas) {
			if (celdas[row + 1][colum].content == 80)cantidad++;
		}
		if (row + 1 < filas && colum - 1 >= 0) {
			if (celdas[row + 1][colum - 1].content == 80)cantidad++;
		}
		if (colum - 1 >= 0) {
			if (celdas[row][colum - 1].content == 80)cantidad++;
		}
		return cantidad;
	}

	private void checkAround(int fil, int col) {
		if (fil >= 0 && fil < filas && col >= 0 && col < cols) {
			if (celdas[fil][col].content == 0) {
				celdas[fil][col].descubierto = true;
				celdas[fil][col].content = 50;
				checkAround(fil, col + 1);
				checkAround(fil, col - 1);
				checkAround(fil + 1, col);
				checkAround(fil - 1, col);
				checkAround(fil - 1, col - 1);
				checkAround(fil - 1, col + 1);
				checkAround(fil + 1, col + 1);
				checkAround(fil + 1, col - 1);
			} else if (celdas[fil][col].content >= 1
					&& celdas[fil][col].content <= filas) {
				celdas[fil][col].descubierto = true;
			}
		}
	}
	

}
