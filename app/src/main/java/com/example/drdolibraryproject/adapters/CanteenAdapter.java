package com.example.drdolibraryproject.adapters;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.drdolibraryproject.R;
import com.example.drdolibraryproject.canteenstorage.CanteenRetrieve;
import com.example.drdolibraryproject.gettersetter.Canteens;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CanteenAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final List<String> canteenMenus;
    private final HashMap<String , List<String>> expandableListDetail;

    public CanteenAdapter(Context context,List<String> title,HashMap<String,List<String>> details){
        this.context = context;
        this.canteenMenus = title;
        this.expandableListDetail = details;
    }

    @Override
    public int getGroupCount() {
        return this.canteenMenus.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return Objects.requireNonNull(this.expandableListDetail.get(this.canteenMenus.get(i))).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.canteenMenus.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return Objects.requireNonNull(this.expandableListDetail.get(this.canteenMenus.get(i))).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String title1 = canteenMenus.get(i);
        View convertView = view;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.parent_canteen_list,viewGroup,false);
        }
        TextView title = convertView.findViewById(R.id.canteen_list_title);
        title.setText(title1);
        Toast.makeText(context, "Group is initialized..//", Toast.LENGTH_SHORT).show();
        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View convertView = view;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_canteen_list,viewGroup,false);
        }
        Button download = convertView.findViewById(R.id.download);
        TextView fileName = convertView.findViewById(R.id.download_text);
        fileName.setText((CharSequence) expandableListDetail.get(canteenMenus.get(i)));
        download.setOnClickListener(view1 -> {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("CanteenMenus");
            StorageReference ref = storageRef.child((CharSequence) expandableListDetail.get(canteenMenus.get(i))+".pdf");
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    for(String s : Objects.requireNonNull(expandableListDetail.get(canteenMenus.get(i)))) {
                        CanteenRetrieve canteenRetrieve = new CanteenRetrieve(context, s, ".pdf", DIRECTORY_DOWNLOADS, uri.toString());
                        canteenRetrieve.downloadFile();
                        Toast.makeText(context, "Downloading File.. " + s + ".pdf", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
//            String pdfURI = child.getUri();
        });
        Toast.makeText(context, "Child is initialized..//", Toast.LENGTH_SHORT).show();
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
