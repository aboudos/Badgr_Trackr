package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import api.API;
import data_types.Location;
import data_types.LocationHistory;
import data_types.User;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    MapFragment mapFragment = new MapFragment();
    AccountFragment accountFragment = new AccountFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
    }

    public boolean handleSelectNavigation(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.home_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                return true;
            case R.id.map_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, mapFragment).commit();
                return true;
            case R.id.account_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, accountFragment).commit();
                return true;
        }
        return false;
    }
}