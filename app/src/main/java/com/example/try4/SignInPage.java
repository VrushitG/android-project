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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignInPage extends AppCompatActivity {
    EditText editfullname,edituname,editmail,editpass;
    FirebaseAuth auth;
    DatabaseReference reference,dbref;
    String fullname,user,email,pass;


    ImageButton signups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        editfullname = findViewById(R.id.fullname);
        edituname  = findViewById(R.id.uname);
        editmail = findViewById(R.id.email);
        editpass = findViewById(R.id.pass);

        signups = findViewById(R.id.signups);
        signups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null);
    }

    private void validateData() {
        fullname = editfullname.getText().toString();
        user = edituname.getText().toString();
        email = editmail.getText().toString();
        pass = editpass.getText().toString();

        if (fullname.isEmpty()) {
            editfullname.setError("Name is required!");
            editfullname.requestFocus();
        } else if (user.isEmpty()) {
            edituname.setError("Username is required!");
            edituname.requestFocus();
        } else if (email.isEmpty()) {
            editmail.setError("Email is required!");
            editmail.requestFocus();
        } else if (pass.isEmpty()) {
            editpass.setError("Password is required!");
            editpass.requestFocus();
        } else {
            createUser();
        }
    }

    private void createUser() {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uploadData();
                        } else {
                            Toast.makeText(com.example.try4.SignInPage.this, "Error:" + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.example.try4.SignInPage.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData() {
        dbref = reference.child("users");
        String key = dbref.push().getKey();

        HashMap<String,String> user = new HashMap<>();
        user.put("key", key);
        user.put("name", key);
        user.put("email", key);

        dbref.child(key).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(com.example.try4.SignInPage.this, "User Created!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(com.example.try4.SignInPage.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.example.try4.SignInPage.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}