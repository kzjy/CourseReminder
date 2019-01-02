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
import android.widget.Toast;
import java.util.ArrayList;

import kz.coursereminder.R;
import kz.coursereminder.adapters.NotificationRecyclerViewAdapter;
import kz.coursereminder.controllers.NotificationController;
import kz.coursereminder.structure.Reminder;


public class NotificationsFragment extends Fragment {

    /**
     * Controller that controls what gets displayed
     */
    private NotificationController controller;
    /**
     * Adapter for upcoming recycler view
     */
    private NotificationRecyclerViewAdapter adapter;
    /**
     * Adapter for past recycler view
     */
    private NotificationRecyclerViewAdapter pastAdapter;
    /**
     * Arraylist of reminders for upcoming reminders
     */
    private ArrayList<Reminder> upcoming = new ArrayList<>();
    /**
     * Arraylist of past reminders
     */
    private ArrayList<Reminder> past = new ArrayList<>();
    /**
     * View of the fragment
     */
    private View view;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new NotificationController(this.getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        setUpUpcomingRecyclerView(view);
        setUpPastRecyclerView(view);
        reduceViews(view);
        setImageBackground();
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Set background to user stored background
     */
    private void setImageBackground() {
        try {
            Drawable backgroundDrawable = ((MainActivity) getActivity()).getBackgroundDrawable();
            ImageView background = view.findViewById(R.id.notification_background);
            background.setImageDrawable(backgroundDrawable);
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Background could not be loaded",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Set up recyclerview for upcoming notifications
     *
     * @param view view of the fragment
     */
    private void setUpUpcomingRecyclerView(View view) {
        upcoming.clear();
        upcoming.addAll(controller.getUpcoming());
        RecyclerView notificationRecycler = view.findViewById(R.id.notification_recycler_view);
        adapter = new NotificationRecyclerViewAdapter(upcoming);
        notificationRecycler.setAdapter(adapter);
        notificationRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationRecycler.setNestedScrollingEnabled(false);
    }

    /**
     * Set up recyclerview for past notifications
     *
     * @param view view of the fragment
     */
    private void setUpPastRecyclerView(View view) {
        past.clear();
        past.addAll(controller.getPast());
        RecyclerView pastRecycler = view.findViewById(R.id.notification_recycler_view_past);
        pastAdapter = new NotificationRecyclerViewAdapter(past);
        pastRecycler.setAdapter(pastAdapter);
        pastRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        pastRecycler.setNestedScrollingEnabled(false);
    }

    /**
     * Set unnecessary views to be gone
     *
     * @param view view of the fragment
     */
    private void reduceViews(View view) {
        boolean upcomingEmpty = upcoming.size() == 0;
        boolean pastEmpty = past.size() == 0;
        if (upcomingEmpty && pastEmpty) {
            view.findViewById(R.id.notification_nothing_currently).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.notification_nothing_currently).setVisibility(View.INVISIBLE);
        }
        togglePastVisibility(pastEmpty);
        toggleUpcomingVisibility(upcomingEmpty);
    }

    /**
     * Toggle past recycler visibility
     * @param pastEmpty whether past arraylist is empty
     */
    private void togglePastVisibility(boolean pastEmpty) {
        LinearLayout pastLayout = view.findViewById(R.id.notification_past_set);
        if (pastEmpty) {
            pastLayout.setVisibility(View.GONE);
        } else {
            pastLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Toggle upcoming recycler visibility
     * @param upcomingEmpty whether upcoming arraylist is empty
     */
    private void toggleUpcomingVisibility(boolean upcomingEmpty) {
        LinearLayout upcomingLayout = view.findViewById(R.id.notification_upcoming_set);
        if (upcomingEmpty) {
            upcomingLayout.setVisibility(View.GONE);
        } else {
            upcomingLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Reads file for new reminders and update recyclerview
     */
    public void refresh() {
        controller.update();
        updateReminderArrayList();
        adapter.notifyDataSetChanged();
        pastAdapter.notifyDataSetChanged();
        reduceViews(view);
    }

    /**
     * updates the past and upcoming arraylist
     */
    private void updateReminderArrayList() {
        upcoming.clear();
        upcoming.addAll(controller.getUpcoming());
        past.clear();
        past.addAll(controller.getPast());
    }
}
