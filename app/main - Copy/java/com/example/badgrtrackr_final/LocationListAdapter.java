package com.example.badgrtrackr_final;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.badgrtrackr_final.api.LocationListAPI;
import com.example.badgrtrackr_final.data_types.Location;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationListAdapter extends BaseExpandableListAdapter {
    private Context context; // context passed from fragment
    private List<Location> locations; // list of locations to be displayed, changed by the filter/searches
    private List<Location> locationsOriginal; // original list to reset search
    private LocationListAPI locAPI; // instance of the current location API if needed

    public LocationListAdapter(Context context, LocationListAPI locAPI, List<Location> locations) {
        this.context = context;
        this.locAPI = locAPI;
        this.locations = locations;
        this.locationsOriginal =  locAPI.getLocationList();
    }

    public List<Location> getLocations() {
        return locations;
    }

    @Override
    public int getGroupCount() {
        return locations.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 2; // 4 groups: address, number of people visiting, 2 charts
    }

    @Override
    public Object getGroup(int groupPosition) {
        return locations.get(groupPosition);
    }

    // returns the child data, either address or traffic count for now
    // this one might be wrong but seems to be working
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        switch(childPosition) {
            case 0:
                return locations.get(groupPosition).getAddress();
            case 1:
                return locations.get(groupPosition).getTrafficCount();
            default:
                return 0;
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    // sets the groups in the expandable list
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        Location group = (Location) getGroup(groupPosition); // get the Location object being created

        // if the view is empty, inflate the view into the screen
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exp_group, null);
        }

        TextView item = view.findViewById(R.id.groupTextName); // find the text box in the exp_group xml
        item.setTypeface(null, Typeface.BOLD);
        item.setText(group.getName()); // set the text box to the location name

        View trafficIndicator = view.findViewById(R.id.traffic_indicator); // get the traffic indicator for the current location
        switch(group.getTrafficIndicator()) { // return the correct indicator shape based on the location's indicator
            case 0:
                trafficIndicator.setBackground(ContextCompat.getDrawable(context, R.drawable.traffic_indicator_low));
                break;
            case 1:
                trafficIndicator.setBackground(ContextCompat.getDrawable(context, R.drawable.traffic_indicator_medium));
                break;
            case 2:
                trafficIndicator.setBackground(ContextCompat.getDrawable(context, R.drawable.traffic_indicator_high));
                break;
            default:
                break;
        }

        return view;
    }

    // creates the child view for the dropdown area of each group
    // for now this is just a placeholder "Data", we will use this to create dropdown data, ignore for now
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exp_child, null);
        }
        TextView item = view.findViewById(R.id.childTextView);
        item.setText("Data");
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    // filters the locations list for the search strings
    // * need to update this function to only search the names for the length of query
    // * if query == s, we would only want to return Shell Recreational Center
    // * would return of the location names containing an "s" right now
    public List<Location> filterData(String query) {
        query = query.toLowerCase(); // converts query to lowercase

        if (query.isEmpty() || query.equals("")) { // if the query has nothing in it or is empty
            locations = locationsOriginal; // reset the locations list
             return locations; // return all locations
        } else { // if the search text is not empty
            List<Location> newList = new ArrayList<>(); // temporary list
            for (Location location : locationsOriginal) { // iterate through all names in the location list
                if (location.getName().toLowerCase().contains(query)) { // add items containing the query
                    newList.add(location); // return the list
                }
            }
            return newList;
        }
    }
}