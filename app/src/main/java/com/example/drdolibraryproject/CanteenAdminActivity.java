package com.example.drdolibraryproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drdolibraryproject.gettersetter.Canteens;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CanteenAdminActivity extends AppCompatActivity {
    private Button fileChoose;
    private TextView selectedFileName;
    private Button upload;
    private ProgressBar uploadProgress;
    private TextInputLayout file;
    private Date date;
    private SimpleDateFormat sdf;
    private EditText fileName;
    private StorageReference storageRef;
    private FirebaseFirestore canteenMenus;
    private StorageTask uploadTask;
    private TextView uploadText,uploaded;
    private Uri pdfURI;
    private static final int PICK_PDF_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_admin);
        initialiseUI();
        canteenMenus = FirebaseFirestore.getInstance();
        date = new Date();
        sdf = new SimpleDateFormat("MMMM", Locale.getDefault());
        storageRef = FirebaseStorage.getInstance().getReference("CanteenMenus");
        fileChoose.setOnClickListener(view ->{
            chooseFile();
        });
        upload.setOnClickListener(view -> {
            fileUpload();
        });
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            pdfURI = data.getData();
            File fileName = new File(pdfURI.toString());
            selectedFileName.setText(fileName.getName());
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void fileUpload(){
        if(pdfURI != null){
            fileName = file.getEditText();
            if(fileName == null){
                Toast.makeText(this, "Please Enter FileName.", Toast.LENGTH_SHORT).show();
            }else {
                    StorageReference fileRef = storageRef.child(fileName.getText().toString());
                    uploadTask = fileRef.putFile(pdfURI).addOnSuccessListener(taskSnapshot -> {
                        Handler handler = new Handler();
                        handler.postDelayed(() -> uploadProgress.setProgress(0),500);
                        Toast.makeText(CanteenAdminActivity.this, "Upload Successful.", Toast.LENGTH_SHORT).show();
                        uploadText.setText("Uploaded");
                        Map<String,String> menus = new HashMap<>();
                        menus.put("Name",fileName.getText().toString());
                        menus.put("URL",fileRef.getDownloadUrl().toString());
                        menus.put("Month",sdf.format(date));
                        Canteens canteens = new Canteens(fileRef.getDownloadUrl().toString(),fileName.getText().toString(), sdf.format(date));
                        canteenMenus.collection("canteen_menus").add(menus)
                                .addOnCompleteListener(task -> {
                                    if(task.isSuccessful()){
                                        Toast.makeText(CanteenAdminActivity.this, "File Data Uploaded to database.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(e -> {
                                    e.printStackTrace();
                                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }).addOnFailureListener(e -> {
                        e.printStackTrace();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }).addOnProgressListener(snapshot -> {
                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        int prog = (int) progress;
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                uploadProgress.setProgress(prog);
                            }
                        });
                    });
            }
        }
        else{
            Toast.makeText(this, "Please select a file..", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialiseUI() {
        fileChoose = findViewById(R.id.chooseFile);
        upload = findViewById(R.id.uploadBtn);
        file = findViewById(R.id.fileName);
        uploadProgress = findViewById(R.id.uploadedProgress);
        uploadText = findViewById(R.id.uploadText);
        selectedFileName = findViewById(R.id.selectedFileName);
    }
}