package com.example.badgrtrackr_final.api;

import android.util.Log;

import com.example.badgrtrackr_final.data_types.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationListAPI {
    private List<Location> locationList; // list of locations, each list item is a Location object
    private Map<String, Map<String, String>> allLocHistory; // list of the location history for all locations Map<LocationName, Map<"mon", "1&2&3&4">>
    // the LocationListAPI constructor passes the allLocHistory.get(row[0]) to the Location object where row[0] is the name

    // constructor
    public LocationListAPI(InputStream is, InputStream allLocIs) {
        getAllLocHis(allLocIs); // get the location history values
        locationList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is)); // get all location data
        try {
            String csvLine;
            while((csvLine = reader.readLine()) != null) {
                if (csvLine.contains("name")) {
                    continue;
                }
                String[] row = csvLine.split(";");
                locationList.add(new Location(row, allLocHistory.get(row[0]))); // pass the csv split csv row and location history to Location constructor
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
    }

    public void getAllLocHis(InputStream is) {
        allLocHistory = new HashMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String csvLine;
            String[] labels = reader.readLine().split(";");
            while((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(";");
                Map<String, String> temp = formatLocHisRow(labels, row);
                allLocHistory.put(row[0], temp);
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
    }

    // formats the location history split csv row into a map
    public Map<String, String> formatLocHisRow(String[] labels, String[] row) {
        Map<String, String> temp = new HashMap<>();
        for (int i = 1; i < row.length; i++) {
            temp.put(labels[i], row[i]);
        }
        return temp;
    }

    // returns the list of all locations
    public List<Location> getLocationList() {
        return locationList;
    }

    // returns a specified location
    public Location getLocation(String locationName) {
        for (Location location : locationList) {
            if (location.getName().equals(locationName)) {
                return location;
            }
        }
        return null;
    }
}
