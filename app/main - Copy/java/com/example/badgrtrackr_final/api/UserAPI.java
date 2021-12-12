package com.example.badgrtrackr_final.api;

import com.example.badgrtrackr_final.data_types.User;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class UserAPI {
    private User user;

    public UserAPI(InputStream is) {
        loadUserData(is);
    }

    public void loadUserData(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String csvLine;
            String[] labels = reader.readLine().split(";");
            String[] userData = reader.readLine().split(";");
            user = new User(userData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }
}
