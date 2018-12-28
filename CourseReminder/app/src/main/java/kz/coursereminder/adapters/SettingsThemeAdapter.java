package kz.coursereminder.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.coursereminder.R;

public class SettingsThemeAdapter extends BaseAdapter {

    private Context context;
    private int[] color = new int[]{0, 1, 2, 3, 4, 5, 6};
    private int selectedPosition = 0;

    public SettingsThemeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return color.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.layout_setting_theme, null);
        }

        LinearLayout background = convertView.findViewById(R.id.settings_theme_color_backgournd);
        if (position == selectedPosition) {
            background.setBackgroundColor(ContextCompat.getColor(context, R.color.LightGrey));
        } else {
            background.setBackgroundColor(ContextCompat.getColor(context, R.color.White));
        }

        CircleImageView theme = convertView.findViewById(R.id.settings_theme_color);
        setThemeColor(theme, position);
        return convertView;
    }

    private void setThemeColor(View theme, int position) {
        switch (position) {
            case 1:
                theme.setBackgroundColor(ContextCompat.getColor(context, R.color.Theme1));
                break;
            case 2:
                theme.setBackgroundColor(ContextCompat.getColor(context, R.color.Theme2));
                break;
            case 3:
                theme.setBackgroundColor(ContextCompat.getColor(context, R.color.Theme3));
                break;
            case 4:
                theme.setBackgroundColor(ContextCompat.getColor(context, R.color.Theme4));
                break;
            case 5:
                theme.setBackgroundColor(ContextCompat.getColor(context, R.color.Theme5));
                break;
            case 6:
                theme.setBackgroundColor(ContextCompat.getColor(context, R.color.Theme6));
                break;
            default:
                theme.setBackgroundColor(ContextCompat.getColor(context, R.color.Theme1));
                break;
        }
    }

    public void setHighlight(int position) {
        selectedPosition = position;
    }
}
