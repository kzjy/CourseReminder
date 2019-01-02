package kz.coursereminder.display;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import kz.coursereminder.R;
import kz.coursereminder.structure.BitmapConverter;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;
import kz.coursereminder.structure.NotificationReceiver;
import kz.coursereminder.structure.Reminder;

public abstract class ThemedActivity extends AppCompatActivity {

    protected SharedPreferences preferences;
    protected BitmapConverter bitmapConverter = new BitmapConverter();
    protected CourseManager courseManager;
    protected FileManager fileManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadTheme();
        super.onCreate(savedInstanceState);
        fileManager = new FileManager(this);
        courseManager = fileManager.getCourseManager();
        setUpAlarms();
    }


    @Override
    protected void onRestart() {
        loadTheme();
        FileManager fileManager = new FileManager(this);
        courseManager = fileManager.getCourseManager();
        setUpAlarms();
        super.onRestart();
    }

    /**
     * Get username stored in sharedpreferences
     *
     * @return username
     */
    public String getUserName() {
        return preferences.getString("User", "Anonymous");
    }

    protected void setUpAlarms() {
        courseManager.getReminderManager().removePastReminder();
        fileManager.writeFile(CourseManager.COURSES, courseManager);
        cancelAllAlarm();
        setAllReminderNotification();
    }

    /**
     * Get icon stored in sharedpreferences
     *
     * @return icon
     */
    public Drawable getIconDrawable() {
        String storedIcon = preferences.getString("Icon", "");
        if (storedIcon.equals("")) {
            return getDrawable(R.drawable.home_user_175);
        }
        Bitmap backgroundBitmap = bitmapConverter.decodeBase64(storedIcon);
        return new BitmapDrawable(getResources(), backgroundBitmap);
    }

    /**
     * Get the background saved in sharedPreferences
     *
     * @return a drawable of the background
     */
    public Drawable getBackgroundDrawable() {
        String storedBackground = preferences.getString("Background", "");
        if (storedBackground.equals("")) {
            return getDrawable(R.drawable.cloud);
        }
        Bitmap backgroundBitmap = bitmapConverter.decodeBase64(storedBackground);
        return new BitmapDrawable(getResources(), backgroundBitmap);
    }

    /**
     * Load the currently saved theme from preferences
     */
    private void loadTheme() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int theme = preferences.getInt("Theme", 0);
        selectTheme(theme);
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
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        // string to display for notification
        intent.putExtra("title", reminder.getNameDisplayString());
        intent.putExtra("message", "It is almost time! uwu");
        intent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id,
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
    public void cancelAlarm(int id) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    /**
     * Chooses the theme to apply
     *
     * @param theme theme stored in sharedPreferences
     */
    protected void selectTheme(int theme) {
        switch (theme) {
            case 0:
                setTheme(R.style.Theme0);
                break;
            case 1:
                setTheme(R.style.Theme1);
                break;
            case 2:
                setTheme(R.style.Theme2);
                break;
            case 3:
                setTheme(R.style.Theme3);
                break;
            case 4:
                setTheme(R.style.Theme4);
                break;
            case 5:
                setTheme(R.style.Theme5);
                break;
            case 6:
                setTheme(R.style.Theme6);
                break;

        }
    }

}
