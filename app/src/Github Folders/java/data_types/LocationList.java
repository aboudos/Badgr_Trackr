package data_types;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationList {
    private List<Location> locations; // list of Location items
    private int totalVisits;



    public int getTotalVisits() {
        int count = 0;
        for (Location loc : locations) {
            count += loc.getTraffic();
        }
        return count;
    }

    // receives an inputstream from main activity, retrieves csv, creates the object
    public LocationList(InputStream is) {
        locations = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        try {
            String csvLine;
            while((csvLine = reader.readLine()) != null) {
                if (csvLine.contains("name")) {
                    continue;
                }
                String[] row = csvLine.split(";");
                locations.add(new Location(row));
            }
        } catch (IOException exception) {
            throw new RuntimeException("Error reading CSV file");
        } finally {
            try {
                is.close();
            } catch (IOException exception) {
                throw new RuntimeException("Error closing the input stream");
            }
        }

        this.totalVisits = getTotalVisits();
    }

    // returns the List object containing all locations
    public List<Location> getLocations() {
        return locations;
    }

    // returns a specified location
    public Location getLocation(String key) {
        for (Location loc : locations) {
            if (loc.getLocation().get("name").equals(key)) {
                return loc;
            }
        }
        return null;
    }

    // creates a list of group names/content to be added to the expandable list view
    public List<String> createGroupList() {
        List<String> groupList = new ArrayList<>();
        for (Location loc : locations) {
            String temp = loc.getLocation().get("name");
            groupList.add(temp);
        }
        return groupList;
    }

    // creates a collection out of the locations for use in teh locationlistadapter class/to creates expandable list view objects
    public Map<String, List<String>> getLocationCollection() {
        Map<String, List<String>> locationCollection = new HashMap<>();
        for (Location loc : locations) {
            Log.d("@@@2", loc.getLocation().get("name"));
            List<String> location = new ArrayList<>();
            for (Map.Entry<String, String> value : loc.getLocation().entrySet()) {
                location.add(value.getValue());
            }
            locationCollection.put(location.get(4), location);
        }
        return locationCollection;
    }
}
