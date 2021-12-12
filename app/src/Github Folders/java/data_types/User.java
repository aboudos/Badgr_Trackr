package data_types;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class User {
    private String first;
    private String last;
    private String email;

    public User(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        try {
            String csvLine;
            while((csvLine = reader.readLine()) != null) {
                if (csvLine.contains("name")) {
                    continue;
                }
                String[] row = csvLine.split(";");
                this.first = row[0];
                this.last = row[1];
                this.email = row[2];
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

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getEmail() {
        return email;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
