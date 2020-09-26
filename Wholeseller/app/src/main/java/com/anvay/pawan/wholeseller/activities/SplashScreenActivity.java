/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.anvay.pawan.wholeseller.R;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        AnimationDrawable splashAnimation = (AnimationDrawable) findViewById(R.id.splash_icon).getBackground();
//        splashAnimation.setEnterFadeDuration(800);
//        splashAnimation.start();
        final int SPLASH_DISPLAY_LENGTH = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
