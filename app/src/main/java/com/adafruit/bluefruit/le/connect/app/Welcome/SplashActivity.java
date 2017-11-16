package com.adafruit.bluefruit.le.connect.app.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adafruit.bluefruit.le.connect.app.MainActivity;
import com.adafruit.bluefruit.le.connect.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {
    private ProgressBar mProgress;
    private Timer timer;
    private int i=0;
    TextView textView;

    // Splash screen timer
	// ProgressBar
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

        System.out.println("Splash");

        mProgress = (ProgressBar) findViewById(R.id.pb);
        mProgress.setProgress(0);
        textView=(TextView)findViewById(R.id.textView);
        textView.setText("");
		//new Handler().postDelayed(new Runnable() {
        // Start lengthy operation in a background thread
        final long period = 40;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            public void run() {

                //this repeats every 100 ms
                if (i<100){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(i)+"%");
                        }
                    });
                    mProgress.setProgress(i);
                    i++;
                }else {
                    //start button

                    //closing the timer
                    timer.cancel();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    // close this activity
                    finish();
                }
            }
        }, 0, period);
    }

}
