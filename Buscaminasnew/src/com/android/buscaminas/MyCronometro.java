package com.android.buscaminas;
import android.os.Handler;


public class MyCronometro implements Runnable{
	long startTime = 0;
	long millis;
	int seconds;
	int minute;
	
	 Handler timerHandler = new Handler();
	 
	 public MyCronometro(long time){
		 this.startTime=time;
	 }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long millis = System.currentTimeMillis() - startTime;
		int seconds = (int)(millis/1000);
		int minutes = seconds/60;
		seconds = seconds%60;
		
		//timerTextView.setText(String.format("%d:%02d",minutes,seconds));
		timerHandler.postDelayed(this,500);
	}
}
