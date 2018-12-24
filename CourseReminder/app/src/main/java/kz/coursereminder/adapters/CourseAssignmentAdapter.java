package kz.coursereminder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.PipedOutputStream;
import java.text.DateFormatSymbols;
import java.util.ArrayList;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.FileManager;
import kz.coursereminder.structure.Task;

public class CourseAssignmentAdapter extends RecyclerView.Adapter<CourseAssignmentAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Task> taskList;

    public CourseAssignmentAdapter(Context context, CourseActivityController controller) {
        this.context = context;
        this.taskList = controller.getCurrentCourse().getTasks();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.layout_assignment_list_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Task currentTask = taskList.get(i);
        viewHolder.assignmentName.setText(currentTask.getNameDisplayString());
        String time = currentTask.getTimeDisplayString();
        String dateTime = currentTask.getDateDisplayString() + "   at   " + time;
        viewHolder.assignmentDate.setText(dateTime);
        setLayoutOnClickListener(viewHolder);
    }



    private void setLayoutOnClickListener(ViewHolder viewHolder) {
        viewHolder.foreground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "bleh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout foreground;
        TextView assignmentName;
        TextView assignmentDate;
        RelativeLayout background;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foreground = itemView.findViewById(R.id.assignment_list_item_foreground);
            assignmentDate = itemView.findViewById(R.id.assignment_list_date);
            assignmentName = itemView.findViewById(R.id.assignment_list_name);
            background = itemView.findViewById(R.id.assignment_list_item_background);
        }
    }

}
