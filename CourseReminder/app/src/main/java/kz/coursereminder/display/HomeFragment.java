package kz.coursereminder.display;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kz.coursereminder.R;
import kz.coursereminder.adapters.HomeRecyclerViewAdapter;
import kz.coursereminder.controllers.NotificationController;
import kz.coursereminder.structure.Reminder;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    /**
     * View of the fragment
     */
    private View view;
    /**
     * Controller of the fragment
     */
    private NotificationController controller;
    /**
     * Recyclerview adapter of today recycler view
     */
    private HomeRecyclerViewAdapter todayAdapter;
    /**
     * Recycler view adapter for upcoming recycler view
     */
    private HomeRecyclerViewAdapter upcomingAdapter;

    private ArrayList<Reminder> today = new ArrayList<>();
    private ArrayList<Reminder> upcoming = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new NotificationController(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        setSavedResources();
        // set theme accent
        setAccentColor();
        // set listview display
        setTodayListViewItem();
        setUpcomingListViewItem();
        return view;
    }

    /**
     * Set up the sharedpreferences resrouces
     */
    private void setSavedResources() {
        try {
            Drawable backgroundDrawable = ((MainActivity) getActivity()).getBackgroundDrawable();
            ((ImageView) view.findViewById(R.id.home_background)).setImageDrawable(backgroundDrawable);
            Drawable iconDrawable = ((MainActivity) getActivity()).getIconDrawable();
            ((ImageView) view.findViewById(R.id.home_icon)).setImageDrawable(iconDrawable);
            String user = "Welcome, " + ((MainActivity) getActivity()).getUserName();
            ((TextView) view.findViewById(R.id.home_user)).setText(user);
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Oopsie! Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * set color accent above profile
     */
    private void setAccentColor() {
        try {
            TypedValue typedValue = new TypedValue();
            getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;
            view.findViewById(R.id.home_themed_accent).setBackgroundColor(color);
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Oopsie! Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Set listivew for today's reminder
     */
    private void setTodayListViewItem() {
        today.addAll(controller.getTodayReminder());
        todayAdapter = new HomeRecyclerViewAdapter(today);
        RecyclerView todayRecycler = view.findViewById(R.id.home_today_listview);
        todayRecycler.setAdapter(todayAdapter);
        todayRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        todayRecycler.setNestedScrollingEnabled(false);
        toggleNothigTodayVisibility();
    }

    /**
     * set listivew for upcoming reminders
     */
    private void setUpcomingListViewItem() {
        upcoming.addAll(controller.getWeekReminder());
        upcomingAdapter = new HomeRecyclerViewAdapter(upcoming);
        RecyclerView upcomingRecycler = view.findViewById(R.id.home_upcoming_listview);
        upcomingRecycler.setAdapter(upcomingAdapter);
        upcomingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        upcomingRecycler.setNestedScrollingEnabled(false);
        toggleNothigTodayVisibility();
    }

    /**
     * Toggle text nothing today visibility
     */
    private void toggleNothigTodayVisibility() {
        if (today.size() != 0) {
            view.findViewById(R.id.home_nothing_today).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.home_nothing_today).setVisibility(View.VISIBLE);
        }
        if (upcoming.size() != 0) {
            view.findViewById(R.id.home_nothing_upcoming).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.home_nothing_upcoming).setVisibility(View.VISIBLE);
        }
    }

    /**
     * Reads file for new reminders and update recyclerview
     */
    public void refresh() {
        try {
            controller.update();
            updateReminderArrayList();
            todayAdapter.notifyDataSetChanged();
            upcomingAdapter.notifyDataSetChanged();
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Oopsie! Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * update the today and upcoming arraylist
     */
    private void updateReminderArrayList() {
        today.clear();
        today.addAll(controller.getTodayReminder());
        upcoming.clear();
        upcoming.addAll(controller.getWeekReminder());
        toggleNothigTodayVisibility();
    }
}
