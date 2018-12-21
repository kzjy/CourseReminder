package kz.coursereminder.display;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import kz.coursereminder.R;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.FileManager;

public class CourseActivity extends AppCompatActivity {
    /**
     * File manager for loading current course information
     */
    FileManager fileManager;
    Course currentCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        // get current course info
        fileManager = new FileManager(this);
        Intent intent = getIntent();
        String courseName = intent.getStringExtra("name");
        currentCourse = fileManager.getCourseManager().getSpecificCourse(courseName);
        // set title to current course name
        setTitle(courseName);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent resultIntent = new Intent();
                setResult(AppCompatActivity.RESULT_OK, resultIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                // TODO add something
            }
        });
    }
}
