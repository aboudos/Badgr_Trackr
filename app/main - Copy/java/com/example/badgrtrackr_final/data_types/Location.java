package com.example.badgrtrackr_final.data_types;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/*
    How to generate day data
    Get random priority 1-3, then do a random range in that frame

 */
public class Location {
    // hours of the day for setting the locHistoryWeek map
    private String[] hours = {"8am", "9am", "10am", "11am", "12pm", "1pm", "2pm", "3pm", "4pm", "5pm", "6pm", "7pm", "8pm", "9pm", "10pm", "11pm", "12am"};

    private String name; // name of the location
    private String address; // location address
    private Map<String, Float> coordinates; // contains: latitude=x, longitude=y, keys are latitude, longitude
    private int trafficIndicator; // 0 = low, 1 = med, 2 = high
    private int trafficCount; // the total number of visits this week (will potentially be used for calculating traffic for features to be added)
    private Map<String, Map<String, Integer>> locHistoryWeek; // history divided by day and week
    // Ex: Map<mon, Map<8am, 12>>

    // Constructor
    public Location(String[] csvRow, Map<String, String> weekData) {
        this.name = csvRow[0];
        this.address = csvRow[1] + ", " + csvRow[2] + ", " + csvRow[3] + " " + csvRow[4];
        this.coordinates = new HashMap<>();
        this.coordinates.put("longitude", Float.valueOf(csvRow[5]));
        this.coordinates.put("latitude", Float.valueOf(csvRow[6]));
        this.trafficIndicator = Integer.valueOf(csvRow[7]);
        formatLocationData(weekData); // takes the weekData Map and formats it into a Map<String, Map<String, Integer>>
        // weekData = <"mon", "1&2&3&4&5&6&7&8"> where & acts a divider between values for each hour of the day
    }

    // converst teh Map<String, String> into a Map<String, Map<String, Integer>>
    public void formatLocationData(Map<String, String> row) {
        locHistoryWeek = new HashMap<>();
        int total = 0;
        Map<String, Integer> values;
        for (Map.Entry<String, String> loc : row.entrySet()) {
            String[] tempValues = loc.getValue().split("&");
            values = new HashMap<>();
            for (int i = 0; i < tempValues.length; i++) {
                int val = Integer.valueOf(tempValues[i]);
                values.put(hours[i], val);
                total += val;
            }
            locHistoryWeek.put(loc.getKey(), values);
        }
        trafficCount = total;
    }

    // get name
    public String getName() {
        return name;
    }

    // get address
    public String getAddress() {
        return address;
    }

    // get total visits this week
    public int getTrafficCount() {
        return trafficCount;
    }

    // get the traffic indicator
    public int getTrafficIndicator() {
        return trafficIndicator;
    }

    // returns the lat/long coordinate map
    public Map<String, Float> getCoordinates() {
        return coordinates;
    }

    // returns the Map for location history (to be used for charts if possible)
    public Map<String, Map<String, Integer>> getLocHistoryWeek() {
        return locHistoryWeek;
    }

    // gets the current day as a 3 letter identifier (ie "sun")
    public String getDayOfWeekName() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String today = new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());
        Log.d("time", today);
        return today;
    }

    // returns the hour of the day as a string with am/pm (ie "8am")
    public String getHourOfDay() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String ampm = "";
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 12) {
            hour = hour - 12;
            ampm = "pm";
        } else {
            ampm = "am";
        }
        return "" + hour + ampm;
    }

    // function originally used to create and load mock data
    /*
    public void createLocationHistory() {
        Random rand = new Random();
        int min = 0;
        int max = 0;
        int indicator = rand.nextInt(3);
        switch(indicator) {
            case 0:
                min = 0;
                max = 20;
                break;
            case 1:
                min = 21;
                max = 40;
                break;
            case 2:
                min = 41;
                max = 60;
                break;
        }

        trafficIndicator = indicator;

        trafficCount = 0;
        locHistoryWeek = new HashMap<>();
        Log.d("DDD", getName());
        for (String day : days) {
            Map<String, Integer> hourValues = new HashMap<>();
            for (String hour : hours) {
                int value = rand.nextInt((max - min+1))+ min;
                trafficCount += value;
                hourValues.put(hour, value);
            }
            Log.d("DDD" + day, hourValues.toString());
            locHistoryWeek.put(day, hourValues);
        }
    }
    */
}
