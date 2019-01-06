package kz.coursereminder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.display.CourseActivity;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.Reminder;

public class CourseAssignmentAdapter extends RecyclerView.Adapter<CourseAssignmentAdapter.ViewHolder> {

    private Context context;
    private Course course;
    private boolean isGrade;

    public CourseAssignmentAdapter(Context context, CourseActivityController controller, boolean isGrade) {
        this.context = context;
        this.course = controller.getCurrentCourse();
        this.isGrade = isGrade;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.layout_assignment_list_item,
                viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Reminder currentReminder;
        final int position = i;
        if (isGrade) {
            currentReminder = course.getCompletedReminders().get(i);
            setUpAssignmentName(viewHolder, currentReminder);
            setUpGrade(viewHolder, currentReminder);
            viewHolder.foreground.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((CourseActivity) context).onGradeLongClicked(position);
                    return true;
                }
            });
        } else {
            currentReminder = course.getReminders().get(i);
            setUpAssignmentName(viewHolder, currentReminder);
        }
    }

    /**
     * Set up the view holder for reminder display
     * @param viewHolder viewholder of the reminder
     * @param currentReminder current reminder to display
     */
    private void setUpAssignmentName(@NonNull ViewHolder viewHolder, Reminder currentReminder) {
        viewHolder.assignmentGrade.setText("");
        viewHolder.assignmentWeight.setText("");
        viewHolder.assignmentName.setText(currentReminder.getNameDisplayString());
        String dateTime = currentReminder.getDueDateTimeDisplayString();
        viewHolder.assignmentDate.setText(dateTime);
    }

    /**
     * Set up the viewholder for grade display
     * @param viewHolder viewholder of the grade
     * @param currentReminder current reminder to display
     */
    private void setUpGrade(@NonNull ViewHolder viewHolder, Reminder currentReminder) {
        String point = currentReminder.getGrade().getGrade()
                + " / " + currentReminder.getGrade().getTotal();
        String weight = "Weight: " + currentReminder.getGrade().getWeight();
        viewHolder.assignmentWeight.setText(weight);
        viewHolder.assignmentGrade.setText(point);
    }

    /**
     * Return the item count in the view
     * @return the item count
     */
    @Override
    public int getItemCount() {
        if (isGrade) {
            return course.getCompletedReminders().size();
        }
        return course.getReminders().size();
    }

    /**
     * View holder class that holds each item
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout foreground;
        TextView assignmentName;
        TextView assignmentDate;
        TextView assignmentGrade;
        TextView assignmentWeight;
        RelativeLayout background;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            foreground = itemView.findViewById(R.id.assignment_list_item_foreground);
            assignmentDate = itemView.findViewById(R.id.assignment_list_date);
            assignmentName = itemView.findViewById(R.id.assignment_list_name);
            assignmentGrade = itemView.findViewById(R.id.assignment_grade_point);
            assignmentWeight = itemView.findViewById(R.id.assignment_grade_weight);
            background = itemView.findViewById(R.id.assignment_list_item_background);
        }
    }

}
