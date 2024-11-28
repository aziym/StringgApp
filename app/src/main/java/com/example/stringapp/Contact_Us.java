package com.example.stringapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class Contact_Us extends AppCompatActivity {

    private ImageView button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        button_back = findViewById(R.id.buttonM);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contact_Us.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        OrientationEventListener orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {

                int epsilion = 10;
                int leftlandscape = 90;
                int rightlandcape = 270;

                if (epsilionCheck(orientation, leftlandscape, epsilion) || epsilionCheck(orientation, rightlandcape, epsilion)) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }

            private boolean epsilionCheck(int orientation, int landscapeMode, int epsilion) {
                return orientation > landscapeMode - epsilion && orientation < landscapeMode + epsilion;
            }
        };

        orientationEventListener.enable();


        // Visit Page Function
        //face = findViewById(R.id.facebook1);
        //mail = findViewById(R.id.gmail1);
        //tweet = findViewById(R.id.twitter1);

        //face.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        getDir("https://www.facebook.com/umpsamalaysia");
        //    }
        //});

        //mail.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        getDir("https://www.umpsa.edu.my/en");
        //    }
        //});

        //tweet.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        getDir("https://twitter.com/umpsamalaysia");
        //    }
        //});
        //}

        //private void getDir(String s) {
        //    Uri uri = Uri.parse(s);
        //    startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}