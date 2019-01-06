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

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Reminder> reminders;

    public HomeRecyclerViewAdapter(ArrayList<Reminder> reminders) {
        this.reminders = reminders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.layout_home_reminder_item,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.date.setText(reminders.get(i).getDueDateTimeDisplayString());
        viewHolder.name.setText(reminders.get(i).getNameDisplayString());
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView date;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.home_list_item_name);
            date = itemView.findViewById(R.id.home_list_item_date);
        }
    }

}
