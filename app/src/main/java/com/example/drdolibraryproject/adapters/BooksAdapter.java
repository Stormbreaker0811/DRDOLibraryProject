package com.example.drdolibraryproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.example.drdolibraryproject.R;
import com.example.drdolibraryproject.gettersetter.Library;

import java.util.HashMap;
import java.util.List;

public class BooksAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> title;
    private HashMap<String,List<String>> details;
    public BooksAdapter(Context context,List<String> title,HashMap<String,List<String>> details){
        this.context = context;
        this.title = title;
        this.details = details;
    }
    @Override
    public int getGroupCount() {
        return title.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return details.get(title.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return title.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return details.get(title.get(i)).get(i1);
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
        View convertView = view;
        Library library = (Library) getGroup(i);
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.book_parent_list,null);
        }
        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
