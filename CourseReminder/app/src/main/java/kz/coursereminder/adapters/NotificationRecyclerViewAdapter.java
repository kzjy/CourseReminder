package kz.coursereminder.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.coursereminder.R;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.Reminder;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private Context context;
    private ArrayList<Reminder> reminders;

    public NotificationRecyclerViewAdapter(Context context, ArrayList<Reminder> reminders) {
        this.reminders = reminders;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String dateTime = reminders.get(i).getDateTime().getDateDisplayString() + " at " +
                reminders.get(i).getDateTime().getTimeDisplayString();
        viewHolder.dueDate.setText(dateTime);
        viewHolder.assignment.setText(reminders.get(i).getName());
        if (reminders.get(i).getIsTest()) {
            viewHolder.image.setImageResource(R.drawable.ic_edit_black_72dp);
        } else {
            viewHolder.image.setImageResource(R.drawable.medium_assignment_72);
        }

    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        CircleImageView image;
        TextView assignment;
        TextView dueDate;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recycler_image);
            assignment = itemView.findViewById(R.id.recycler_assignment);
            dueDate = itemView.findViewById(R.id.recycler_due_date);
            relativeLayout = itemView.findViewById(R.id.recycler_item);
        }
    }

}
