package kz.coursereminder.structure;

import android.graphics.Color;

import java.io.Serializable;

import kz.coursereminder.R;

public class Setting implements Serializable {

    public static final String SETTINGS = "settings.ser";

    private int themeColor = 0;

    public int getThemeColor() {
        return 0;
    }

    public void setThemeColor(int themeColor) {
        this.themeColor = themeColor;
    }

}
