package data_types;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Location {
    private String[] labels = {"name", "address", "city", "state", "zip", "longitude", "latitude"};
    private String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private String[] hours = {"8am", "9am", "10am", "11am", "12pm", "1pm", "2pm", "3pm", "4pm", "5pm", "6pm", "7pm", "8pm", "9pm", "10pm", "11pm", "12am"};

    private Map<String, String> location;
    private Map<String, Map<String, Integer>> weekData;
    private int trafficToday;


    // gets the csv row separated by ;, aligns with the labels array
    public Location(String[] locationData) {
        location = new HashMap<>();
        for (int i = 0; i < locationData.length; i++) {
            Log.d("@@@STR", locationData[i]);
            location.put(labels[i], locationData[i]);
        }
        this.weekData = createWeekData();
    }

    // creates random mock data for the location days of the hour
    public Map<String, Map<String, Integer>> createWeekData() {
        Random rand = new Random();
        Map<String, Map<String, Integer>> locationWeekData = new HashMap<>();
        for (String day : days) {
            Map<String, Integer> hoursDay = new HashMap<>();
            for (String hour : hours) {
                hoursDay.put(hour, rand.nextInt(20));
            }
            locationWeekData.put(day, hoursDay);
        }
        return locationWeekData;
    }

    // returns the location object as a Map<'data key', 'data value'>
    public Map<String, String> getLocation() {
        return location;
    }

    public Map<String, Map<String, Integer>> getWeekData() {
        return weekData;
    }

    public void incrementLocationDate() {
        /* Increment the day of the week based on the current day*/
    }

    public String getDayOfWeekName() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String today = new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());
        return today;
    }

    public int sumDayData(Map<String, Integer> day) {
        int sum = 0;
        for (Map.Entry<String, Integer> hour : day.entrySet()) {
            sum = sum + hour.getValue();
        }
        return sum;
    }

    public void getLocationTraffic() {
        String today = getDayOfWeekName();
        int sum = sumDayData(weekData.get(today));
        trafficToday = sum;
    }

    public int getTraffic() {
        return trafficToday;
    }
}
