package kz.coursereminder.controllers;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;
import kz.coursereminder.structure.Reminder;
import kz.coursereminder.structure.ReminderDateTime;

public class NotificationFragmentController {

    private Context context;
    private CourseManager courseManager;

    private ArrayList<Reminder> upcoming = new ArrayList<>();
    private ArrayList<Reminder> past = new ArrayList<>();

    public NotificationFragmentController(Context context) {
        this.context = context;
        loadManager();
        generatDisplayArrayLists();
    }

    public ArrayList<Reminder> getUpcoming() {
        return upcoming;
    }

    public ArrayList<Reminder> getPast() {
        return past;
    }

    private void loadManager() {
        FileManager fileManager = new FileManager(context);
        courseManager = fileManager.getCourseManager();
    }

    public void update() {
        loadManager();
    }

    /**
     * Get the current date and time in termins of ReminderDateTime
     * @return a ReminderDateTime that contains current time
     */
    private ReminderDateTime getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        Integer[] date = new Integer[] {calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)};
        Integer[] time = new Integer[] {calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE)};
        return new ReminderDateTime(date, time);
    }

    /**
     * Add reminders to past / upcoming arrayList
     */
    private void generatDisplayArrayLists() {
        ReminderDateTime current = getCurrentDateTime();
        for (int i = 0; i < courseManager.getCourses().size(); i++) {
            Course c = courseManager.getCourses().get(i);
            for (int j = 0; j < c.getReminders().size(); j++) {
                Reminder r = c.getReminders().get(j);
                if (current.compareTo(r.getDateTime()) < 0) {
                    upcoming.add(r);
                } else {
                    past.add(r);
                }
            }
        }
        Collections.sort(upcoming);
        Collections.sort(past);
        Collections.reverse(upcoming);
        Collections.reverse(past);
    }
}
