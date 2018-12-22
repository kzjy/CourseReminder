package kz.coursereminder.display;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DirectedAcyclicGraph;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.controllers.CourseActivityPopUpManager;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;

public class CourseActivity extends AppCompatActivity implements View.OnLongClickListener {
    /**
     * Course Activity Controller
     */
    CourseActivityController courseActivityController;

    CourseActivityPopUpManager popUpManager;

    /**
     * Arraylist of all textViews
     * index 0 -> course name
     * index 1 -> course info
     * index 2 -> course notes
     */
    private ArrayList<TextView> textViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        // get current course info
        String name = getIntent().getStringExtra("name");
        courseActivityController = new CourseActivityController(this, name);
        popUpManager = new CourseActivityPopUpManager(this, courseActivityController);
        // display current course info
        findTextView();
        displayCourseInfo();
        // Button listeners
        deleteButtonListener();
        courseDisplayEditListener();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                back();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Finds the layout views
     */
    private void findTextView() {
        textViews.add((TextView) findViewById(R.id.course_name));
        textViews.add((TextView) findViewById(R.id.course_info));
        textViews.add((TextView) findViewById(R.id.course_notes));
    }

    /**
     * Displays the course info on activity
     */
    public void displayCourseInfo() {
        setTitle(courseActivityController.getCurrentCourse().getName());
        textViews.get(0).setText(courseActivityController.getCurrentCourse().getName());
        textViews.get(1).setText(courseActivityController.getCurrentCourse().getInfo());
        textViews.get(2).setText(courseActivityController.getCurrentCourse().getNotes());
    }

    /**
     * Add new assignment button
     */
    private void assignmentButtonListener() {
        Button addAssignment = findViewById(R.id.course_add_assignment_button);
        addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO add something
            }
        });
    }

    /**
     * Add new grade button
     */
    private void gradeButtonListener() {
        Button addGrade = findViewById(R.id.course_add_grade_button);
        addGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO add something
            }
        });
    }

    /**
     * Delete course button
     */
    private void deleteButtonListener() {
        Button delete = findViewById(R.id.course_delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpManager.showPopUp(3);
            }
        });
    }

    /**
     * Activate textView hold listeners
     */
    private void courseDisplayEditListener() {
        for (int i = 0; i < 3; i++) {
            textViews.get(i).setOnLongClickListener(this);
        }
    }

    /**
     * Go back to dashboard
     */
    public void back() {
        Intent resultIntent = new Intent();
        setResult(AppCompatActivity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.course_name:
                popUpManager.showPopUp(0);
                return true;
            case R.id.course_info:
                popUpManager.showPopUp(1);
                return true;
            case R.id.course_notes:
                popUpManager.showPopUp(2);
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayCourseInfo();
    }
}
