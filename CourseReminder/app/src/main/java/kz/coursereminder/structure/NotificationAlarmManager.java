package kz.coursereminder.structure;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class NotificationAlarmManager {

    private CourseManager courseManager;
    private Context context;

    public NotificationAlarmManager(Context context) {
        this.context = context;
        FileManager fileManager = new FileManager(context);
        courseManager = fileManager.getCourseManager();
    }

    private void updateCourseManager() {
        FileManager fileManager = new FileManager(context);
        courseManager = fileManager.getCourseManager();
    }
    /**
     * Set up the reminder notification alarms
     */
    public void setUpAlarms() {
        updateCourseManager();
        courseManager.cleanUpReminderManager();
        FileManager fileManager = new FileManager(context);
        fileManager.writeFile(CourseManager.COURSES, courseManager);
        cancelAllAlarm();
        setAllReminderNotification();
    }

    /**
     * Set alarm for all the upcoming reminders
     */
    private void setAllReminderNotification() {
        Reminder[] reminderArray = courseManager.getReminderManager().getActiveReminders();
        Log.v("ALL", courseManager.getReminderManager().toString());
        for (int i = 0; i < reminderArray.length; i++) {
            if (reminderArray[i] != null) {
                startAlarm(reminderArray[i], i);
                Log.v(reminderArray[i].getName(), "alarm set");
            }
        }
    }

    /**
     * set alarm for a specific reminder
     *
     * @param reminder reminder to set alarm for
     * @param id       position in reminderManager
     */
    private void startAlarm(Reminder reminder, int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        // string to display for notification
        intent.putExtra("title", reminder.getNameDisplayString());
        intent.putExtra("message", "It is almost time! uwu");
        intent.putExtra("id", id);
        // alarm manager
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                    reminder.getNotificationTime().getTimeInMillis(), pendingIntent);
        }
    }

    /**
     * Cancel all alarms active
     */
    private void cancelAllAlarm() {
        Reminder[] reminderArray = courseManager.getReminderManager().getActiveReminders();
        for (int i = 0; i < reminderArray.length; i++) {
            if (reminderArray[i] != null) {
                cancelAlarm(i);
            }
        }
    }

    /**
     * Cancel the alarm with id
     *
     * @param id id of the reminder
     */
    private void cancelAlarm(int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Log.v("cancelled", String.valueOf(id));
    }
}
