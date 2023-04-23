package com.example.drdolibraryproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.drdolibraryproject.bottomnavigation.BirthdaysFragment;
import com.example.drdolibraryproject.bottomnavigation.BrowseBooksFragment;
import com.example.drdolibraryproject.bottomnavigation.CanteenBrowseFragment;
import com.example.drdolibraryproject.lists.BirthdayDisplayFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class LandingActivity extends AppCompatActivity {
    private BottomNavigationView navigate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        initialiseUI();
        navigate.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch(id){
                case R.id.browse_books:
                    navigate.getMenu().findItem(R.id.browse_books).setChecked(true);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,new BrowseBooksFragment())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                    break;
                case R.id.browse_birthdays:
                    navigate.getMenu().findItem(R.id.browse_birthdays).setChecked(true);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,new BirthdayDisplayFragment())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                    break;
                case R.id.browse_canteen:
                    navigate.getMenu().findItem(R.id.browse_canteen).setChecked(true);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,new CanteenBrowseFragment())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                    break;
            }

            return false;
        });
    }

    private void initialiseUI() {
        navigate = findViewById(R.id.navigate);
    }
}