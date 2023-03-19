package com.example.drdolibraryproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout login_details;
    private EditText login_pass;
    private Button login_btn;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialiseUI();
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_pass = login_details.getEditText();
                password = login_pass.getText().toString();
                login_process(password);
            }
        });
    }

    private void initialiseUI() {
        login_details = findViewById(R.id.login_details);
        login_btn = findViewById(R.id.login);
    }
    private void login_process(String password){
        FirebaseFirestore usersdb = FirebaseFirestore.getInstance();
        usersdb.collection("users_records").whereEqualTo("Password",password).
                get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(LoginActivity.this,LandingActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(e -> {

                });
    }
}