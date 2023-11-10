package com.example.drdolibraryproject.bottomnavigation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.drdolibraryproject.R;
import com.example.drdolibraryproject.adapters.BooksAdapter;
import com.example.drdolibraryproject.databinding.FragmentBrowseBooksBinding;
import com.example.drdolibraryproject.gettersetter.Library;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowseBooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowseBooksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private View customView;
    private FragmentBrowseBooksBinding binding;
    private static final String ARG_PARAM2 = "param2";
    private TextInputLayout search;
    private EditText search_string;
    private RadioGroup search_categories;
    private Button searchBtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Library> libraryList;
    private AlertDialog.Builder booksAlert;
    private Library lib;
    private RecyclerView booksRecycler;
    private Context context;

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
        binding = FragmentBrowseBooksBinding.inflate(getLayoutInflater());
        searchBtn = binding.searchButton;
        search_categories = binding.searchCategories;
        search = binding.searchBooks;
        libraryList = new ArrayList<>();
        booksAlert = new AlertDialog.Builder(context);
        customView = getLayoutInflater().inflate(R.layout.books_alert,null);
        booksAlert.setTitle("Book Details");
        booksAlert.setView(customView);
        booksRecycler = customView.findViewById(R.id.recyclerBooks);
        booksRecycler.setHasFixedSize(true);
        booksRecycler.setLayoutManager(new LinearLayoutManager(context));
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Search Called..//", Toast.LENGTH_SHORT).show();
                search.addOnEditTextAttachedListener(textInputLayout -> {
                    search_string = textInputLayout.getEditText();
                });
                RadioButton selectedRadio = getSelectedRadioButton();
                if(selectedRadio != null) {
                    switch (selectedRadio.getId()) {
                        case R.id.book_name:
                            searchByBookName();
                            break;
                        case R.id.author_name:
                            searchByAuthorName();
                            break;
                        case R.id.category:
                            searchByCategory();
                            break;
                        case R.id.publish_year:
                            searchByPublishYear();
                            break;
                        default:
                            Toast.makeText(context, "Please select one of the buttons to search..//", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                final AlertDialog books = booksAlert.create();
                if(customView.getParent() != null){
                    ((ViewGroup)customView.getParent()).removeView(customView);
                    booksAlert.setView(customView);
                }
                books.show();
            }
        });

    }

    private void searchByBookName() {
        FirebaseFirestore booksdb = FirebaseFirestore.getInstance();
        booksdb.collection("library_records").whereEqualTo("BookName",search_string.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        String bookName = doc.getString("BookName");
                        String authorName = doc.getString("AuthorName");
                        String category = doc.getString("Category");
                        String publishYear = doc.getString("PublishYear");
                        lib = new Library(bookName,authorName,category,publishYear);
                        libraryList.add(lib);
                    }
                    booksRecycler.setAdapter(new BooksAdapter(context,libraryList));
                    booksAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            libraryList.clear();
                        }
                    });

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
        Toast.makeText(context, "Category Called..//", Toast.LENGTH_SHORT).show();
        FirebaseFirestore category = FirebaseFirestore.getInstance();
        category.collection("library_records").whereEqualTo("Category",search_string.getText().toString())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                String bookName = doc.getString("BookName");
                                String authorName = doc.getString("AuthorName");
                                String category = doc.getString("Category");
                                String publishYear = doc.getString("PublishYear");
                                lib = new Library(bookName,authorName,category,publishYear);
                                libraryList.add(lib);
                            }
                            booksRecycler.setAdapter(new BooksAdapter(context,libraryList));
                            booksAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    libraryList.clear();
                                }
                            });
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
        Toast.makeText(context, "Author Name Called..//", Toast.LENGTH_SHORT).show();
        FirebaseFirestore author = FirebaseFirestore.getInstance();
        author.collection("library_records").whereEqualTo("AuthorName",search_string.getText().toString())
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            String bookName = doc.getString("BookName");
                            String authorName = doc.getString("AuthorName");
                            String category = doc.getString("Category");
                            String publishYear = doc.getString("PublishYear");
                            lib = new Library(bookName,authorName,category,publishYear);
                            libraryList.add(lib);
                        }
                        booksRecycler.setAdapter(new BooksAdapter(context,libraryList));
                        booksAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                libraryList.clear();
                            }
                        });
                    }
                }).addOnFailureListener(e -> {
                    search.setError("No books found..//");
                });

    }

    private void searchByPublishYear() {
        Toast.makeText(context, "Publish Year Called..//", Toast.LENGTH_SHORT).show();
        FirebaseFirestore publish_year = FirebaseFirestore.getInstance();
        publish_year.collection("library_records")
                .whereEqualTo("PublishYear",search_string.getText().toString()).get().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            String bookName = doc.getString("BookName");
                            String authorName = doc.getString("AuthorName");
                            String category = doc.getString("Category");
                            String publishYear = doc.getString("PublishYear");
                            lib = new Library(bookName,authorName,category,publishYear);
                            libraryList.add(lib);
                        }
                        booksRecycler.setAdapter(new BooksAdapter(context,libraryList));
                        booksAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                libraryList.clear();
                            }
                        });
                    }
                }).addOnFailureListener(e -> {

                });
    }

    private RadioButton getSelectedRadioButton(){
        int selectedId = search_categories.getCheckedRadioButtonId();
        return binding.getRoot().findViewById(selectedId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}