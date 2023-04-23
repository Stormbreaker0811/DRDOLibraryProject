package com.example.drdolibraryproject.bottomnavigation;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.drdolibraryproject.R;
import com.example.drdolibraryproject.adapters.CanteenAdapter;
import com.example.drdolibraryproject.adapters.CanteenListDataPump;
import com.example.drdolibraryproject.databinding.FragmentCanteenBrowseBinding;
import com.google.firestore.v1.Precondition;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CanteenBrowseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CanteenBrowseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentCanteenBrowseBinding binding;
    private Context context;
    private ExpandableListView canteen_list;
    private List<String> title;
    private HashMap<String , List<String>> details;
    private ProgressBar canteenListProgress;
    public CanteenBrowseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CanteenBrowseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CanteenBrowseFragment newInstance(String param1, String param2) {
        CanteenBrowseFragment fragment = new CanteenBrowseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Layout has been inflated.....
        binding = FragmentCanteenBrowseBinding.inflate(inflater);
        View root = binding.getRoot();
        canteen_list = binding.canteenList;
        canteenListProgress = binding.canteenListProgress;
        details = CanteenListDataPump.getData();
        title = new ArrayList<>(details.keySet());
        CanteenAdapter canteenAdapter = new CanteenAdapter(getContext(),title,details);
        canteenListProgress.setVisibility(View.VISIBLE);
        canteen_list.setVisibility(View.VISIBLE);
        canteen_list.setAdapter(canteenAdapter);
        if(canteen_list.isEnabled()){
            Toast.makeText(getContext(), "Canteen list initialized", Toast.LENGTH_SHORT).show();
        }
//        Toast.makeText(getContext(), "Canteen List Initialized..//", Toast.LENGTH_SHORT).show();
        canteenListProgress.setVisibility(View.INVISIBLE);
        canteen_list.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                Toast.makeText(getContext(), title.get(i)+" has collapsed.", Toast.LENGTH_SHORT).show();
            }
        });
        canteen_list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                Toast.makeText(getContext(), title.get(i)+" has expanded.", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    public void onAttach(@androidx.annotation.NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}