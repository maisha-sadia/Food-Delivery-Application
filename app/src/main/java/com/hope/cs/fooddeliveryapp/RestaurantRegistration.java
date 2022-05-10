package com.hope.cs.fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;



public class RestaurantRegistration extends AppCompatActivity {
    String[] Maharashtra = {"Mumbai","Pune","Nashik"};
    String[] Madhyapradesh = {"Bhopal","Indore","Ujjain"};

    TextInputLayout forename,surname,email,password,confirmPassword,phoneNumber,postCode,area,houseNumber;
    Spinner citySpin;
    Button register, emailSignInButton, phoneSignInButton;
    CountryCodePicker Cpp;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String Fname,Sname,EmailId,Pass,CPass, PNum,HNum,PCode,Area, City;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_registration);

        forename = (TextInputLayout)findViewById(R.id.forename);
        surname = (TextInputLayout)findViewById(R.id.surname);
        email = (TextInputLayout)findViewById(R.id.emailAddress);
        password = (TextInputLayout)findViewById(R.id.password);
        confirmPassword = (TextInputLayout)findViewById(R.id.confirmPassword);
        phoneNumber = (TextInputLayout)findViewById(R.id.phoneNumber);
        postCode = (TextInputLayout)findViewById(R.id.postCode);
        area = (TextInputLayout)findViewById(R.id.area);
        houseNumber = (TextInputLayout)findViewById(R.id.houseNo);
        forename = (TextInputLayout)findViewById(R.id.forename);
        citySpin = (Spinner)findViewById(R.id.city);

        register = (Button)findViewById(R.id.newRegister);
        emailSignInButton = (Button)findViewById(R.id.emailLoginButton);
        phoneSignInButton = (Button)findViewById(R.id.phoneLoginButton);

        citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object value = adapterView.getItemAtPosition(i);
                City = value.toString().trim();
//                if(City.equals("Merseyside")){
//                    ArrayList<String> list = new ArrayList<>();
//                    for(String cities: Merseyside){
//                        list.add(cities);
//                    }
//                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RestaurantRegistration.this,android.R.layout.simple_spinner_item,list);
//                    citySpin.setAdapter(arrayAdapter);
//                }
//                if(City.equals("Hampshire")){
//                    ArrayList<String> list = new ArrayList<>();
//                    for(String cities: Hampshire){
//                        list.add(cities);
//                    }
//                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RestaurantRegistration.this,android.R.layout.simple_spinner_item,list);
//                    citySpin.setAdapter(arrayAdapter);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String role = getIntent().getStringExtra("Role").toString().trim();

        databaseReference = firebaseDatabase.getInstance().getReference(role);
        FAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fname = forename.getEditText().getText().toString().trim();
                Sname = surname.getEditText().getText().toString().trim();
                EmailId = email.getEditText().getText().toString().trim();
                Pass = password.getEditText().getText().toString().trim();
                CPass = confirmPassword.getEditText().getText().toString().trim();
                PNum = phoneNumber.getEditText().getText().toString().trim();
                Area = area.getEditText().getText().toString().trim();
                HNum = houseNumber.getEditText().getText().toString().trim();
                PCode = postCode.getEditText().getText().toString().trim();

                if (isValid()){
                    final ProgressDialog mDialog = new ProgressDialog(RestaurantRegistration.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Please wait");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(EmailId,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userId);
                                final HashMap<String , String> hashMap = new HashMap<>();
                                hashMap.put("Role",role);
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        HashMap<String,String> hashMap1 = new HashMap<>();
                                        hashMap1.put("First Name",Fname);
                                        hashMap1.put("Last Name",Sname);
                                        hashMap1.put("Email Address",EmailId);
                                        hashMap1.put("Phone Number",PNum);
                                        hashMap1.put("Area",Area);
                                        hashMap1.put("House Number",HNum);
                                        hashMap1.put("Post Code",PCode);
                                        hashMap1.put("Area",Area);
                                        hashMap1.put("City",City);
                                        hashMap1.put("Password",Pass);
                                        hashMap1.put("Confirm Password",CPass);


                                        firebaseDatabase.getInstance().getReference(role)
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mDialog.dismiss();

                                                FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if(task.isSuccessful()){
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantRegistration.this);
                                                            builder.setMessage("You Have Registered! Make Sure To Verify Your Email");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    dialog.dismiss();
                                                                    Intent loginPage = new Intent(RestaurantRegistration.this,RestaurantEmailLogin.class);
                                                                    startActivity(loginPage);
                                                                }
                                                            });
                                                            AlertDialog Alert = builder.create();
                                                            Alert.show();
                                                        }else{
                                                            mDialog.dismiss();
                                                            AlertDialogueBox.showAlert(RestaurantRegistration.this,"Error",task.getException().getMessage());
                                                        }
                                                    }
                                                });

                                            }
                                        });

                                    }
                                });
                            }
                        }
                    });
                }
