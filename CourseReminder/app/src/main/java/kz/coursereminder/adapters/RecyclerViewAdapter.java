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

import de.hdodenhof.circleimageview.CircleImageView;
import kz.coursereminder.R;
import kz.coursereminder.structure.Assignment;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> images;
    private ArrayList<Assignment> assignments;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<String> images, ArrayList<Assignment> assignments) {
        this.images = images;
        this.assignments = assignments;
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
        viewHolder.course.setText(assignments.get(i).getCourse());
        String dateTime = assignments.get(i).getDate() + assignments.get(i).getTime();
        viewHolder.dueDate.setText(dateTime);
        viewHolder.assignment.setText(assignments.get(i).getName());
        if (assignments.get(i).getIsTest()) {
            viewHolder.image.setImageResource(R.drawable.ic_edit_black_72dp);
        } else {
            viewHolder.image.setImageResource(R.drawable.medium_assignment_72);
        }

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO redirect to another screen
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        CircleImageView image;
        TextView course;
        TextView assignment;
        TextView dueDate;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recycler_image);
            course = itemView.findViewById(R.id.recycler_course_name);
            assignment = itemView.findViewById(R.id.recycler_assignment);
            dueDate = itemView.findViewById(R.id.recycler_due_date);
            relativeLayout = itemView.findViewById(R.id.recycler_item);
        }
    }

}
