package kz.coursereminder.structure;

import java.io.Serializable;
import java.text.DateFormatSymbols;

public class Task implements Serializable{

    private String date;
    private String course;
    private String name;
    private String time;
    private String specialNotes;
    private boolean isTest;

    public Task() {
        date = "";
        course = "";
        name = "";
        time = "";
    }

    public Task(String name, String date, String time, boolean isTest, String specialNotes) {
        this.date = date;
        this.name = name;
        this.time = time;
        this.isTest = isTest;
        this.specialNotes = specialNotes;
    }

    public String getDate() {
        return date;
    }

    public String getCourse() {
        return course;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public boolean getIsTest() {
        return isTest;
    }

    public String getTimeDisplayString() {
        String[] timeArray = time.split(":");
        if (timeArray[1].trim().equals("0")) {
            return timeArray[0] + ": 00";
        }
        return time;
    }

    public String getDateDisplayString() {
        String[] dateArray = date.split("/");
        String month = new DateFormatSymbols().getMonths()[Integer.valueOf(dateArray[1].trim()) - 1];
        return month + " " + dateArray[0] + ", " + dateArray[2];
    }

    public String getNameDisplayString() {
        if (isTest) {
            return "Test: " + name;
        }
        return "Assignment: " + name;
    }
}
