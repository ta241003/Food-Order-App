package com.example.deliveryfoodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliveryfoodapp.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASS_SCREEN = 5000;

    // Variables
    Animation topAnimation, bottomAnimation;
    ImageView image_splash;
    TextView txt_company, txt_slogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Animations
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        // Hooks
        image_splash = findViewById(R.id.image_splash);
        txt_company = findViewById(R.id.txt_company);
        txt_slogan = findViewById(R.id.txt_slogan);

        image_splash.setAnimation(topAnimation);
        txt_company.setAnimation(bottomAnimation);
        txt_slogan.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASS_SCREEN);
    }
    
}