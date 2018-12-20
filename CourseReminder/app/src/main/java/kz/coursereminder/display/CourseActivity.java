package kz.coursereminder.display;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
