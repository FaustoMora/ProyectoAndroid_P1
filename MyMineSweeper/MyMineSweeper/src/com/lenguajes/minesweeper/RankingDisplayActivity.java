package com.lenguajes.minesweeper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class RankingDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking_display);
		
		/*ImageButton matriz[] = new ImageButton[4];
		TableRow row = new TableRow(this);
		
		for(int i=0;i<4;i++){
			matriz[i]= new ImageButton(this);
			matriz[i].setVisibility(0);
			matriz[i].setBackgroundResource(R.drawable.square);
			row.addView(matriz[i]);
		}
		

		row.setVisibility(0);
		setContentView(row);*/
		
		/*TableLayout table = new TableLayout(this);
		
		FrameLayout tableroCeldas[][] = new FrameLayout[8][8];
		TableRow row = new TableRow(this);
		
		for(int fila=0;fila<8;fila++){
			for(int colum=0;colum<8;colum++){
				tableroCeldas[fila][colum].setBackgroundResource(R.drawable.square);
				row.addView(tableroCeldas[fila][colum]);
			}
		}
		
		table.addView(row);
		setContentView(table);*/
		
		
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
		getMenuInflater().inflate(R.menu.ranking_display, menu);
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
