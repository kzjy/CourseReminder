package kz.coursereminder.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kz.coursereminder.R;
import kz.coursereminder.structure.Reminder;

public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Reminder> reminder;

    public CalendarRecyclerViewAdapter(ArrayList<Reminder> reminderArrayList) {
        this.reminder = reminderArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.layout_home_reminder_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Reminder currentReminder = reminder.get(i);
        viewHolder.date.setText(currentReminder.getDueDateTimeDisplayString());
        viewHolder.name.setText(currentReminder.getNameDisplayString());
    }

    @Override
    public int getItemCount() {
        return reminder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.home_list_item_name);
            date = itemView.findViewById(R.id.home_list_item_date);
        }
    }
}
