package com.example.drdolibraryproject.bottomnavigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.drdolibraryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowseBooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowseBooksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    private TextInputLayout search;
    private EditText search_string;
    private RadioGroup search_categories;
    private Button searchBtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BrowseBooksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrowseBooksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrowseBooksFragment newInstance(String param1, String param2) {
        BrowseBooksFragment fragment = new BrowseBooksFragment();
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
        view = getLayoutInflater().inflate(R.layout.fragment_browse_books,null);
        searchBtn = view.findViewById(R.id.search_btn);
        search = view.findViewById(R.id.search_books);
        search_categories = view.findViewById(R.id.search_categories);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "In button click..", Toast.LENGTH_SHORT).show();
                //search_categories.setOnCheckedChangeListener((radioGroup, i) -> {
                //    i = radioGroup.getCheckedRadioButtonId();
                //    switch(i){
                //        case R.id.book_name:
                //            Toast.makeText(getContext(), "Calling Book Name", Toast.LENGTH_SHORT).show();
                //            searchByBookName();
                //            break;
                //        case R.id.author_name:
                //            searchByAuthorName();
                //            break;
                //        case R.id.category:
                //            searchByCategory();
                //            break;
                //        case R.id.publish_year:
                //            searchByPublishYear();
                //            break;
                //    }
                //});
                search_categories.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        int id = search_categories.getCheckedRadioButtonId();
                        Toast.makeText(getContext(), id+"", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void searchByBookName() {
        Toast.makeText(getContext(), "Searching By BookName", Toast.LENGTH_SHORT).show();
        search_string = search.getEditText();
        FirebaseFirestore booksdb = FirebaseFirestore.getInstance();
        booksdb.collection("library_records").whereEqualTo("BookName",search_string.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Toast.makeText(getContext(), search_string.getText().toString()+" Book Found.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                search.setError("No books found..//");
            }
        });
    }

    private void searchByCategory() {
        search_string = search.getEditText();
        FirebaseFirestore category = FirebaseFirestore.getInstance();
        category.collection("library_records").whereEqualTo("Category",search_string.getText().toString())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        search.setError("No books found..//");
                    }
                });
    }

    private void searchByAuthorName() {
        search_string = search.getEditText();
        FirebaseFirestore author = FirebaseFirestore.getInstance();
        author.collection("library_records").whereEqualTo("AuthorName",search_string.getText().toString())
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){

                    }
                }).addOnFailureListener(e -> {
                    search.setError("No books found..//");
                });

    }

    private void searchByPublishYear() {
        search_string = search.getEditText();
        FirebaseFirestore publish_year = FirebaseFirestore.getInstance();
        publish_year.collection("library_records")
                .whereEqualTo("PublishYear",search_string.getText().toString()).get().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){

                    }
                }).addOnFailureListener(e -> {

                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_browse_books, container, false);
    }
}