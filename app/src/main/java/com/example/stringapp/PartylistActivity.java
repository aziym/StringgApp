package com.example.stringapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PartylistActivity extends AppCompatActivity {

    FloatingActionButton fab;

    FloatingActionButton return_button;
    RecyclerView recyclerView;
    List<DataClass> dataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partylist);

        return_button = findViewById(R.id.button1);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(PartylistActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(PartylistActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        MyAdapter adapter = new MyAdapter(PartylistActivity.this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials");
        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartylistActivity.this, AddpartyActivity.class);
                startActivity(intent);
            }
        });

        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartylistActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
}