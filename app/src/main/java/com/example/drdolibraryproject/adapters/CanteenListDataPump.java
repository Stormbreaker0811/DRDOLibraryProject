package com.example.drdolibraryproject.adapters;

import android.util.Log;

import com.example.drdolibraryproject.gettersetter.Canteens;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CanteenListDataPump {
    public static HashMap<String , List<String>> getData(){
        List<String> january = new ArrayList<>();
        List<String> february = new ArrayList<>();
        List<String> march = new ArrayList<>();
        List<String> april = new ArrayList<>();
        List<String> may = new ArrayList<>();
        List<String> june = new ArrayList<>();
        List<String> july = new ArrayList<>();
        List<String> august = new ArrayList<>();
        List<String> september = new ArrayList<>();
        List<String> october = new ArrayList<>();
        List<String> november = new ArrayList<>();
        List<String> december = new ArrayList<>();
        HashMap<String , List<String>> details = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.getDefault());
        String monthNow = sdf.format(new Date());
        FirebaseFirestore monthDB = FirebaseFirestore.getInstance();
        switch (monthNow) {
            case "January":
                monthDB.collection("canteen_menus").whereEqualTo("Month", "January").
                        get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    january.add(doc.getString("Name"));
                                    details.put("January",january);
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                //
                break;
            case "February":
                monthDB.collection("canteen_menus").whereEqualTo("Month", "February").
                        get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    february.add(doc.getString("Name"));
                                    details.put("February",february);
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                break;
            case "March":
                monthDB.collection("canteen_menus").whereEqualTo("Month", "March").
                        get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    march.add(doc.getString("Name"));
                                    details.put("March",march);
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                break;
            case "April":
                monthDB.collection("canteen_menus").whereEqualTo("Month", "April").
                        get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    april.add(doc.getString("Name"));
                                    details.put("April",april);
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                break;
            case "May":
                monthDB.collection("canteen_menus").whereEqualTo("Month", "May").
                        get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    may.add(doc.getString("Name"));
                                    details.put("May",may);
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                break;
            case "June":
                monthDB.collection("canteen_menus").whereEqualTo("Month", "June").
                        get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    june.add(doc.getString("Name"));
                                    details.put("June",june);
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                break;
            case "July":
                monthDB.collection("canteen_menus").whereEqualTo("Month", "July").
                        get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    july.add(doc.getString("Name"));
                                    details.put("July",july);
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                break;
            case "August":
                monthDB.collection("canteen_menus").whereEqualTo("Month", "August").
                        get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    august.add(doc.getString("Name"));
                                    details.put("August",august);
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                break;
            case "September":
                monthDB.collection("canteen_menus").whereEqualTo("Month", "September").
                        get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    september.add(doc.getString("Name"));
                                    details.put("September",september);
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                break;
            case "October":
                monthDB.collection("canteen_menus").whereEqualTo("Month", "October").
                        get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    october.add(doc.getString("Name"));
                                    details.put("October",october);
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                break;
            case "November":
                monthDB.collection("canteen_menus").whereEqualTo("Month", "November").
                        get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    november.add(doc.getString("Name"));
                                    details.put("November",november);
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                break;
            case "December":
                monthDB.collection("canteen_menus").whereEqualTo("Month", "December").
                        get().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    december.add(doc.getString("Name"));
                                    details.put("December",december);
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                break;
        }
        return details;
    }

}
