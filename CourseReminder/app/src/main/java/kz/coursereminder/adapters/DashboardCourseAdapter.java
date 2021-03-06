package kz.coursereminder.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
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

public class DashboardCourseAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Course> courses;

    public DashboardCourseAdapter(Context context, CourseManager courseManager) {
        this.context = context;
        this.courses = courseManager.getCourses();
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
        setBackgroundColor(convertView);
        if (course.getImage() == R.drawable.course_icoin_add) {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.DarkGray));
        }

        // 3
        ImageView courseImage = convertView.findViewById(R.id.grid_image);
        TextView courseText = convertView.findViewById(R.id.grid_text);
        courseText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                context.getResources().getDimension(R.dimen.textsize));

        // 4
        courseImage.setImageResource(course.getImage());
        courseText.setText(course.getName());

        return convertView;
    }

    private void setBackgroundColor(View view) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        int color = typedValue.data;
        view.setBackgroundColor(color);
    }

}
