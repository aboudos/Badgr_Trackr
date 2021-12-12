package data_types;

import java.util.List;
import java.util.Map;

public class LocationHistory {
    Map<String, Integer> locationHistory;

    // row containing 'location name': (int) number of visits
    public LocationHistory(List<String[]> locations) {
        for (String[] location : locations) {
            locationHistory.put(location[0], Integer.valueOf(location[1]));
        }
    }

    // returns the location history map
    public Map<String, Integer> getLocationHistory() {
        return locationHistory;
    }

    // returns the number of visits at a specified location
    public Integer getLocationVisits(String key) {
        for (Map.Entry<String, Integer> location : locationHistory.entrySet()) {
            if (location.getKey().equals(key)) {
                return location.getValue();
            }
        }
        return -1;
    }
}
