package com.destinyapp.jempolok.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.destinyapp.jempolok.Model.Musupadi;
import com.destinyapp.jempolok.R;
import com.destinyapp.jempolok.Services;
import com.destinyapp.jempolok.SharedPreferance.DB_Helper;

public class SplashScreenActivity extends AppCompatActivity {
    Musupadi musupadi;
    LottieAnimationView lottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musupadi = new Musupadi();
        setContentView(R.layout.activity_splash_screen);
        lottie = findViewById(R.id.lottie);
        final Handler handler = new Handler();
        final DB_Helper dbHelper = new DB_Helper(SplashScreenActivity.this);
        Cursor cursor = dbHelper.checkUser();
        if (cursor.getCount()>0){
            musupadi.Back(SplashScreenActivity.this);
            finish();
        }else{
            lottie.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }
}