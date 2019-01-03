package kz.coursereminder.controllers;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;
import kz.coursereminder.structure.Reminder;

public class NotificationController {

    private static final String TAG = "NotificationController";
    private Context context;
    private CourseManager courseManager;

    private ArrayList<Reminder> all = new ArrayList<>();
    private ArrayList<Reminder> upcoming = new ArrayList<>();
    private ArrayList<Reminder> past = new ArrayList<>();

    public NotificationController(Context context) {
        this.context = context;
        update();
    }

    public ArrayList<Reminder> getUpcoming() {
        return upcoming;
    }

    public ArrayList<Reminder> getPast() {
        return past;
    }

    /**
     * Read coursemanager from file
     */
    private void loadManager() {
        FileManager fileManager = new FileManager(context);
        courseManager = fileManager.getCourseManager();
    }

    /**
     * Update the controller and check for changes in reminders
     */
    public void update() {
        loadManager();
        generatDisplayArrayLists();
        Log.v(TAG, String.valueOf(all.size()));
    }

    private void getReminderList() {
        all = new ArrayList<>();
        for (int i = 0; i < courseManager.getCourses().size(); i++) {
            Course c = courseManager.getCourses().get(i);
            all.addAll(c.getReminders());
        }
    }

    /**
     * Add reminders to past / upcoming arrayList
     */
    private void generatDisplayArrayLists() {
        Calendar calendar = Calendar.getInstance();
        getReminderList();
        upcoming = new ArrayList<>();
        past = new ArrayList<>();
        for (Reminder r : all) {
            if (calendar.compareTo(r.getDateTime()) < 0) {
                Log.v(TAG, r.getName());
                upcoming.add(r);
            } else {
                past.add(r);
            }
        }
        Collections.sort(upcoming);
        Collections.sort(past);
        Collections.reverse(past);
    }


    /**
     * Get list of reminders today
     *
     * @return arraylist of reminders
     */
    public ArrayList<Reminder> getTodayReminder() {
        ArrayList<Reminder> todayReminder = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        for (Reminder r : all) {
            if (r.getSameDate(c)) {
                todayReminder.add(r);
            }
        }
        Collections.sort(todayReminder);
        return todayReminder;
    }

    public ArrayList<Reminder> getDateReminder(Calendar calendar) {
        ArrayList<Reminder> todayReminder = new ArrayList<>();
        for (Reminder r : all) {
            if (r.getSameDate(calendar)) {
                todayReminder.add(r);
            }
        }
        Collections.sort(todayReminder);
        return todayReminder;
    }

    /**
     * Get list of reminders less than a week from now
     *
     * @return arraylist of reminders
     */
    public ArrayList<Reminder> getWeekReminder() {
        ArrayList<Reminder> weekReminder = new ArrayList<>();
        Calendar current = Calendar.getInstance();
        Calendar week = (Calendar) current.clone();
        week.add(Calendar.DATE, 7);
        Log.v(TAG, String.valueOf(upcoming));
        for (Reminder r : upcoming) {
            if (week.compareTo(r.getDateTime()) >= 0 && !r.getSameDate(current)) {
                weekReminder.add(r);
                Log.v(TAG, "added");
            }
            Log.v(TAG, "checked");
        }
        Collections.sort(weekReminder);
        Log.v(TAG, String.valueOf(weekReminder));
        return weekReminder;
    }
}
