package kz.coursereminder.display;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import kz.coursereminder.R;
import kz.coursereminder.adapters.NotificationRecyclerViewAdapter;
import kz.coursereminder.controllers.NotificationFragmentController;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;
import kz.coursereminder.structure.Reminder;
import kz.coursereminder.structure.ReminderDateTime;


public class Notifications extends Fragment {

    private NotificationFragmentController controller;
    private NotificationRecyclerViewAdapter adapter;
    private NotificationRecyclerViewAdapter pastAdapter;

    public Notifications() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new NotificationFragmentController(this.getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        setUpUpcomingRecyclerView(view);
        setUpPastRecyclerView(view);
        reduceViews(view);
        Drawable backgroundDrawable = ((MainActivity) getActivity()).getBackgroundDrawable();
        ImageView background = view.findViewById(R.id.notification_background);
        background.setImageDrawable(backgroundDrawable);
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Set up recyclerview for upcoming notifications
     * @param view view of the fragment
     */
    private void setUpUpcomingRecyclerView(View view) {
        RecyclerView notificationRecycler = view.findViewById(R.id.notification_recycler_view);
        adapter = new NotificationRecyclerViewAdapter(controller.getUpcoming());
        notificationRecycler.setAdapter(adapter);
        notificationRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationRecycler.setNestedScrollingEnabled(false);
    }

    /**
     * Set up recyclerview for past notifications
     * @param view view of the fragment
     *
     */
    private void setUpPastRecyclerView(View view) {
        RecyclerView pastRecycler = view.findViewById(R.id.notification_recycler_view_past);
        pastAdapter = new NotificationRecyclerViewAdapter(controller.getPast());
        pastRecycler.setAdapter(pastAdapter);
        pastRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        pastRecycler.setNestedScrollingEnabled(false);
    }

    /**
     * Set unnecessary views to be gone
     * @param view view of the fragment
     */
    private void reduceViews(View view){
        LinearLayout upcoming = view.findViewById(R.id.notification_upcoming_set);
        LinearLayout past = view.findViewById(R.id.notification_past_set);
        boolean upcomingEmpty = controller.getUpcoming().size() == 0;
        boolean pastEmpty = controller.getPast().size() == 0;
        if (upcomingEmpty && pastEmpty) {
            view.findViewById(R.id.notification_nothing_currently).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.notification_nothing_currently).setVisibility(View.INVISIBLE);
        }
        if (upcomingEmpty) {
            upcoming.setVisibility(View.GONE);
        }
        if (pastEmpty) {
            past.setVisibility(View.GONE);
        }
    }

    /**
     * Reads file for new reminders and update recyclerview
     */
    public void refresh() {
        controller.update();
        adapter.notifyDataSetChanged();
        pastAdapter.notifyDataSetChanged();
    }
}
