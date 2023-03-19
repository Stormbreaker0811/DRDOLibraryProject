package com.example.drdolibraryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drdolibraryproject.dialogs.DatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextInputLayout name,password,confirm,mobile,date_of_birth;
    private EditText username,pass,conf,mob,dob;
    private Button register_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialiseUI();
        getEditText();
        dob.setOnClickListener(view -> {
            DatePicker datePicker = new DatePicker();
            datePicker.show(getSupportFragmentManager(),"DATE_PICK");
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
        dob = date_of_birth.getEditText();
    }

    private void initialiseUI() {
        name = findViewById(R.id.username_register);
        password = findViewById(R.id.password_register);
        mobile = findViewById(R.id.mobile_register);
        confirm = findViewById(R.id.confirm_register);
        register_btn = findViewById(R.id.register);
        date_of_birth = findViewById(R.id.dob_register);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, i);
        mCalendar.set(Calendar.MONTH, i1);
        mCalendar.set(Calendar.DAY_OF_MONTH, i2);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(selectedDate);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}