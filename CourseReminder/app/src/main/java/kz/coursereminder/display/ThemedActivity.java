package kz.coursereminder.display;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import kz.coursereminder.R;

public abstract class ThemedActivity extends AppCompatActivity {

    protected SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        loadTheme();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        loadTheme();
        super.onRestart();
    }

    private void loadTheme() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int theme = preferences.getInt("Theme", 0);
        selectTheme(theme);
    }

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
