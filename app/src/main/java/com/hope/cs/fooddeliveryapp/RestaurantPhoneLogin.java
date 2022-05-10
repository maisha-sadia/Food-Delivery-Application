package com.hope.cs.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class RestaurantPhoneLogin extends AppCompatActivity {
    EditText number;
    Button sendOTP, signInEmail;
    TextView signUp;
    CountryCodePicker Cpp;
    FirebaseAuth FAuth;
    String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_phone_login);

        number = (EditText) findViewById(R.id.phoneNumber);
        sendOTP = (Button) findViewById(R.id.sendOTP);
        signUp = (TextView) findViewById(R.id.phoneRegister);
        signInEmail = (Button) findViewById(R.id.signInEmail);
        Cpp = (CountryCodePicker) findViewById(R.id.countryCodeHolder);

        FAuth = FirebaseAuth.getInstance();
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = number.getText().toString().trim();
                String phoneNumWithCpp = Cpp.getSelectedCountryCodeWithPlus()+phoneNumber;
                Intent otpPage = new Intent(RestaurantPhoneLogin.this, RestaurantSendOTP.class);
                otpPage.putExtra("PhoneNumber",phoneNumWithCpp);

                startActivity(otpPage);
                finish();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Takes the user to the select user phase where they can choose the type of user they want to register as
                Intent registration = new Intent(RestaurantPhoneLogin.this,SelectUser.class);
                registration.putExtra("Home","Registration");
                startActivity(registration);
                finish();
            }
        });
        signInEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestaurantPhoneLogin.this, RestaurantEmailLogin.class));
                finish();
            }
        });}
}