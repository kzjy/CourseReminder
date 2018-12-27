package kz.coursereminder.structure;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.DateFormatSymbols;

public class Reminder implements Serializable, Comparable<Reminder> {

    private String date;
    private String name;
    private String time;
    private Grade grade;
    private boolean isTest;

    public Reminder(String name, String date, String time, boolean isTest) {
        this.date = date;
        this.name = name;
        this.time = time;
        this.isTest = isTest;
    }

    public Integer[] getDate() {
        String[] dateStringArray = date.trim().split("/");
        return new Integer[] {Integer.valueOf(dateStringArray[0].trim()),
            Integer.valueOf(dateStringArray[1].trim()), Integer.valueOf(dateStringArray[2].trim())};
    }

    public String getName() {
        return name;
    }

    public Integer[] getTime() {
        String[] timeStringArray = time.trim().split(":");
        return new Integer[] {Integer.valueOf(timeStringArray[0].trim()),
                Integer.valueOf(timeStringArray[1].trim())};
    }

    public String getTimeString() {
        return this.time;
    }

    public String getDateString() {
        return this.date;
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

    @Override
    public int compareTo(@NonNull Reminder o) {
        Integer[] otherTime = o.getTime();
        Integer[] otherDate = o.getDate();
        int compareDate = compareDate(otherDate);
        if (compareDate == 0) {
            return compareTime(otherTime);
        }
        return compareDate;

    }

    /**
     * Compare date with otherDate, this.date closer is -1, same day is 0, further is 1
     * @param otherDate other date in integer array
     * @return an int
     */
    private int compareDate(Integer[] otherDate) {
        Integer[] thisDate = getDate();
        if (thisDate[2] > otherDate[2]) {
            return 1;
        } else if (thisDate[2] < otherDate[2]) {
            return -1;
        } else if (thisDate[1] > otherDate[1]) {
            return 1;
        } else if (thisDate[1] < otherDate[1]) {
            return -1;
        } else if (thisDate[0] > otherDate[0]) {
            return 1;
        }
        return -1;
    }

    /**
     * Compare time with otherTime, this.time closer is -1, same time is 0, later is 1
     * @param otherTime other time in integer array
     * @return an int
     */
    private int compareTime(Integer[] otherTime) {
        Integer[] thisTime = getTime();
        if (thisTime[0] >= otherTime[0]) {
            return 1;
        } else if (thisTime[1] > otherTime[1]) {
            return 1;
        }
        return 0;
    }
}
