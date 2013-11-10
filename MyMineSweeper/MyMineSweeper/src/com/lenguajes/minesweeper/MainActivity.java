package com.lenguajes.minesweeper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	public final static String MAIN_ACT_IMG = "com.lenguajes.minesweeper"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button1 = (Button) findViewById(R.id.nwgame_button);
		Button button2 = (Button) findViewById(R.id.instruc_button);
		Button button3 = (Button) findViewById(R.id.rank_button);
		Button button4 = (Button) findViewById(R.id.exit_button);
		
		button1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(view.getContext(),NwGameDisplayActivity.class);
				startActivity(intent);
			}
		}
		);
		
		button2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(view.getContext(),InstrucDisplayActivity.class);
				startActivity(intent);
			}
		}
		);
		
		button3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(view.getContext(),RankingDisplayActivity.class);
				startActivity(intent);
			}
		}
		);
		
		
		button4.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				System.exit(1);
			}
		}
		);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	
	
	/*bloque de la web para ir al home-root
	Intent intent = new Intent(Intent.ACTION_MAIN);
	intent.addCategory(Intent.CATEGORY_HOME);
	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	startActivity(intent);*/

}
