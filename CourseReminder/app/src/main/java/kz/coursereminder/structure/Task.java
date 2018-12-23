package kz.coursereminder.structure;

import java.io.Serializable;

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
}
