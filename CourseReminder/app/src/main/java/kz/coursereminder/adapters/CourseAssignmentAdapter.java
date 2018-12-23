package kz.coursereminder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import kz.coursereminder.R;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.Task;

public class CourseAssignmentAdapter extends BaseAdapter {

    private Context context;
    private Course course;

    public CourseAssignmentAdapter(Context context, Course course) {
        this.context = context;
        this.course = course;
    }

    @Override
    public int getCount() {
        return course.getTasks().size();
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

        Task currrentAssignment = course.getTasks().get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.layout_assignment_list_item, null);
        }

        TextView assignmentName = convertView.findViewById(R.id.assignment_list_name);
        TextView assignmentDate = convertView.findViewById(R.id.assignment_list_date);

        assignmentName.setText(currrentAssignment.getName());
        String time = currrentAssignment.getDate() + " at " + currrentAssignment.getTime();
        assignmentDate.setText(time);

        return convertView;
    }
}
