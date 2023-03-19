package com.example.drdolibraryproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.drdolibraryproject.R;
import com.example.drdolibraryproject.gettersetter.Birthdays;

import java.util.ArrayList;

public class BirthdayAdapter extends ArrayAdapter {
    public BirthdayAdapter(@NonNull Context context, ArrayList<Birthdays> birthdays) {
        super(context, 0, birthdays);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.birthday_list,parent,false);
        }
        Birthdays birthdays = (Birthdays) getItem(position);
        TextView happyBirthday = view.findViewById(R.id.happy_birthday);
        happyBirthday.setText(birthdays.getName());
        return view;
    }
}
