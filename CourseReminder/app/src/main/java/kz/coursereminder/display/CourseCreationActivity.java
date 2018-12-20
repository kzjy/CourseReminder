package kz.coursereminder.display;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import java.io.Serializable;
import java.util.ArrayList;

import kz.coursereminder.R;
import kz.coursereminder.classes.CourseCreationIconAdapter;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseCreationController;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;

public class CourseCreationActivity extends AppCompatActivity {

    private static final String TAG = "CourseCreationActivity";

    /**
     * Display gridview
     */
    private GridView gridView;
    private CourseCreationIconAdapter creationIconAdapter;

    /**
     * Arraylist of possible course icons
     */

    private ArrayList<Integer> icons = new ArrayList<>();

    /**
     * Controller for Course Creation
     */
    private CourseCreationController courseCreationController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_creation);

        // action bar stuff
        setTitle("New course");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // setup icons
        addIconToList();
        //grid view
        setupView();

        //Instantiate controller
        courseCreationController = new CourseCreationController(this);
    }

    /**
     * Set up the view of the activity
     */
    private void setupView() {
        gridView = findViewById(R.id.create_course_grid);
        creationIconAdapter = new CourseCreationIconAdapter(this, icons);
        gridView.setAdapter(creationIconAdapter);
        addGridViewClickListener();
    }

    /**
     * action bar menu stuff
     *
     * @param menu menu
     * @return ?
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_creation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent = new Intent();
        setResult(AppCompatActivity.RESULT_OK, resultIntent);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.create_course:
                // make new course
                String name = courseNameListener();
                String info = courseInfoListener();
                int image = creationIconAdapter.getHighLight() + 1;
                boolean creationSuccessful = courseCreationController.addCourse(name, info, image);
                if (creationSuccessful) {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Listeners in layout
     */
    private void addGridViewClickListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Integer icon = icons.get(position);
                creationIconAdapter.setHighLight(position);
                creationIconAdapter.notifyDataSetChanged();
            }
        });
    }

    private String courseNameListener() {
        EditText courseName = findViewById(R.id.create_course_name);
        courseName.requestFocus();
        courseName.requestFocus();
        return courseName.getText().toString();
    }

    private String courseInfoListener() {
        EditText courseInfo = findViewById(R.id.create_course_info);
        return courseInfo.getText().toString();
    }

    /**
     * Add these icons to the list of available icons
     */
    private void addIconToList() {
        icons.add(R.drawable.course_icon_art);
        icons.add(R.drawable.course_icon_biology);
        icons.add(R.drawable.course_icon_chem);
        icons.add(R.drawable.course_icon_cs);
        icons.add(R.drawable.course_icon_econ);
        icons.add(R.drawable.course_icon_eng);
        icons.add(R.drawable.course_icon_film);
        icons.add(R.drawable.course_icon_lit);
        icons.add(R.drawable.course_icon_math);
        icons.add(R.drawable.course_icon_music);
        icons.add(R.drawable.course_icon_physics);
        icons.add(R.drawable.course_icon_stats);
    }

}
