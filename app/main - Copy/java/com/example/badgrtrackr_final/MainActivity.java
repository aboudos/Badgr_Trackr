package com.example.badgrtrackr_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.badgrtrackr_final.api.LocationListAPI;
import com.example.badgrtrackr_final.api.UserAPI;
import com.example.badgrtrackr_final.data_types.Location;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav; // create bottom nav
    HomePage home = new HomePage(); // create new home page fragment
    MapPage map = new MapPage(); // create new map fragment
    AccountPage account = new AccountPage(); // create new account fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNav); // set bottom nav object to the bottom nav xml object
        bottomNav.setSelectedItemId(R.id.home_option); // set the selected item to the home page

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); // sets the header to custom instead of default
        getSupportActionBar().setCustomView(R.layout.action_bar); // pass in the custom app header
    }

    // handles the nav page change selection
    // * this is where we need to reset the selected item, having trouble getting it working
    public boolean handleSelectNavigation(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.home_option:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, home).commit();
                return true;
            case R.id.map_option:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, map).commit();
                return true;
            case R.id.account_option:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, account).commit();
                return true;
        }
        return false;
    }
}