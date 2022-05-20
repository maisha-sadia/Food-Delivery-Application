package com.hope.cs.fooddeliveryapp.resFoodPanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hope.cs.fooddeliveryapp.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.UUID;

public class restaurantPostDish extends AppCompatActivity {
    ImageButton imageButton;
    Button post_dish;
    Spinner Dishes;
    TextInputLayout desc,qty,pri;
    String description,quantity,price,dishes;
    Uri imageUri;
    private Uri cropImageUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,data;
    FirebaseAuth FAuth;
    StorageReference ref;
    String RestaurantId , RandomUID , City , Area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_post_dish);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Dishes = (Spinner)findViewById(R.id.dishes);
        desc = (TextInputLayout) findViewById(R.id.description);
        qty = (TextInputLayout) findViewById(R.id.quantity);
        pri = (TextInputLayout) findViewById(R.id.price);
        post_dish = (Button) findViewById(R.id.upload);
        FAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("Food_Details");

        try {
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            data = firebaseDatabase.getInstance().getReference("Restaurant").child(userid);
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Restaurant restaurant = snapshot.getValue(Restaurant.class);
                    City = restaurant.getCity();
                    Area = restaurant.getArea();
                    imageButton = (ImageButton) findViewById(R.id.uploadImage);

                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onSelectImageclick(v);
                        }
                    });
                    post_dish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dishes = Dishes.getSelectedItem().toString().trim();
                            description = desc.getEditText().getText().toString().trim();
                            quantity = qty.getEditText().getText().toString().trim();
                            price = pri.getEditText().getText().toString().trim();

                            if(isValid()){
                                uploadImage();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            Log.e("Error: ",e.getMessage());
        }

    }

    private void uploadImage() {

        if(imageUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(restaurantPostDish.this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            RandomUID = UUID.randomUUID().toString();
            ref = storageReference.child(RandomUID);
            RestaurantId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Food_Details info = new Food_Details(dishes,quantity,price,description,String.valueOf(uri),RandomUID,RestaurantId);
                            firebaseDatabase.getInstance().getReference("Food_Details").child(City).child(Area).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID)
                                    .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    progressDialog.dismiss();
                                    Toast.makeText(restaurantPostDish.this,"Dish Posted Successfully!",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(restaurantPostDish.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int) progress+"%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }

    }

    private boolean isValid() {

        desc.setErrorEnabled(false);
        desc.setError("");
        qty.setErrorEnabled(false);
        qty.setError("");
        pri.setErrorEnabled(false);
        pri.setError("");

        boolean isValidDescription = false,isValidPrice=false,isValidQuantity=false,isValid=false;
        if(TextUtils.isEmpty(description)){
            desc.setErrorEnabled(true);
            desc.setError("Description is Required");
        }else{
            desc.setError(null);
            isValidDescription=true;
        }
        if(TextUtils.isEmpty(quantity)){
            qty.setErrorEnabled(true);
            qty.setError("Enter number of Plates or Items");
        }else{
            isValidQuantity=true;
        }
        if(TextUtils.isEmpty(price)){
            pri.setErrorEnabled(true);
            pri.setError("Please Mention Price");
        }else{
            isValidPrice=true;
        }
        isValid = (isValidDescription && isValidQuantity && isValidPrice)?true:false;
        return isValid;
    }
    private void startCropImageActivity(Uri imageUri){
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private void onSelectImageclick(View v){
        CropImage.startPickImageActivity(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (cropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCropImageActivity(cropImageUri);
        } else {
            Toast.makeText(this, "Cancelling! Permission Not Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            imageUri = CropImage.getPickImageResultUri(this,data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this,imageUri)){
                cropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }else{
                startCropImageActivity(imageUri);
            }
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                ((ImageButton) findViewById(R.id.uploadImage)).setImageURI(result.getUri());
                Toast.makeText(this,"Cropped Successfully!",Toast.LENGTH_SHORT).show();
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Toast.makeText(this,"Failed To Crop"+result.getError(),Toast.LENGTH_SHORT).show();

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}