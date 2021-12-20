package com.example.try4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

public class your_vehichle extends AppCompatActivity {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mGetReference;
    TextView txtcar,txtlisc;
    ImageButton igetData,icancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_vehichle);

        mDatabase = FirebaseDatabase.getInstance();
        mGetReference = mDatabase.getReference("Data");

        txtcar = findViewById(R.id.car);

        igetData = findViewById(R.id.getdata);
        icancel = findViewById(R.id.back);

        icancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(your_vehichle.this,home_page.class);
                startActivity(intent);
            }
        });

        getData();
    }
    private void getData(){
        mGetReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                txtcar.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(your_vehichle.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}