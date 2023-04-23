package com.example.drdolibraryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout name,password,confirm,mobile;
    private EditText username,pass,conf,mob,dob;
    private Button register_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialiseUI();
        getEditText();
        dob.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    dob.setText(i2+"/"+(i1 + 1)+"/"+i);
                }
            },year,month,day);
            datePickerDialog.show();

        });
        register_btn.setOnClickListener(view -> {
            if(TextUtils.isEmpty(username.getText()) && TextUtils.isEmpty(pass.getText()) &&
                    TextUtils.isEmpty(mob.getText()) && TextUtils.isEmpty(conf.getText()) &&
                    TextUtils.isEmpty(dob.getText())){
                name.setError("Please Fill all the fields.");
                mobile.setError("Please Fill all the fields.");
                password.setError("Please Fill all the fields.");
                confirm.setError("Please Fill all the fields.");

            } else if (!(pass.getText().toString().equals(conf.getText().toString()))) {
                password.setError("Passwords don't match");
                password.setErrorEnabled(true);
                confirm.setError("Passwords don't match");
                confirm.setErrorEnabled(true);
            }
            else{
                Intent intent = new Intent(RegisterActivity.this,OTPActivity.class);
                intent.putExtra("Name",username.getText().toString());
                intent.putExtra("Password",pass.getText().toString());
                intent.putExtra("DOB",dob.getText().toString());
                intent.putExtra("Mobile",mob.getText().toString());
                startActivity(intent);
            }
        });
    }


    private void getEditText() {
        username = name.getEditText();
        pass = password.getEditText();
        conf = confirm.getEditText();
        mob = mobile.getEditText();
    }

    private void initialiseUI() {
        name = findViewById(R.id.username_register);
        password = findViewById(R.id.password_register);
        mobile = findViewById(R.id.mobile_register);
        confirm = findViewById(R.id.confirm_register);
        register_btn = findViewById(R.id.register);
        dob = findViewById(R.id.date_of_birth);
    }
}