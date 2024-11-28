package com.example.stringapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Feedback extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText Feedback;
    Button button;
    ImageView buttonB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        OrientationEventListener orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {

                int epsilion = 10;
                int leftlandscape = 90;
                int rightlandcape = 270;

                if(epsilionCheck(orientation, leftlandscape, epsilion) || epsilionCheck(orientation, rightlandcape, epsilion)){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }
            private boolean epsilionCheck(int orientation, int landscapeMode, int epsilion){
                return orientation > landscapeMode - epsilion && orientation < landscapeMode + epsilion;
            }
        };

        orientationEventListener.enable();

        buttonB = findViewById(R.id.buttonS);
        buttonB = findViewById(R.id.buttonS);
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivityS();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Feedback");

        Feedback = findViewById(R.id.Feedback1);
        button = findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inpFeedback = Feedback.getText().toString().trim();
                HashMap<Object, String> result = new HashMap<>();
                result.put("feedback", inpFeedback);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Feedback");
                reference.setValue(result);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void openactivityS(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}