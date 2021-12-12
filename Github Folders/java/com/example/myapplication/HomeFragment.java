package com.example.myapplication;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data_types.LocationList;

public class HomeFragment extends Fragment {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    SearchView searchView;
    List<String> groupList;
    Map<String, List<String>> searchCollection;
    Map<String, List<String>> locationCollection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InputStream lis = getResources().openRawResource(R.raw.location_data);
        LocationList locations = new LocationList(lis);
        groupList = locations.createGroupList();
        locationCollection = locations.getLocationCollection();

        expandableListView = view.findViewById(R.id.expandable_list);
        expandableListAdapter = new LocationListAdapter(view.getContext(), groupList, locationCollection, locations);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                return true;
            }
        });


        searchView = view.findViewById(R.id.search_bar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return false;
            }

            public void filterData(String s) {
                searchCollection = new HashMap<String, List<String>>();

                for (Map.Entry<String, List<String>> entry : locationCollection.entrySet()) {
                    if (entry.getKey().contains(s)) {
                        searchCollection.put(entry.getKey(), entry.getValue());
                    }
                }

                expandableListAdapter = new LocationListAdapter(view.getContext(), groupList, searchCollection, locations);
                expandableListView.setAdapter(expandableListAdapter);
            }
        });
    }
}