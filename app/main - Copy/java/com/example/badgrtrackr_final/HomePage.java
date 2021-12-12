package com.example.badgrtrackr_final;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.badgrtrackr_final.api.LocationListAPI;
import com.example.badgrtrackr_final.data_types.Location;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends Fragment {
    ExpandableListView expListView; // expandable list view
    LocationListAdapter expListAdapter; // expandable list adapter (put data into expandable list), using custom LocationListAdapter class
    SearchView searchView; // the search bar object
    LocationListAPI locAPI; // location list API to access location data


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InputStream is = getResources().openRawResource(R.raw.location_data); // create a new input stream for the location_data csv
        InputStream allLocHisIs = getResources().openRawResource(R.raw.location_history); // create a new input stream for the location_history csv

        locAPI = new LocationListAPI(is, allLocHisIs); // create a new location API with the required data

        expListView = view.findViewById(R.id.expandable_list); // access the expandable view xml object
        expListAdapter = new LocationListAdapter(view.getContext(), locAPI, locAPI.getLocationList()); // set the adapter to a new instance of the location adapter
        expListView.setAdapter(expListAdapter); // set the expandable list with the adapter

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expListAdapter.getChild(i, i1).toString();
                return true;
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expListAdapter.getChild(i, i1).toString();
                return true;
            }
        });

        // get the search view object in xml
        searchView = view.findViewById(R.id.search_bar);

        // override the search features
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Location> temp = expListAdapter.filterData(query); // search locations for submitted string
                expListAdapter = new LocationListAdapter(view.getContext(), locAPI, temp); // new adapter instance with only locations found by search
                expListView.setAdapter(expListAdapter); // set the view to the new adapter
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Location> temp = expListAdapter.filterData(newText); // search locations for submitted string
                expListAdapter = new LocationListAdapter(view.getContext(), locAPI, temp); // new adapter instance with only locations found by search
                expListView.setAdapter(expListAdapter); // set the view to the new adapter
                return false;
            }
        });
    }
}
