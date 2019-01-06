package kz.coursereminder.display;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import kz.coursereminder.R;
import kz.coursereminder.adapters.DashboardCourseAdapter;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {

    /**
     * Data Structure Fields
     */
    private FileManager fileManager;
    private CourseManager courseManager;
    /**
     * Display Fields
     */
    private GridView gridView;

    /**
     * default constructor
     */
    public DashBoardFragment() {
        // Required empty public constructor
    }

    /**
     * On creates
     *
     * @param savedInstanceState some stuff
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileManager = new FileManager(this.getActivity());
        courseManager = fileManager.getCourseManager();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
            gridView = view.findViewById(R.id.dashboard_gridview);
            if (getActivity() != null) {
                Drawable backgroundDrawable = ((MainActivity) getActivity()).getBackgroundDrawable();
                ((ImageView) view.findViewById(R.id.dashboard_background)).setImageDrawable(backgroundDrawable);
            }
            updateDashboard();
            addGridViewClickListener();

        return view;
    }

    private void updateDashboard() {
        DashboardCourseAdapter dashboardCourseAdapter = new DashboardCourseAdapter(
                this.getActivity(), courseManager);
        gridView.setAdapter(dashboardCourseAdapter);
    }

    /**
     * add grid view listener
     */
    private void addGridViewClickListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Course course = courseManager.getCourses().get(position);
                if (course.getImage() == R.drawable.course_icoin_add) {
                    swapToCourseCreation();
                } else {
                    swapToCourse(course.getName());
                }
            }
        });
    }

    /**
     * Swap to course specific activity
     *
     * @param courseName the coursename to swap to
     */
    private void swapToCourse(String courseName) {
        Intent toCourse = new Intent(this.getActivity(), CourseActivity.class);
        toCourse.putExtra("name", courseName);
        startActivityForResult(toCourse, 10000);
    }

    /**
     * Swap to the course creation activity
     */
    private void swapToCourseCreation() {
        Intent toCreate = new Intent(getActivity(), CourseCreationActivity.class);
        startActivityForResult(toCreate, 10001);
    }

    /**
     * Catches the Course Creation intent for succesfully finishing course creation
     *
     * @param requestCode .
     * @param resultCode .
     * @param data .
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (((requestCode == 10001) || (requestCode == 10000)) &&
                (resultCode == AppCompatActivity.RESULT_OK)) {
            fileManager.loadFile(CourseManager.COURSES);
            courseManager = fileManager.getCourseManager();
            updateDashboard();
        }
    }

}
