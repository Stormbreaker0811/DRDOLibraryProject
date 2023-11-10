package com.example.drdolibraryproject.adapters;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CanteenListDataPump {
    private static String monthNow;
    private static final List<String> menus = new ArrayList<>();
    public interface CanteenListCallback {
        void onDataReceived(HashMap<String, List<String>> details);
    }

    public static void getData(final CanteenListCallback callback) {
        final HashMap<String, List<String>> details = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.getDefault());
        FirebaseFirestore monthDB = FirebaseFirestore.getInstance();
        monthDB.collection("canteen_menus").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    monthNow = doc.getString("Month");
                    isEqualMonth();
                    details.put(monthNow, menus);
                    callback.onDataReceived(details);
                }
            }
        }).addOnFailureListener(Throwable::printStackTrace);
    }
    public static void isEqualMonth(){
        switch(monthNow){
            case "January":
                FirebaseFirestore.getInstance().collection("canteen_menus").whereEqualTo("Month","January").get()
                        .addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){
                                for(QueryDocumentSnapshot doc1 : task1.getResult()){
                                    menus.clear();
                                    menus.add(doc1.getString("Name"));
                                }
                            }
                        }).addOnFailureListener(Throwable::printStackTrace);
                break;
                case "February":
                    FirebaseFirestore.getInstance().collection("canteen_menus").whereEqualTo("Month","February").get()
                            .addOnCompleteListener(task1 -> {
                                if(task1.isSuccessful()){
                                    for(QueryDocumentSnapshot doc1 : task1.getResult()){
                                        menus.clear();
                                        menus.add(doc1.getString("Name"));
                                    }
                                }
                            }).addOnFailureListener(Throwable::printStackTrace);
                    break;
                    case "March":
                        FirebaseFirestore.getInstance().collection("canteen_menus").whereEqualTo("Month","March").get()
                                .addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        for(QueryDocumentSnapshot doc1 : task1.getResult()){
                                            menus.clear();
                                            menus.add(doc1.getString("Name"));
                                        }
                                    }
                                }).addOnFailureListener(Throwable::printStackTrace);
                        break; case "April": FirebaseFirestore.getInstance().collection("canteen_menus").whereEqualTo("Month","April").get()
                    .addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            for(QueryDocumentSnapshot doc1 : task1.getResult()){
                                menus.clear();
                                menus.add(doc1.getString("Name"));
                            }
                        }
                    }).addOnFailureListener(Throwable::printStackTrace);
                        break;
                        case "May":
                            FirebaseFirestore.getInstance().collection("canteen_menus").whereEqualTo("Month","May").get()
                    .addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            for(QueryDocumentSnapshot doc1 : task1.getResult()){
                                menus.clear();
                                menus.add(doc1.getString("Name"));
                            }
                        }
                    }).addOnFailureListener(Throwable::printStackTrace);
                        break;
                        case "June":
                            FirebaseFirestore.getInstance().collection("canteen_menus").whereEqualTo("Month","June").get()
                    .addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            for(QueryDocumentSnapshot doc1 : task1.getResult()){
                                menus.clear();
                                menus.add(doc1.getString("Name"));
                            }
                        }
                    }).addOnFailureListener(Throwable::printStackTrace);
                        break;
                        case "July":
                            FirebaseFirestore.getInstance().collection("canteen_menus").whereEqualTo("Month","July").get()
                    .addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            for(QueryDocumentSnapshot doc1 : task1.getResult()){
                                menus.clear();
                                menus.add(doc1.getString("Name"));
                            }
                        }
                    }).addOnFailureListener(Throwable::printStackTrace);
                        break;
                        case "August":
                            FirebaseFirestore.getInstance().collection("canteen_menus").whereEqualTo("Month","August").get()
                    .addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            for(QueryDocumentSnapshot doc1 : task1.getResult()){
                                menus.clear();
                                Log.d("Menus",menus.toString());
                                menus.add(doc1.getString("Name"));
                                Log.d("TAG", "isEqualMonth: "+menus);
                            }
                        }
                    }).addOnFailureListener(Throwable::printStackTrace);
                        break;
                        case "September":
                            FirebaseFirestore.getInstance().collection("canteen_menus").whereEqualTo("Month","September").get()
                    .addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            for(QueryDocumentSnapshot doc1 : task1.getResult()){
                                menus.clear();
                                menus.add(doc1.getString("Name"));
                            }
                        }
                    }).addOnFailureListener(Throwable::printStackTrace);
                        break;
                        case "October":
                            FirebaseFirestore.getInstance().collection("canteen_menus").whereEqualTo("Month","October").get()
                    .addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            for(QueryDocumentSnapshot doc1 : task1.getResult()){
                                menus.clear();
                                menus.add(doc1.getString("Name"));
                            }
                        }
                    }).addOnFailureListener(Throwable::printStackTrace);
                        break;
                        case "November":
                            FirebaseFirestore.getInstance().collection("canteen_menus").whereEqualTo("Month","November").get()
                                    .addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            for(QueryDocumentSnapshot doc1 : task1.getResult()){
                                menus.clear();
                                menus.add(doc1.getString("Name"));
                            }
                        }
                    }).addOnFailureListener(Throwable::printStackTrace);
                        break;
                        case "December":
                            FirebaseFirestore.getInstance().collection("canteen_menus").whereEqualTo("Month","December").get()
                    .addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            for(QueryDocumentSnapshot doc1 : task1.getResult()){
                                menus.clear();
                                menus.add(doc1.getString("Name"));
                            }
                        }
                    }).addOnFailureListener(Throwable::printStackTrace);
                        break;
                        default:
                            menus.add("No menus for this month..//");
                        break;
        }
    }
}
