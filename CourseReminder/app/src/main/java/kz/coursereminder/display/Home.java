package kz.coursereminder.display;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kz.coursereminder.R;
import kz.coursereminder.adapters.HomeRecyclerViewAdapter;
import kz.coursereminder.controllers.NotificationController;
import kz.coursereminder.structure.Reminder;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private View view;
    private NotificationController controller;
    private HomeRecyclerViewAdapter todayAdapter;
    private HomeRecyclerViewAdapter upcomingAdapter;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new NotificationController(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        // set background
        Drawable backgroundDrawable = ((MainActivity) getActivity()).getBackgroundDrawable();
        ((ImageView) view.findViewById(R.id.home_background)).setImageDrawable(backgroundDrawable);
        // set icon
        Drawable iconDrawable = ((MainActivity) getActivity()).getIconDrawable();
        ((ImageView) view.findViewById(R.id.home_icon)).setImageDrawable(iconDrawable);
        // set name
        String user = "Welcome, " + ((MainActivity) getActivity()).getUserName();
        ((TextView) view.findViewById(R.id.home_user)).setText(user);
        // set theme accent
        setAccentColor();
        // set listview display
        setTodayListViewItem();
        setUpcomingListViewItem();
        return view;
    }

    /**
     * set color accent above profile
     */
    private void setAccentColor() {
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int color = typedValue.data;
        view.findViewById(R.id.home_themed_accent).setBackgroundColor(color);
    }

    /**
     * Set listivew for today's reminder
     */
    private void setTodayListViewItem() {
        ArrayList<Reminder> todayReminder = controller.getTodayReminder();
        todayAdapter = new HomeRecyclerViewAdapter(todayReminder);
        RecyclerView today = view.findViewById(R.id.home_today_listview);
        today.setAdapter(todayAdapter);
        today.setLayoutManager(new LinearLayoutManager(getContext()));
        today.setNestedScrollingEnabled(false);
        if (todayReminder.size() != 0) {
            view.findViewById(R.id.home_nothing_today).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.home_nothing_today).setVisibility(View.VISIBLE);
        }

    }

    /**
     * set listivew for upcoming reminders
     */
    private void setUpcomingListViewItem() {
        ArrayList<Reminder> weekReminder = controller.getWeekReminder();
        upcomingAdapter = new HomeRecyclerViewAdapter(weekReminder);
        RecyclerView upcoming = view.findViewById(R.id.home_upcoming_listview);
        upcoming.setAdapter(upcomingAdapter);
        upcoming.setLayoutManager(new LinearLayoutManager(getContext()));
        upcoming.setNestedScrollingEnabled(false);
        Log.v("updated", "b");
        if (weekReminder.size() != 0) {
            view.findViewById(R.id.home_nothing_upcoming).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.home_nothing_upcoming).setVisibility(View.VISIBLE);
        }
    }

    /**
     * Reads file for new reminders and update recyclerview
     */
    public void refresh() {
        controller.update();
        setTodayListViewItem();
        setUpcomingListViewItem();
    }
}
