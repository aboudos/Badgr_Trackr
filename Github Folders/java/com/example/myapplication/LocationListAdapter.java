package com.example.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.List;
import java.util.Map;

import data_types.Location;
import data_types.LocationList;

public class LocationListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupList;
    private LocationList locationList;
    private Map<String, List<String>> locationCollection;

    public LocationListAdapter(Context context, List<String> groupList, Map<String, List<String>> locations, LocationList locationList) {
        this.context = context;
        this.groupList = groupList;
        this.locationCollection = locations;
        for (Map.Entry<String, List<String>> loc : locationCollection.entrySet()) {
            Log.d("@@@10", loc.getKey());
        }
        this.locationList = locationList;
    }
    @Override
    public int getGroupCount() {
        return locationCollection.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return locationCollection.get(groupList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return locationCollection.get(groupList.get(i)).get(i1);
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
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String groupName = getGroup(i).toString();
        // use locationCollection to get distance data, traffic, and
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.home_group, null);
        }

        TextView item = view.findViewById(R.id.groupTextName);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(groupName);

        // custom functions
        Location loc = locationList.getLocation(groupName);
        int traffic = loc.getTraffic();
        Log.d("@@@", "" + groupName + "-" + traffic);
        return view;
    }

    // need to edit this, want to include address and then charts in the child
    // create custom xml layouts for charts and for the address
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String model = getChild(i, i1).toString();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.home_child, null);
        }
        TextView item = view.findViewById(R.id.childTextView);
        item.setText(model);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
