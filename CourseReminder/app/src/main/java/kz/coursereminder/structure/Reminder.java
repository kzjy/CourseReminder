package kz.coursereminder.structure;

import java.io.Serializable;
import java.text.DateFormatSymbols;

public class Reminder implements Serializable{

    private String date;
    private String name;
    private String time;
    private Grade grade;
    private boolean isTest;

    public Reminder() {
        date = "";
        name = "";
        time = "";
    }

    public Reminder(String name, String date, String time, boolean isTest) {
        this.date = date;
        this.name = name;
        this.time = time;
        this.isTest = isTest;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public Grade getGrade() {
        return grade;
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

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return name;
    }
}
