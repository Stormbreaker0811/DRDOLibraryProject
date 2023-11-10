package com.example.drdolibraryproject.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drdolibraryproject.R;
import com.example.drdolibraryproject.gettersetter.Library;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    private Context context;
    private List<Library> libraryList;

    public BooksAdapter(Context context, List<Library> libraryList) {
        this.context = context;
        this.libraryList = libraryList;
    }

    @NonNull
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.books_details,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.ViewHolder holder, int position) {
        Library library = libraryList.get(position);
        holder.bookName.setText(library.getBookName());
        holder.authorName.setText(library.getAuthorName());
        holder.category.setText(library.getCategory());
        holder.publishYear.setText(library.getPublishYear());
        holder.authorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore authorDB = FirebaseFirestore.getInstance();
                String authorName = holder.authorName.getText().toString();
                Query query = authorDB.collection("library").whereEqualTo("AuthorName",authorName);
                AggregateQuery countQuery = query.count();
                countQuery.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       AggregateQuerySnapshot countSnapshot = task.getResult();
                       long count = Objects.requireNonNull(countSnapshot).getCount();
                       Toast.makeText(context, "There are "+count+" books by "+authorName, Toast.LENGTH_SHORT).show();
                   }
                }).addOnFailureListener(e -> {

                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return libraryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookName,authorName,category,publishYear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.book_name);
            authorName = itemView.findViewById(R.id.author_name);
            category = itemView.findViewById(R.id.category_);
            publishYear = itemView.findViewById(R.id.publishing_year);
        }
    }
}
