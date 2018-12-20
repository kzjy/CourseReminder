package kz.coursereminder.classes;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import kz.coursereminder.R;

public class CourseCreationIconAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Integer> icons;
    private int highLight;

    public int getHighLight() {
        return highLight;
    }

    public CourseCreationIconAdapter(Context context, ArrayList<Integer> icons) {
        this.context = context;
        this.icons = icons;
    }
    public void setHighLight(int i) {
        this.highLight = i;
    }

    @Override
    public int getCount() {
        return icons.size();
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
        // some initalization stuff
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.layout_creation_icon, null);
        }

        // set icon background color depending on whether it is selected
        if (position == highLight) {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.DarkGray));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.actionbar));
        }

        // set icon image
        ImageView imageView = convertView.findViewById(R.id.create_course_grid_icon);
        imageView.setImageDrawable(ContextCompat.getDrawable(context, icons.get(position)));

        return convertView;
    }

}
