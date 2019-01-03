package kz.coursereminder.display;

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
import kz.coursereminder.controllers.Controller;
import kz.coursereminder.structure.BitmapConverter;


public abstract class ThemedActivity extends AppCompatActivity {

    protected SharedPreferences preferences;
    protected BitmapConverter bitmapConverter = new BitmapConverter();
    private Controller baseController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadTheme();
        super.onCreate(savedInstanceState);
        baseController = new Controller(this);
    }
//
//    @Override
//    protected void onRestart() {
//        loadTheme();
//        baseController.updateCourseManager();
//        baseController.getAlarmManager().setUpAlarms();
//        super.onRestart();
//    }

    @Override
    protected void onStop() {
        baseController.updateCourseManager();
        baseController.getAlarmManager().setUpAlarms();
        Log.v("stopped", "beep");
        super.onStop();
    }

    /**
     * Get username stored in sharedpreferences
     *
     * @return username
     */
    public String getUserName() {
        return preferences.getString("User", "Anonymous");
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
