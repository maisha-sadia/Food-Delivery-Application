package com.hope.cs.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class SelectUser extends AppCompatActivity {

    ImageView bgImage;
    Button restaurantOwner,customer, deliveryGuy;
    Intent intent;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        final Animation ZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        final Animation ZoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out);

        bgImage = findViewById(R.id.bgImage2);
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

        intent = getIntent();
        type = intent.getStringExtra("Home").toString().trim();

        restaurantOwner = (Button)findViewById(R.id.restaurantOwner);
        customer = (Button)findViewById(R.id.customer);
        deliveryGuy = (Button)findViewById(R.id.deliveryGuy);

        restaurantOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Email")){
                    Intent emailLogin = new Intent(SelectUser.this,RestaurantEmailLogin.class);
                    startActivity(emailLogin);
                    finish();
                } if(type.equals("Phone")){
                    Intent phoneLogin = new Intent(SelectUser.this,RestaurantPhoneLogin.class);
                    startActivity(phoneLogin);
                    finish();
                } if(type.equals("Register")){
                    Intent registration = new Intent(SelectUser.this,RestaurantRegistration.class);
                    startActivity(registration);
                    finish();
                }
            }
        });
  customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Email")){
                    Intent emailLogin = new Intent(SelectUser.this,CustomerEmailLogin.class);
                    startActivity(emailLogin);
                    finish();
                } if(type.equals("Phone")){
                    Intent phoneLogin = new Intent(SelectUser.this,CustomerPhoneLogin.class);
                    startActivity(phoneLogin);
                    finish();
                } if(type.equals("Register")){
                    Intent registration = new Intent(SelectUser.this,CustomerRegistration.class);
                    startActivity(registration);
                    finish();
                }
            }
        });
  deliveryGuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Email")){
                    Intent emailLogin = new Intent(SelectUser.this,DeliveryGuyEmailLogin.class);
                    startActivity(emailLogin);
                    finish();
                } if(type.equals("Phone")){
                    Intent phoneLogin = new Intent(SelectUser.this,DeliveryGuyPhoneLogin.class);
                    startActivity(phoneLogin);
                    finish();
                } if(type.equals("Register")){
                    Intent registration = new Intent(SelectUser.this,DeliveryGuyRegistration.class);
                    startActivity(registration);
                    finish();
                }
            }
        });

    }
}