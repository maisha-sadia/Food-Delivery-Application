package com.hope.cs.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {

    Button loginWithEmail, loginWithPhone, register;
    ImageView bgImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final Animation ZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        final Animation ZoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out);

        bgImage = findViewById(R.id.bgImage);
        bgImage.setAnimation(ZoomIn);
        bgImage.setAnimation(ZoomOut);

        ZoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bgImage.setAnimation(ZoomIn);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ZoomIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bgImage.setAnimation(ZoomOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        loginWithEmail = (Button)findViewById(R.id.emailLoginButton);
        loginWithPhone = (Button)findViewById(R.id.phoneLoginButton);
        register = (Button)findViewById(R.id.registerButton);

        loginWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signEmail = new Intent(MainMenu.this,SelectUser.class);
                signEmail.putExtra("Home","Email");
                startActivity(signEmail);
                finish();
            }
        });

        loginWithPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signPhone = new Intent(MainMenu.this,SelectUser.class);
                signPhone.putExtra("Home","Phone");
                startActivity(signPhone);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(MainMenu.this,SelectUser.class);
                signUp.putExtra("Home","Register");
                startActivity(signUp);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}