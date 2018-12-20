package kz.coursereminder.structure;

import java.io.Serializable;

public class Assignment implements Serializable{

    private String date;
    private String course;
    private String name;
    private String time;
    private boolean isTest;

    public Assignment() {
        date = "";
        course = "";
        name = "";
        time = "";
    }

    public Assignment(String course, String name, String date, String time, boolean isTest) {
        this.date = date;
        this.course = course;
        this.name = name;
        this.time = time;
        this.isTest = isTest;
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
}
