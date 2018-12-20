package kz.coursereminder.display;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import kz.coursereminder.R;
import kz.coursereminder.classes.DashboardCourseAdapter;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoard extends Fragment {

    /**
     * Debugging fields
     */
    private static final String TAG = "DashBoard";
    public String FRAGMENT_NAME = "Courses";

    /**
     * Data Structure Fields
     */
    private FileManager fileManager;
    private CourseManager courseManager;
    private ArrayList<Course> courseList;
    /**
     * Display Fields
     */
    private GridView gridView;
    public DashboardCourseAdapter dashboardCourseAdapter;

    /**
     * default constructor
     */
    public DashBoard() {
        // Required empty public constructor
    }

    /**
     * On creates
     * @param savedInstanceState some stuff
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileManager = new FileManager(this.getActivity());
        courseManager = fileManager.getCourseManager();
        courseList = new ArrayList<>(courseManager.getCourses());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);
        gridView = view.findViewById(R.id.dashboard_gridview);
        dashboardCourseAdapter = new DashboardCourseAdapter(this.getActivity(), courseManager);
        Log.v(TAG, String.valueOf(courseManager.getCourses().size()));
        gridView.setAdapter(dashboardCourseAdapter);
        addGridViewClickListener();

        return view;
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
                }
                else {
                    swapToCourse(course.getName());
                }
            }
        });
    }

    /**
     * Swap to course specific activity
     * @param courseName the coursename to swap to
     */
    private void swapToCourse(String courseName) {
        Intent toCourse = new Intent(this.getActivity(), CourseActivity.class);
        toCourse.putExtra("name", courseName);
        startActivity(toCourse);
    }

    /**
     * Swap to the course creation activity
     */
    private void swapToCourseCreation() {
        Intent toCreate = new Intent(getActivity() , CourseCreationActivity.class);
        startActivityForResult(toCreate, 10001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 10001) && (resultCode == AppCompatActivity.RESULT_OK)) {
            fileManager.loadFile(CourseManager.COURSES);
            courseManager = fileManager.getCourseManager();
            courseList.add(0, courseManager.getCourses().get(0));
            dashboardCourseAdapter.notifyDataSetChanged();
            Log.v(TAG, String.valueOf(courseManager.getCourses().size()));
        }

    }

}
