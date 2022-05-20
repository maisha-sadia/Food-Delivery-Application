package com.hope.cs.fooddeliveryapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    FirebaseAuth FAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        Hiding the title from the splash screen
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        imageView = (ImageView)findViewById(R.id.starting_logo);
        textView = (TextView)findViewById(R.id.ownerName);

        imageView.animate().alpha(0f).setDuration(0);
        textView.animate().alpha(0f).setDuration(0);

        imageView.animate().alpha(1f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                textView.animate().alpha(1f).setDuration(800);
            }
        });


//        Making the splash screen to be visible for 2 seconds and redirecting it to the mainActivity screen
//
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FAuth = FirebaseAuth.getInstance();

                if(FAuth.getCurrentUser()!=null){

                    if(FAuth.getCurrentUser().isEmailVerified()){

                        FAuth=FirebaseAuth.getInstance();

                        databaseReference = firebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid()+"/Role");

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String role = snapshot.getValue(String.class);

                                if(role.equals("Restaurant")){
                                    startActivity(new Intent(SplashScreenActivity.this,RestaurantPanel_BottomNav.class));
                                    finish();

                                }
                                if(role.equals("Customer")){
                                    startActivity(new Intent(SplashScreenActivity.this,CustomerPanel_BottomNav.class));
                                    finish();

                                }
                                if(role.equals("DeliveryPerson")) {
                                    startActivity(new Intent(SplashScreenActivity.this, DeliveryGuyPanel_BottomNav.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(SplashScreenActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);
                        builder.setMessage("Please verify your email");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(SplashScreenActivity.this,MainMenu.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        FAuth.signOut();

                    }

                }else{
                    startActivity(new Intent(SplashScreenActivity.this,MainMenu.class));
                    finish();
                }

            }
        },2000);
    }
}