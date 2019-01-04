package kz.coursereminder.controllers;

import android.content.Context;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;
import kz.coursereminder.structure.NotificationAlarmManager;

public class Controller {

    /**
     * Context of the controller
     */
    protected Context context;
    /**
     * Filemanager
     */
    FileManager fileManager;
    /**
     * Coursemanager
     */
    CourseManager courseManager;
    /**
     * NotificationAlarmManager
     */
    NotificationAlarmManager alarmManager;

    /**
     * Constructor
     * @param context context
     */
    public Controller (Context context) {
        this.context = context;
        this.fileManager = new FileManager(context);
        this.courseManager = fileManager.getCourseManager();
        this.alarmManager = new NotificationAlarmManager(context);
    }

    /**
     * Getter for context
     * @return context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Getter for alarm manager
     * @return alarm manager
     */
    public NotificationAlarmManager getAlarmManager() {
        return alarmManager;
    }

    /**
     * Read from coursemanager file and update course manager
     */
    public void updateCourseManager() {
        fileManager.loadFile(CourseManager.COURSES);
        courseManager = fileManager.getCourseManager();
    }
}
