package com.hope.cs.fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestaurantEmailLogin extends AppCompatActivity {
    TextInputLayout email, password;
    Button signIn;
    TextView forgotPassword, signUp;
    FirebaseAuth FAuth;
    String EmailID, Password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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
                                        FAuth=FirebaseAuth.getInstance();

                                        databaseReference = firebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid()+"/Role");

                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String role = snapshot.getValue(String.class);

                                                if(role.equals("Restaurant")){
                                                    startActivity(new Intent(RestaurantEmailLogin.this,RestaurantPanel_BottomNav.class));
                                                    finish();

                                                }
                                                if(role.equals("Customer")){
                                                    startActivity(new Intent(RestaurantEmailLogin.this,CustomerPanel_BottomNav.class));
                                                    finish();

                                                }
                                                if(role.equals("DeliveryPerson")) {
                                                    startActivity(new Intent(RestaurantEmailLogin.this, DeliveryGuyPanel_BottomNav.class));
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(RestaurantEmailLogin.this,error.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }else{
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