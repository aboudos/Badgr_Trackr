package com.example.badgr_trackr;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageFragment extends Fragment {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> mobileCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    SearchView searchView;
    ExpandableListView listView;
    Map<String, List<String>> searchCollection;
    ExpandableListAdapter expandableListAdapter2;

    public HomePageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_page_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // expandable list of location items
        createGroupList();
        createCollection();
        expandableListView = view.findViewById((R.id.list_item));

        expandableListAdapter = new LocationListAdapter(view.getContext(), groupList, mobileCollection);
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


        // search bar
        searchView = view.findViewById(R.id.search_bar);

        /*
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    searchView.clearFocus();
                }
            }
        });
         */

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

                for (Map.Entry<String, List<String>> entry : mobileCollection.entrySet()) {
                    if (entry.getKey().contains(s)) {
                        searchCollection.put(entry.getKey(), entry.getValue());
                    }
                }

                expandableListAdapter = new LocationListAdapter(view.getContext(), groupList, searchCollection);
                expandableListView.setAdapter(expandableListAdapter);
            }
        });
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("Gordon's Dining Hall");
        groupList.add("Reta's Market");
        groupList.add("Ogg Gym");
        groupList.add("College Library");

    }

    private void createCollection() {
        String[] samsungModels = {"1 address", "0", "0.1"};
        String[] googleModels = {"2 address", "1", "0.2"};
        String[] redmiModels = {"3 address", "2", "0.3"};
        String[] vivoModels = {"4 address", "1", "0.4"};

        mobileCollection = new HashMap<String, List<String>>();
        for (String group: groupList) {
            if (group.equals("Gordon's Dining Hall")) {
                loadChild(samsungModels);
            } else if (group.equals("Reta's Market")) {
                loadChild(googleModels);
            } else if (group.equals("Ogg Gym")) {
                loadChild(redmiModels);
            }else if (group.equals("College Library")) {
                loadChild(vivoModels);
            }
            mobileCollection.put(group, childList);
        }
    }

    private void loadChild(String[] mobileModels) {
        childList = new ArrayList<>();
        for (String model : mobileModels) {
            childList.add(model);
        }
    }
}