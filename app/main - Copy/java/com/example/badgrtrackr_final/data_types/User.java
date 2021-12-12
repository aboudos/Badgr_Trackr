package com.example.badgrtrackr_final.data_types;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String first; // first name
    private String last; // last name
    private String fullName; // full name as "first last"
    private String email; // email
    private String favoriteLocationId; // id of their favorite location (name of location so "Shell Recreation Center" for example)
    private Map<String, Integer> locationHistory; // locationKey (name), numberVisits

    public User(String[] csvRow) {
        this.first = csvRow[0];
        this.last = csvRow[1];
        this.fullName = "" + this.first + " " + this.last;
        this.email = csvRow[2];
        this.locationHistory = formatLocationHistory(csvRow[3]);
        this.favoriteLocationId = getFavoriteLocation();
    }

    // converts the location history string into a map
    public Map<String, Integer> formatLocationHistory(String locationHistoryString) {
        String[] locationVisits = locationHistoryString.split("&");

        String[][] locations = new String[locationVisits.length][];
        for (int i = 0; i < locationVisits.length; i++) {
            locations[i] = locationVisits[i].split("=");
        }

        Map<String, Integer> locationMap = new HashMap<>();
        for (String[] loc : locations) {
            locationMap.put(loc[0], Integer.valueOf(loc[1]));
        }

        return locationMap;
    }

    // finds the user's favorite location from the list of all location history (most visits by user)
    public String getFavoriteLocation() {
        int greatestValue = 0;
        String key = "";
        for (Map.Entry<String, Integer> visits : locationHistory.entrySet()) {
            if (visits.getValue() >= greatestValue) {
                key = visits.getKey();
                greatestValue = visits.getValue();
            }
        }
        return key;
    }

    // returns the number of visits for a specified location
    public int getLocationHistory(String locationId) {
        for (Map.Entry<String, Integer> location : locationHistory.entrySet()) {
            if (location.getKey().equals(locationId)) {
                return location.getValue();
            }
        }
        return -1;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<String, Integer> getLocationHistory() {
        return locationHistory;
    }

    public String getFavoriteLocationId() {
        return favoriteLocationId;
    }
}