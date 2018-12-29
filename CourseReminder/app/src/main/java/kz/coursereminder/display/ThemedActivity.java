package kz.coursereminder.display;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;

import kz.coursereminder.R;
import kz.coursereminder.structure.BitmapConverter;

public abstract class ThemedActivity extends AppCompatActivity {

    protected SharedPreferences preferences;
    protected String bg = "";
    protected BitmapConverter bitmapConverter = new BitmapConverter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadTheme();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        loadTheme();
        super.onRestart();
    }

//    /**
//     * Get default background if none is stored in sharedPreferences
//     * @return the encoded string of background
//     */
//    protected String getDefaultBackgroundString() {
////        Drawable vectorDrawable = VectorDrawableCompat.create(getResources(), R.drawable.cloud,
////                this.getTheme());
//        Bitmap defaultBackground = BitmapFactory.decodeResource(getResources(),R.drawable.cloud);
//        return bitmapConverter.encodeBase64(defaultBackground);
//    }

    /**
     * Get the background saved in sharedPreferences
     * @return a drawable of the background
     */
    public Drawable getBackgroundDrawable() {
        String storedBackground = preferences.getString("Background", bg);
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
