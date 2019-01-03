package kz.coursereminder.controllers;

import android.content.Context;
import android.widget.Toast;


import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;
import kz.coursereminder.structure.NotificationAlarmManager;

public class Controller {

    protected Context context;
    protected FileManager fileManager;
    protected CourseManager courseManager;
    protected NotificationAlarmManager alarmManager;

    public Controller (Context context) {
        this.context = context;
        this.fileManager = new FileManager(context);
        this.courseManager = fileManager.getCourseManager();
        this.alarmManager = new NotificationAlarmManager(context);
    }

    public Context getContext() {
        return context;
    }

    public CourseManager getCourseManager() {
        return courseManager;
    }

    public NotificationAlarmManager getAlarmManager() {
        return alarmManager;
    }

    public void updateCourseManager() {
        fileManager.loadFile(CourseManager.COURSES);
        courseManager = fileManager.getCourseManager();
    }
}
