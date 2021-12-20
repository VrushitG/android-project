package com.example.try4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class confirmParking extends AppCompatActivity {
    ImageButton iyes,ino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_parking);

        iyes = findViewById(R.id.yes);
        ino = findViewById(R.id.no);

        iyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(confirmParking.this, "Your Parking Slot is Booked! Thank You!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(confirmParking.this,home_page.class);
                startActivity(intent);
            }
        });

        ino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(confirmParking.this,home_page.class);
                startActivity(intent);
            }
        });


    }
}