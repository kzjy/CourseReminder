package kz.coursereminder.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.coursereminder.R;
import kz.coursereminder.structure.Reminder;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Reminder> reminders;

    public NotificationRecyclerViewAdapter(ArrayList<Reminder> reminders) {
        this.reminders = reminders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String dateTime = reminders.get(i).getDueDateTimeDisplayString();
        viewHolder.dueDate.setText(dateTime);
        viewHolder.assignment.setText(reminders.get(i).getNameDisplayString());
        selectIcon(reminders.get(i).getType(), viewHolder);
    }

    private void selectIcon(String type, ViewHolder viewHolder) {
        switch (type) {
            case "Assignment":
                viewHolder.image.setImageResource(R.drawable.medium_assignment_72);
                break;
            case "Meet up":
                viewHolder.image.setImageResource(R.drawable.ic_people_black_24dp);
                break;
            case "Study":
                viewHolder.image.setImageResource(R.drawable.ic_local_library_black_24dp);
                break;
            case "Test":
                viewHolder.image.setImageResource(R.drawable.ic_edit_black_72dp);
                break;
            default:
                viewHolder.image.setImageResource(R.drawable.medium_assignment_72);
        }
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        CircleImageView image;
        TextView assignment;
        TextView dueDate;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recycler_image);
            assignment = itemView.findViewById(R.id.recycler_assignment);
            dueDate = itemView.findViewById(R.id.recycler_due_date);
            linearLayout = itemView.findViewById(R.id.recycler_item);
        }
    }

}
