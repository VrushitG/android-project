package com.example.try4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class add_vehicle extends AppCompatActivity {
    ImageButton savebtn,backbtn;
    EditText editlicense,editcarname;
    DatabaseReference dbref,reference;
    String lisc,cname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        savebtn = findViewById(R.id.save);
        backbtn = findViewById(R.id.back);
        editlicense = findViewById(R.id.license);
        editcarname = findViewById(R.id.carname);

        reference = FirebaseDatabase.getInstance().getReference();

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(add_vehicle.this,home_page.class);
                startActivity(i);
            }
        });

    }

    public void validateData(){
        lisc = editlicense.getText().toString();
        cname = editcarname.getText().toString();

        if (lisc.isEmpty()) {
            editlicense.setError("Name is required!");
            editlicense.requestFocus();
        } else if (cname.isEmpty()) {
            editcarname.setError("Username is required!");
            editcarname.requestFocus();
        } else {
            uploadData();
        }
    }

    public void uploadData(){
        dbref = reference.child("users");
        String key = dbref.push().getKey();

        HashMap<String,String> user = new HashMap<>();
        user.put("key", key);
        user.put("license", key);
        user.put("carname", key);

        dbref.child(key).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(com.example.try4.add_vehicle.this, "Data Uploaded!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(com.example.try4.add_vehicle.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.example.try4.add_vehicle.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}