package com.example.drdolibraryproject.lists;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.drdolibraryproject.R;
import com.example.drdolibraryproject.adapters.BirthdayAdapter;
import com.example.drdolibraryproject.gettersetter.Birthdays;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BirthdayDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BirthdayDisplayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private ArrayList<Birthdays> birthdays_list;
    private String mParam2;
    private ListView birthday;
    private ProgressBar progressBar;
    private View view;

    public BirthdayDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BirthdayDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BirthdayDisplayFragment newInstance(String param1, String param2) {
        BirthdayDisplayFragment fragment = new BirthdayDisplayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        birthdays_list = new ArrayList<>();
        FirebaseFirestore birthdays = FirebaseFirestore.getInstance();
        birthdays.collection("users").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot doc : task.getResult()){
                    String birthday_string = doc.getString("Birthdate");
                    assert birthday_string != null;
                    happyBirthday(birthday_string);
                }
            }
        }).addOnFailureListener(Throwable::printStackTrace);
    }

    private void happyBirthday(String birthdays){
        String[] birth = birthdays.split("/");
        Date now = new Date();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM",Locale.getDefault());
        String day = dayFormat.format(now);
        String month = monthFormat.format(now);
        if(birth[0].equals(day) && birth[1].equals(month)){
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("Birthdate",birthdays).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            birthdays_list.add(new Birthdays(doc.getString("Name")));
                            BirthdayAdapter birthAdap = new BirthdayAdapter(getContext(),birthdays_list);
                            birthday.setAdapter(birthAdap);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }).addOnFailureListener(Throwable::printStackTrace);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_birthday_display, container, false);
        birthday = view.findViewById(R.id.birthdays_list);
        progressBar = view.findViewById(R.id.progress_circle);
        // Inflate the layout for this fragment
        return view;
    }
}