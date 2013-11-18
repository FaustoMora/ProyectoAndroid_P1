package com.lenguajes.minesweeper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class FacilGameActivity extends Activity {
	
	Button tablero[][];
	TableLayout tabla;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facil_game);
		
		tabla = (TableLayout)findViewById(R.id.tablero_facil);
		 
		 for(int fila=0;fila<8;fila++){
			 
			 TableRow row = new TableRow(this);
			 row.setLayoutParams(new LayoutParams(300,300));
			 
			 for(int colum=0; colum<8;colum++){
				 
				 this.tablero[fila][colum]= new Button(this);
				 this.tablero[fila][colum].setLayoutParams(new LayoutParams(300,300));
				 
				 row.addView(this.tablero[fila][colum]);
			 }
			 tabla.addView(row,new LayoutParams(300,300));
		 }
		 
		 setContentView(tabla);
		// Show the Up button in the action bar.
		setupActionBar();
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.facil_game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
