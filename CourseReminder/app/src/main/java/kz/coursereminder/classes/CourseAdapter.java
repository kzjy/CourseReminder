package kz.coursereminder.classes;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kz.coursereminder.R;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;

public class CourseAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Course> courses;

    public CourseAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public int getCount() {
        return courses.size();
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
        // 1
        Course course = courses.get(position);

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.layout_grid_item, null);
        }
        // set color
        convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.actionbar));
//        convertView.setBackground(ContextCompat.getDrawable(context, R.drawable.b0));
        if (course.getImage() == R.drawable.course_icoin_add) {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.DarkGray));
        }

        // 3
        ImageView courseImage = convertView.findViewById(R.id.grid_image);
        TextView courseText = convertView.findViewById(R.id.grid_text);

        // 4
        courseImage.setImageResource(course.getImage());
        courseText.setText(course.getName());

        return convertView;
    }

    private void setViewColor() {

    }

}
