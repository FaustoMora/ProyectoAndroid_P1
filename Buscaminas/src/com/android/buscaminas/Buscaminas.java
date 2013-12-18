package com.android.buscaminas;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.format.Formatter;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

@SuppressLint({ "DrawAllocation", "NewApi", "ValidFragment" })
public class Buscaminas extends FragmentActivity implements OnTouchListener{
Button boton_reinicio;
Button banderaOn;
	
	private Tablero tableroCeldas;
	private Celda[][] celdas;
	private boolean activo = true;
	private int filas=0, check=0;
	private int cols=0, totalminas=0;
	int id=0;
	long startTime=0;
	boolean firstEvent=false,verificacion;
	private int minas=0;
    TextView cronometroText;
    TextView banderaText;
    boolean banderaon=false;
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
    
    Thread thread = new Thread(){
        @Override
       public void run() {
            try {
               Thread.sleep(3500); 
               Buscaminas.this.finish();
           } catch (Exception e) {
               
           }
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
		if(info.getInt("choice")==l){
			if(info.getInt("filas")>8||info.getInt("filas")<16)
			filas=info.getInt("filas");else filas=16;
			if(info.getInt("colums")>8||info.getInt("colums")<30)
			cols=info.getInt("colums");else cols=16;
			if(info.getInt("minas")>1 && info.getInt("minas")<info.getInt("filas")*info.getInt("colums"))
			minas=info.getInt("minas");else minas=(info.getInt("filas")*info.getInt("colums")-1);
			this.cantBandera=minas;this.id=4;
			} // Se escoje el nivel AVANZADO O EXPERTO
		
		
		panel = (LinearLayout) findViewById(R.id.layout2);
		tableroCeldas = new Tablero(this);
		tableroCeldas.setOnTouchListener(this);
		
                this.boton_reinicio = (Button) findViewById(R.id.button_reinicio);
                this.banderaOn = (Button)findViewById(R.id.button_banderaOn);

                
                
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
                
               
                this.banderaOn.setOnLongClickListener(new OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						banderaOn.setBackgroundResource(R.drawable.mememe);
						return true;
					}
				});
                
                
                this.banderaOn.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						if(event.getAction()==MotionEvent.ACTION_DOWN){
							ClipData data = ClipData.newPlainText("", "");
							DragShadowBuilder shadowBuilder = new DragShadowBuilder(v);
							v.startDrag(data, shadowBuilder, v, 0);
							return true;
						}
						return false;
					}
				});
                
                this.banderaOn.setOnDragListener(new OnDragListener() {
                	
                	
                	
                	Drawable enterShape = getResources().getDrawable(R.drawable.mememe);
                    Drawable normalShape = getResources().getDrawable(R.drawable.mineicon2);
                	
					@SuppressWarnings("deprecation")
					@Override
					public boolean onDrag(View v, DragEvent event) {
						// TODO Auto-generated method stub	
											
						
						switch(event.getAction()){
						case DragEvent.ACTION_DRAG_STARTED:
						    // do nothing
							v.setBackgroundDrawable(enterShape);
						      break;
						    case DragEvent.ACTION_DRAG_ENTERED:
						    	v.setBackgroundDrawable(enterShape);

						      break;
						    case DragEvent.ACTION_DRAG_EXITED:        
						    	v.setBackgroundDrawable(normalShape);
						      break;
						    case DragEvent.ACTION_DROP:
						      // Dropped, reassign View to ViewGroup
						    	v.setBackgroundDrawable(enterShape);
						      break;
						    case DragEvent.ACTION_DRAG_ENDED:
						    	v.setBackgroundDrawable(normalShape);
						    	break;
						    case DragEvent.ACTION_DRAG_LOCATION:
						    	 for(int f=0;f<Buscaminas.this.filas;f++){
							    		for(int c=0;c<Buscaminas.this.cols;c++){
							    			if(celdas[f][c].limites((int)event.getX(), (int)event.getY())){	
							    				//Toast.makeText(Buscaminas.this, String.valueOf("X: "+event.getX()+"-"+event.getY()),Toast.LENGTH_LONG).show();
									    		if(!celdas[f][c].descubierto && !celdas[f][c].banderaCelda){
									    			celdas[f][c].banderaCelda=true;
									    		}
							    			}
									    		
							    		}
							    	}
						    	 return true;
						      default:
						   break;
						}
						return false;
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
		if (activo){
			
	        // do your tasks here
	    	switch(event.getAction()){
			case (MotionEvent.ACTION_DOWN):
				this.boton_reinicio.setBackgroundResource(R.drawable.sorpresa);
				this.banderaOn.setBackgroundResource(R.drawable.mineicon);
		        break; 
		        
			case(MotionEvent.ACTION_UP):
				this.boton_reinicio.setBackgroundResource(R.drawable.sonrisa);	
				this.banderaOn.setBackgroundResource(R.drawable.mineicon2);
				break;
			default:

				break;
	    	}
			
		
		for (int f = 0; f < filas; f++) {
			for (int c = 0; c < cols; c++) {
            //comprobacion de la casilla hacia si misma
				if (celdas[f][c].limites((int) event.getX(),
						(int) event.getY())) {
                                //if para poner bandera
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
                                            		timerHandler.removeCallbacks(timerRunnable);
                                            		boton_reinicio.setBackgroundResource(R.drawable.muerto);
                                            		this.banderaOn.setBackgroundResource(R.drawable.bombboy);
                                            		this.destaparMinas();
                                            		
                                            		LayoutInflater inflater = getLayoutInflater();
                                            		View view = inflater.inflate(R.layout.cust_toast_layout, (ViewGroup)findViewById(R.id.custom_toast));
                                            		Toast toast = new Toast(this);
                                            		toast.setView(view);
                                            		toast.setDuration(Toast.LENGTH_LONG);
                                            		toast.show();
                                                    tableroCeldas.invalidate();
                                                    Buscaminas.this.activo = false;
                                                    thread.start();
                                            }else
                                            
                                            if (celdas[f][c].content == 0)
                                                    checkAround(f, c);
                                            tableroCeldas.invalidate();                                        
                                    }
                                    } //end bool bandera
                                    tableroCeldas.invalidate();

                            }//for col
                    }// for fil
		}
		tableroCeldas.invalidate();

           verificacion = checkWin();
            if (verificacion && activo) {
            	timerHandler.removeCallbacks(timerRunnable);
            	this.banderaOn.setBackgroundResource(R.drawable.winboy);
            	this.boton_reinicio.setBackgroundResource(R.drawable.winface);
            	Toast.makeText(this, "Ganaste", Toast.LENGTH_SHORT);
            	Buscaminas.this.tableroCeldas.invalidate();
            	activo=false;
            	this.LanzarIngresoGanador();
            }
            //Toast.makeText(this, String.valueOf(checkWin()), Toast.LENGTH_SHORT);
	    

		//return activo;
		return true;
	}
			
	
        //funcion que chequea si gana
        private boolean checkWin() {
        	int cant = 0;
    		for (int f = 0; f < filas; f++)
    			for (int c = 0; c < cols; c++)
    				if (celdas[f][c].descubierto)
    					{cant++;
    					//Toast.makeText(this, String.valueOf(cant), Toast.LENGTH_SHORT);
    					//check = 61;
    					}
    					check=((filas*cols)-minas);
    		if (cant == check)
    			return true;
    		else
    			return false;

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
        
        public void destaparMinas(){
        	for(int fil=0;fil<filas;fil++){
        		for(int col=0;col<cols;col++){
        			if(celdas[fil][col].content==80){
        				celdas[fil][col].descubierto=true;
        			}
        		}
        	}
        }
  
        
	class Tablero extends View {

		public Tablero(Context context) {
			super(context);
		}

		protected void onDraw(Canvas canvas) {
			
			Resources res = getResources();
			Bitmap bitmap =BitmapFactory.decodeResource(res, R.drawable.ziggs);
			
			Display display = getWindowManager().getDefaultDisplay();
			Point Size = new Point();
			display.getSize(Size);
			int width = Size.x;
			int height = Size.y;
			
			canvas.drawBitmap(bitmap, null, new Rect(0,0,width,height),null);
			
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
			paint2.setTypeface(Typeface.DEFAULT_BOLD);
			
			
			int filaact = 0;
			for (int f = 0; f < filas; f++) {
				for (int c = 0; c < cols; c++) {
					celdas[f][c].fijarxy(c * sizecell, filaact, sizecell);
					if (celdas[f][c].descubierto==false){
						//Visible
						paint.setARGB(255,102,102,102); 
						
						if(celdas[f][c].banderaCelda){
							Resources resource2 = getResources();
							Bitmap bandera =BitmapFactory.decodeResource(resource2,R.drawable.bandera);
							
							canvas.drawBitmap(bandera, null, new Rect(c * sizecell,filaact,c * sizecell
									+ sizecell - 2,filaact + sizecell - 2),null);

						}
						
					}else 						
					
						paint.setARGB(255,216,216,216); 
						canvas.drawRect(c * sizecell, filaact, c * sizecell
							+ sizecell - 2, filaact + sizecell - 2, paint);
						
					if(celdas[f][c].banderaCelda && !celdas[f][c].descubierto){
						paint.setARGB(255,245,222,0); 
						canvas.drawRect(c * sizecell, filaact, c * sizecell
							+ sizecell - 2, filaact + sizecell - 2, paint);
					}	
					
					if (celdas[f][c].content >= 1
							&& celdas[f][c].content <= filas
							&& celdas[f][c].descubierto){
						
						
						paint2.setARGB(255, (int)(Math.random()*200), (int)(Math.random()*150), (int)(Math.random()*150));
						canvas.drawText(String.valueOf(celdas[f][c].content), c	* sizecell + (sizecell / 2) - filas + tolerancia,filaact + sizecell / 2, paint2);
					}
					
					//Aqui esta la bomba
					if (celdas[f][c].content == 80
							&& celdas[f][c].descubierto) {
							
						Resources resource = getResources();
						Bitmap bomba =BitmapFactory.decodeResource(resource, R.drawable.ic_launcher);
						
						canvas.drawBitmap(bomba, null, new Rect(c * sizecell,filaact,c * sizecell
								+ sizecell - 2,filaact + sizecell - 2),null);
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
        		int acum=minas;
                do {
                        int fila = (int) (Math.random() * filas);
                        int columna = (int) (Math.random() * cols);
                        //le agrego posicion actual para primer click
                        if(fila!= fil || columna !=co){
                                if (celdas[fila][columna].content == 0) {
                                        //80 significa que tiene bomba
                                        celdas[fila][columna].content = 80;
                                        acum--;
                                }

                        }
                } while (acum != 0);
                
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
	
	public class DialogoConfirmacion extends DialogFragment {
		@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {

	        AlertDialog.Builder builder = 
	        		new AlertDialog.Builder(getActivity());
	        
	        builder.setMessage("¿Desea salir del juego?")
	        	   .setTitle("Confirmacion")
	               .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   	Log.i("Dialogos", "Confirmacion Aceptada.");
	       					dialog.cancel();
	       					Intent regreso = new Intent(Buscaminas.this,Principal.class);
	       					startActivity(regreso);                                                                        
	                   }
	               })
	               .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   	Log.i("Dialogos", "Confirmacion Cancelada.");
	       					dialog.cancel();
	                   }
	               });

	        return builder.create();
	    }
	}
	@Override
	public void onBackPressed() {
		/*Intent regreso = new Intent(this,Principal.class);
		startActivity(regreso);  */
		FragmentManager fragmentManager = getSupportFragmentManager();
	    DialogoConfirmacion dialogo = new DialogoConfirmacion();
	    dialogo.show(fragmentManager, "tagConfirmacion" );
	    
	return;
	}
	
	public void LanzarIngresoGanador(){
        if(id!=4){
        Intent perdiste= new Intent(this,IngresoNombre.class);
        perdiste.putExtra("dificultad", id);
        perdiste.putExtra("timer", cronometroText.getText());
        startActivity(perdiste);
        }else {
        	Intent perdiste1= new Intent(this,Principal.class);
        	startActivity(perdiste1);}
	}
}
