package kz.coursereminder.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsController extends Controller {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SettingsController (Context context) {
        super(context);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public void setThemeColor(int color) {
        editor.putInt("Theme", color);
    }

    public void save() {
        editor.commit();
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

}