//
            }
        });

    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        email.setErrorEnabled(false);
        email.setError("");
        forename.setErrorEnabled(false);
        forename.setError("");
        surname.setErrorEnabled(false);
        surname.setError("");
        password.setErrorEnabled(false);
        password.setError("");
        phoneNumber.setErrorEnabled(false);
        phoneNumber.setError("");
        confirmPassword.setErrorEnabled(false);
        confirmPassword.setError("");
        area.setErrorEnabled(false);
        area.setError("");
        houseNumber.setErrorEnabled(false);
        houseNumber.setError("");
        postCode.setErrorEnabled(false);
        postCode.setError("");

        boolean isValid=false,isValidhouseno=false,isValidlname=false,isValidname=false,isValidemail=false,isValidpassword=false,isValidconfpassword=false,isValidmobilenum=false,isValidarea=false,isValidpincode=false;
        if(TextUtils.isEmpty(Fname)){
            forename.setErrorEnabled(true);
            forename.setError("Enter First Name");
        }else{
            isValidname = true;
        }
        if(TextUtils.isEmpty(Sname)){
            surname.setErrorEnabled(true);
            surname.setError("Enter Last Name");
        }else{
            isValidlname = true;
        }
        if(TextUtils.isEmpty(EmailId)){
            email.setErrorEnabled(true);
            email.setError("Email Is Required");
        }else{
            if(EmailId.matches(emailpattern)){
                isValidemail = true;
            }else{
                email.setErrorEnabled(true);
                email.setError("Enter a Valid Email Id");
            }
        }
        if(TextUtils.isEmpty(Pass)){
            password.setErrorEnabled(true);
            password.setError("Enter Password");
        }else{
            if(Pass.length()<8){
                password.setErrorEnabled(true);
                password.setError("Password is Weak");
            }else{
                isValidpassword = true;
            }
        }
        if(TextUtils.isEmpty(CPass)){
            confirmPassword.setErrorEnabled(true);
            confirmPassword.setError("Enter Password Again");
        }else{
            if(!Pass.equals(CPass)){
                confirmPassword.setErrorEnabled(true);
                confirmPassword.setError("Password Doesn't Match");
            }else{
                isValidconfpassword = true;
            }
        }
        if(TextUtils.isEmpty(PNum)){
            phoneNumber.setErrorEnabled(true);
            phoneNumber.setError("Mobile Number Is Required");
        }else{
            if(PNum.length()<10){
                phoneNumber.setErrorEnabled(true);
                phoneNumber.setError("Invalid Mobile Number");
            }else{
                isValidmobilenum = true;
            }
        }
        if(TextUtils.isEmpty(Area)){
            area.setErrorEnabled(true);
            area.setError("Area Is Required");
        }else{
            isValidarea = true;
        }
        if(TextUtils.isEmpty(PCode)){
            postCode.setErrorEnabled(true);
            postCode.setError("Please Enter PostCode");
        }else{
            isValidpincode = true;
        }
        if(TextUtils.isEmpty(HNum)){
            houseNumber.setErrorEnabled(true);
            houseNumber.setError("Fields Can't Be Empty");
        }else{
            isValidhouseno = true;
        }

        isValid = (isValidarea && isValidconfpassword && isValidpassword && isValidpincode && isValidemail && isValidmobilenum && isValidname && isValidhouseno && isValidlname) ? true : false;
        return isValid;

    }
}