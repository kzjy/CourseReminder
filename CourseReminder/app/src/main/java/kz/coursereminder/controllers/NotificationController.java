package kz.coursereminder.controllers;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;
import kz.coursereminder.structure.Reminder;
import kz.coursereminder.structure.ReminderDateTime;

public class NotificationController {

    private Context context;
    private CourseManager courseManager;

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

    private void loadManager() {
        FileManager fileManager = new FileManager(context);
        courseManager = fileManager.getCourseManager();
    }

    public void update() {
        loadManager();
        generatDisplayArrayLists();
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
        upcoming = new ArrayList<>();
        past = new ArrayList<>();
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
        Collections.reverse(past);
    }

    /**
     * Calculate the ReminderDateTime equivalent one week from now
     * @return ReminderDateTime one week from now
     */
    private ReminderDateTime getDateOneWeek() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy", Locale.CANADA);
        cal.add(Calendar.DATE, 7);
        String[] dateArray = s.format(cal.getTime()).split("-");
        Integer[] dateInteger = new Integer[] {Integer.valueOf(dateArray[0]),
                Integer.valueOf(dateArray[1]) - 1, Integer.valueOf(dateArray[2])};
        Integer[] dummyTime = new Integer[] {0, 0};
        return new ReminderDateTime(dateInteger, dummyTime);
    }

    /**
     * Get list of reminders today
     * @return arraylist of reminders
     */
    public ArrayList<Reminder> getTodayReminder() {
        ArrayList<Reminder> todayReminder = new ArrayList<>();
        ReminderDateTime current = getCurrentDateTime();
        for (Reminder r : upcoming) {
            if (r.getDateTime().getSameDate(current)) {
                todayReminder.add(r);
            }
        }
        Collections.sort(todayReminder);
        return todayReminder;
    }

    /**
     * Get list of reminders less than a week from now
     * @return arraylist of reminders
     */
    public ArrayList<Reminder> getWeekReminder() {
        ArrayList<Reminder> weekReminder = new ArrayList<>();
        ReminderDateTime weekTime = getDateOneWeek();
        ReminderDateTime current = getCurrentDateTime();
        for (Reminder r: upcoming) {
            if (weekTime.compareTo(r.getDateTime()) >= 0 && !r.getDateTime().getSameDate(current)) {
                weekReminder.add(r);
            }
        }
        Collections.sort(weekReminder);
        return weekReminder;
    }
}
