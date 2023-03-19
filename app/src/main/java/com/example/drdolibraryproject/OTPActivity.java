package com.example.drdolibraryproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    private String mobile,name,password,dob;
    private FirebaseFirestore usersDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String verificationid,code;
    private TextInputLayout OTP;
    private EditText otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        Intent intent = getIntent();
        mobile = intent.getStringExtra("Mobile");
        name = intent.getStringExtra("Name");
        password = intent.getStringExtra("Password");
        dob = intent.getStringExtra("DOB");
        OTP = findViewById(R.id.otp_text);
        otp = OTP.getEditText();
        sendOTP();
        Button verify_btn = findViewById(R.id.verify_btn);
        verify_btn.setOnClickListener(view ->{
            verify(otp.getText().toString());
        });
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            setVerificationid(s);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            setCode(phoneAuthCredential.getSmsCode());
            if(getCode()!=null){
                otp.setText(getCode());
                verify(getCode());
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTPActivity.this, "Verification Failed due to: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verify(String code) {
        //OTP gets verified...
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(getVerificationid(),code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                //OTP is verified and the user gets registered...
                success();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void success() {
        //Entered Details are filled in the database...
        Map<String,Object> users = new HashMap<>();
        users.put("Name",name);
        users.put("Birthdate",dob);
        users.put("Mobile",mobile);
        users.put("Password",password);
        FirebaseFirestore user_db = FirebaseFirestore.getInstance();
        user_db.collection("users").document(name).
                set(users).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        AlertDialog.Builder registerAlert = new AlertDialog.Builder(OTPActivity.this);
                        registerAlert.setTitle("Registration Success");
                        registerAlert.setMessage("Congratulations You are registered! Do you want to login?");
                        registerAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(OTPActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                startActivity(new Intent(OTPActivity.this,MainActivity.class));
                            }
                        }).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Registration Failed due to: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void setVerificationid(String s) {
        this.verificationid = s;
    }

    private void sendOTP() {
        //OTP is sent to the mobile....`
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth).
                        setPhoneNumber(mobile).
                        setTimeout(60L, TimeUnit.SECONDS).
                        setActivity(this).setCallbacks(mCallBacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVerificationid() {
        return verificationid;
    }
}