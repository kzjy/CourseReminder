package kz.coursereminder.display;


import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import kz.coursereminder.R;
import kz.coursereminder.adapters.CalendarRecyclerViewAdapter;
import kz.coursereminder.controllers.NotificationController;
import kz.coursereminder.structure.Reminder;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    private View view;
    private CalendarRecyclerViewAdapter adapter;
    private ArrayList<Reminder> selectedDateReminder = new ArrayList<>();
    private NotificationController controller;

    public CalendarFragment() {
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
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        Drawable backgroundDrawable = ((MainActivity) getActivity()).getBackgroundDrawable();
        ImageView background = view.findViewById(R.id.calendar_background);
        background.setImageDrawable(backgroundDrawable);
        setUpRecyclerView();
        calendarClickListener();
        return view;
    }

    private void setUpRecyclerView() {
        Calendar calendar = Calendar.getInstance();
        selectedDateReminder.addAll(controller.getDateReminder(calendar));
        RecyclerView recyclerView = view.findViewById(R.id.calendar_listview);
        adapter = new CalendarRecyclerViewAdapter(selectedDateReminder);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        toggleNothingTextVisibility();
    }

    private void calendarClickListener() {
        CalendarView calendarView = view.findViewById(R.id.calendar_calendar_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                changeRecyclerViewItem(c);
            }
        });
    }

    private void changeRecyclerViewItem(Calendar calendar) {
        selectedDateReminder.clear();
        selectedDateReminder.addAll(controller.getDateReminder(calendar));
        adapter.notifyDataSetChanged();
        toggleNothingTextVisibility();
    }

    public void refresh() {
        controller.update();
        changeRecyclerViewItem(Calendar.getInstance());
    }

    private void toggleNothingTextVisibility() {
        TextView nothing = view.findViewById(R.id.calendar_nothing);
        if (selectedDateReminder.size() == 0) {
            nothing.setVisibility(View.VISIBLE);
        } else {
            nothing.setVisibility(View.GONE);
        }
    }
}
