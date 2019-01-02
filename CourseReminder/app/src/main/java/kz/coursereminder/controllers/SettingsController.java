package kz.coursereminder.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.List;

public class SettingsController {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SettingsController (Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public void setThemeColor(int color) {
        editor.putInt("Theme", color);
    }

    public void save() {
        editor.commit();
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

}
