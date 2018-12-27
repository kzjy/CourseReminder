package kz.coursereminder.structure;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.DateFormatSymbols;

public class Reminder implements Serializable, Comparable<Reminder> {

    private String name;
    private ReminderDateTime dateTime;
    private Grade grade;
    private boolean isTest;

    public Reminder(String name, Integer[] date, Integer[] time, boolean isTest) {
        this.name = name;
        this.dateTime = new ReminderDateTime(date, time);
        this.isTest = isTest;
    }

    public ReminderDateTime getDateTime() {
        return dateTime;
    }

    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }

    public boolean getIsTest() {
        return isTest;
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

    @Override
    public int compareTo(Reminder o) {
        return dateTime.compareTo(o.getDateTime());
    }
}
