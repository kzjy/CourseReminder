package kz.coursereminder.display;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import kz.coursereminder.R;
import kz.coursereminder.adapters.NotificationRecyclerViewAdapter;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;
import kz.coursereminder.structure.Reminder;


public class Notifications extends Fragment {

    private CourseManager courseManager;
    private NotificationRecyclerViewAdapter adapter;

    public Notifications() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadManager();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        setUpRecyclerView(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void setUpRecyclerView(View view) {
        RecyclerView notificationRecycler = view.findViewById(R.id.notification_recycler_view);
        adapter = new NotificationRecyclerViewAdapter(getContext(),
                generateDisplayArrayList());
        notificationRecycler.setAdapter(adapter);
        notificationRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationRecycler.setNestedScrollingEnabled(false);
    }

    private void loadManager() {
        FileManager fileManager = new FileManager(this.getActivity());
        courseManager = fileManager.getCourseManager();
    }

    private ArrayList<Reminder> generateDisplayArrayList() {
        ArrayList<Reminder> reminders = new ArrayList<>();
        ArrayList<Course> courseList = courseManager.getCourses();
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            reminders.addAll(course.getReminders());
        }
        Collections.sort(reminders);
        Collections.reverse(reminders);
        return reminders;
    }

    public void refresh() {
        loadManager();
        adapter.notifyDataSetChanged();
    }
}
