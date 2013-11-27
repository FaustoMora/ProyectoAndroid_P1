package com.example.cronometro;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class CronometroMainActivity extends Activity {
	
	TextView timerTextView;
    long startTime = 0;
    Button boton;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			long millis = System.currentTimeMillis() - startTime;
			int seconds = (int)(millis/1000);
			int minutes = seconds/60;
			seconds = seconds%60;
			
			timerTextView.setText(String.format("%d:%02d",minutes,seconds));
			timerHandler.postDelayed(this,500);
		}
    	
    };
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cronometro_main);
		
		timerTextView = (TextView) findViewById(R.id.timerTextView);
		this.boton = (Button)findViewById(R.id.button1);
		
		boton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startTime = System.currentTimeMillis();
				timerHandler.postDelayed(timerRunnable,	500);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cronometro_main, menu);
		return true;
	}

}
