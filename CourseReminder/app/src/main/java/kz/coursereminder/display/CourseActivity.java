package kz.coursereminder.display;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
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

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;

public class CourseActivity extends AppCompatActivity {
    /**
     * Course Activity Controller
     */
    CourseActivityController courseActivityController;
    /**
     * Menu Items
     */
    private MenuItem edit;
    private MenuItem done;
    /**
     * TextView and EditText for course details display
     */
    EditText courseEdit;
    TextView courseName;
    EditText courseInfoEdit;
    TextView courseInfo;
    EditText courseNotesEdit;
    TextView courseNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        // get current course info
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        courseActivityController = new CourseActivityController(this, name);
        // display current course info
        findLayoutComponent();
        displayCourseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_activity_menu, menu);
        done = menu.findItem(R.id.done_edit);
        edit = menu.findItem(R.id.edit_course);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent resultIntent = new Intent();
                setResult(AppCompatActivity.RESULT_OK, resultIntent);
                finish();
                return true;
            case R.id.edit_course:
                toggleEdit();
                return true;
            case R.id.done_edit:
                courseActivityController.saveEdit(
                        courseEdit.getText().toString(),
                        courseInfoEdit.getText().toString(),
                        courseNotesEdit.getText().toString());
                toggleEdit();
                hideKeyboard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Finds the layout views
     */
    private void findLayoutComponent() {
        courseEdit = findViewById(R.id.course_name_edit);
        courseName = findViewById(R.id.course_name);
        courseInfo = findViewById(R.id.course_info);
        courseInfoEdit = findViewById(R.id.course_info_edit);
        courseNotes = findViewById(R.id.course_notes);
        courseNotesEdit = findViewById(R.id.course_notes_edit);
    }

    /**
     * Displays the course info on activity
     */
    private void displayCourseInfo() {
        setTitle(courseActivityController.getCurrentCourse().getName());
        courseName.setText(courseActivityController.getCurrentCourse().getName());
        courseInfo.setText(courseActivityController.getCurrentCourse().getInfo());
        courseNotes.setText(courseActivityController.getCurrentCourse().getNotes());
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

    /**
     * Toggle edit mode
     */
    private void toggleEdit() {
        boolean currentEditEnable = done.isVisible();
        int currentTextEditEnable = (currentEditEnable) ? View.VISIBLE : View.GONE;
        int notTextEditEnable = (currentEditEnable) ? View.GONE : View.VISIBLE;
        toggleEditVisibility(currentEditEnable, currentTextEditEnable, notTextEditEnable);
        if (currentEditEnable) {
            displayCourseInfo();
        } else {
            setEdiText();
        }
    }

    /**
     * Toggle all the edit text visibility
     * @param currentEditEnable whether edit is enabled right now
     * @param currentTextEditEnable whether edit text is visible right now
     * @param notTextEditEnable int of not currentTextEnable
     */
    private void toggleEditVisibility(boolean currentEditEnable, int currentTextEditEnable, int notTextEditEnable) {
        done.setVisible(!currentEditEnable);
        edit.setVisible(currentEditEnable);
        courseEdit.setVisibility(notTextEditEnable);
        courseName.setVisibility(currentTextEditEnable);
        courseInfo.setVisibility(currentTextEditEnable);
        courseInfoEdit.setVisibility(notTextEditEnable);
        courseNotesEdit.setVisibility(notTextEditEnable);
        courseNotes.setVisibility(currentTextEditEnable);
    }

    /**
     * Set the edit text to the current text
     */
    private void setEdiText() {
        courseEdit.setText(courseActivityController.getCurrentCourse().getName());
        courseNotesEdit.setText(courseActivityController.getCurrentCourse().getNotes());
        courseInfoEdit.setText(courseActivityController.getCurrentCourse().getInfo());
    }

    /**
     * Hides the soft keyboard when edit mode is done
     */
    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(
                AppCompatActivity.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(courseName.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
