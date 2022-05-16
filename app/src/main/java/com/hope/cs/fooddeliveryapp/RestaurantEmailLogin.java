package com.hope.cs.fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RestaurantEmailLogin extends AppCompatActivity {
    TextInputLayout email, password;
    Button signIn;
    TextView forgotPassword, signUp;
    FirebaseAuth FAuth;
    String EmailID, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_email_login);
        try {
            email= (TextInputLayout)findViewById(R.id.loginEmailAddress);
            password=(TextInputLayout)findViewById(R.id.loginPassword);
            forgotPassword=(TextView) findViewById(R.id.forgotPassword);
            signIn = (Button) findViewById(R.id.LoginButton);
            signUp=(TextView) findViewById(R.id.textView3);
            FAuth = FirebaseAuth.getInstance();

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EmailID = email.getEditText().getText().toString().trim();
                    Password = password.getEditText().getText().toString().trim();

                    if(isValid()){
                        final ProgressDialog mDialog = new ProgressDialog(RestaurantEmailLogin.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Signing In Please Wait");
                        mDialog.show();

                        FAuth.signInWithEmailAndPassword(EmailID,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mDialog.dismiss();

                                    if(FAuth.getCurrentUser().isEmailVerified()){
                                        mDialog.dismiss();
                                        Toast.makeText(RestaurantEmailLogin.this,"Successfully Logged In", Toast.LENGTH_SHORT).show();
                                        Intent Z= new Intent(RestaurantEmailLogin.this, RestaurantPanel_BottomNav.class);
                                        startActivity(Z);

                                    }else {
                                        AlertDialogueBox.showAlert(RestaurantEmailLogin.this,"Verification Failed", "Please verify your email");
                                    }
                                }else
                                {
                                    mDialog.dismiss();
                                    AlertDialogueBox.showAlert(RestaurantEmailLogin.this,"Unexpected Error", task.getException().getMessage());
                                }
                            }
                        });

                    }
                }
            });
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Takes the user to the select user phase where they can choose the type of user they want to register as
                    Intent registration = new Intent(RestaurantEmailLogin.this,SelectUser.class);
                    registration.putExtra("Home","Register");
                    startActivity(registration);
                    finish();
                }
            });
            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(RestaurantEmailLogin.this, RestaurantForgotPassword.class));
                    finish();
                }
            });

        }catch (Exception exception){
            Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid(){
        email.setErrorEnabled(false);
        email.setError("");
        password.setErrorEnabled(false);
        password.setError("");

        boolean isValid=false,isValidEmail=false,isValidPassword=false;
        if(TextUtils.isEmpty(EmailID)){
            email.setErrorEnabled(true);
            email.setError("Email is required");
        }else{
            if(EmailID.matches(emailPattern)){
                isValidEmail=true;
            }else{
                email.setErrorEnabled(true);
                email.setError("Invalid Email");
            }
        }
        if(TextUtils.isEmpty(Password)){
            password.setErrorEnabled(true);
            password.setError("Password is required");
        }else{
           isValidPassword=true;
        }
        isValid=(isValidEmail&&isValidPassword)?true:false;

        return isValid;

    }
}