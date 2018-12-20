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
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;

public class CourseCreationActivity extends AppCompatActivity {

    private static final String TAG = "CourseCreationActivity";

    /**
     * Display fields
     */
    private GridView gridView;
    private CourseCreationIconAdapter creationIconAdapter;

    /**
     * Data Fields
     */
    private ArrayList<Integer> icons = new ArrayList<>();
    private FileManager fileManager;
    private CourseManager courseManager;

    /**
     * On create
     *
     * @param savedInstanceState .
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_creation);

        // action bar stuff
        setTitle("New course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // setup icons
        addIconToList();
        //grid view
        gridView = findViewById(R.id.create_course_grid);
        creationIconAdapter = new CourseCreationIconAdapter(this, icons);
        gridView.setAdapter(creationIconAdapter);
        addGridViewClickListener();
        // filemanager and coursemanager
        fileManager = new FileManager(this);
        courseManager = fileManager.getCourseManager();
        //

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
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(AppCompatActivity.RESULT_CANCELED);
//                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
            case R.id.create_course:

                // make new course
                String name = courseNameListener();
                String info = courseInfoListener();
                Course newCourse = new Course (name, creationIconAdapter.getHighLight() + 1);
                courseManager.addCourse(newCourse);
                fileManager.writeFile(CourseManager.COURSES, courseManager);

                // update dashboard & exit
                Intent resultIntent = new Intent();
                resultIntent.putExtra("course", newCourse);
                setResult(AppCompatActivity.RESULT_OK, resultIntent);
                hideSoftKeyboard(this);
                finish();
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
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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

    public static void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
