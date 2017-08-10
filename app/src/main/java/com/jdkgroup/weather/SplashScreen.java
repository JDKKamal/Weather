package com.jdkgroup.weather;

import android.os.Bundle;
import android.view.MotionEvent;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.weather.activity.WeatherActivity;

public class SplashScreen extends BaseActivity {

    private Thread mSplashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        // Wait given period of time or exit on touch
                        wait(3000L);
                    }
                } catch (InterruptedException ex) {
                }
                launch(WeatherActivity.class);
                finish();
            }
        };

        mSplashThread.start();
    }

    // Processes splash screen touch events
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if (evt.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (mSplashThread) {
                mSplashThread.notifyAll();
            }
        }
        return true;
    }
}
