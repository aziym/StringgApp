package com.example.stringapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ImageView profilePic, coverPic;
    TextView nameTv, emailTv, phoneTv;

    FloatingActionButton updateProfile;

    ProgressDialog pd;

    AlertDialog dialog, dialog1;
    ActivityResultLauncher<Intent> startActivityResultLauncher;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_REQUEST_CODE = 400;

    String cameraPermissions[];
    String storagePermissions[];

    FloatingActionButton balik_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        balik_button = findViewById(R.id.button1);

        balik_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        profilePic = findViewById(R.id.profilePic);
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);
        emailTv = findViewById(R.id.emailTv);
        updateProfile = findViewById(R.id.updateProfile);
        coverPic = findViewById(R.id.coverPic);
        //pd = new ProgressDialog(getApplication());

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for (DataSnapshot ds : datasnapshot.getChildren()){
                    String  name = "" + ds.child("name").getValue();
                    String  email = "" + ds.child("email").getValue();
                    String  phone = "" + ds.child("phone").getValue();
                    String  image = "" + ds.child("image").getValue();
                    String  cover = "" + ds.child("cover").getValue();

                    nameTv.setText(name);
                    emailTv.setText(email);
                    phoneTv.setText(phone);
                    try{
                        Picasso.get().load(image).into(profilePic);
                    }
                    catch (Exception e){
                        //Picasso.get().load(R.drawable.baseline_add_a_photo_24).into(profilePic);
                    }

                    try{
                        Picasso.get().load(cover).into(coverPic);
                    }
                    catch (Exception e){

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Data");
        View view = getLayoutInflater().inflate(R.layout.fragment_update,  null);
        builder.setView(view);
        dialog = builder.create();

        Button profileUpload = view.findViewById(R.id.profileUpload);
        Button coverUpload = view.findViewById(R.id.coverUpload);
        Button saveButton = view.findViewById(R.id.saveButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);
        EditText nameUpdate = view.findViewById(R.id.nameUpdate);
        EditText phoneUpdate = view.findViewById(R.id.phoneUpdate);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Select Method");
        View view1 = getLayoutInflater().inflate(R.layout.fragment_media,  null);
        builder1.setView(view1);
        dialog1 = builder1.create();
        Button useCamera = view1.findViewById(R.id.camera);
        Button useStorage = view1.findViewById(R.id.storage);

        profileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.show();
                useCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*TakePicture();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivity(intent);
                        //if(intent.resolveActivity(getPackageManager()) != null){
                            //startActivityResultLauncher.launch(intent);
                       // }*/
                    }
                });

            }
        });

        coverUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameUpdate.getText().toString().trim();
                if(!TextUtils.isEmpty(name)) {
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("name", name);

                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplication(), "Update...", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplication(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }

                String phone = phoneUpdate.getText().toString().trim();
                if(!TextUtils.isEmpty(phone)){
                    HashMap<String, Object> result1 = new HashMap<>();
                    result1.put("phone", phone);

                    databaseReference.child(user.getUid()).updateChildren(result1)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    //Toast.makeText(getApplication(), "Updated...", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Toast.makeText(getApplication(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }



                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        /*startActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == RESULT_OK &&  o.getData() != null ) {
                            Bundle bundle = o.getData().getExtras();
                            Bitmap imageBitmap = (Bitmap) bundle.get ("data");
                            profilePic.setImageBitmap(imageBitmap);

                        }
                    }

                }
        );*/

    }

    private void  TakePicture(){

    }
}