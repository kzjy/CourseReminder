package kz.coursereminder.display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import kz.coursereminder.R;
import kz.coursereminder.adapters.CourseAssignmentAdapter;
import kz.coursereminder.adapters.CourseRecyclerItemTouchHelper;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.popup.AssignmentDeletePopUp;
import kz.coursereminder.popup.CourseDeletePopUp;
import kz.coursereminder.popup.CourseInfoEditPopUp;
import kz.coursereminder.popup.CourseNameEditPopUp;
import kz.coursereminder.popup.CourseNotesEditPopUp;
import kz.coursereminder.popup.GradeAdditionPopUp;
import kz.coursereminder.popup.GradeEditPopUp;

public class CourseActivity extends ThemedActivity implements View.OnLongClickListener,
CourseRecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    /**
     * Course Activity Controller
     */
    CourseActivityController courseActivityController;

    /**
     * Arraylist of all textViews
     * index 0 -> course name
     * index 1 -> course info
     * index 2 -> course notes
     */
    private ArrayList<TextView> textViews = new ArrayList<>();
    CourseAssignmentAdapter assignmentAdapter;
    CourseAssignmentAdapter gradeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        // get current course info
        String name = getIntent().getStringExtra("name");
        courseActivityController = new CourseActivityController(this, name);
        // display current course info
        findTextView();
        displayCourseInfo();
        // Button listeners
        deleteButtonListener();
        gradeButtonListener();
        assignmentButtonListener();
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
        textViews.add((TextView) findViewById(R.id.course_average));
    }
    /**
     * Displays the course info on activity
     */
    public void displayCourseInfo() {
        courseActivityController.updateController();
        setTitle(courseActivityController.getCurrentCourse().getName());
        textViews.get(0).setText(courseActivityController.getCurrentCourse().getName());
        textViews.get(1).setText(courseActivityController.getCurrentCourse().getInfo());
        textViews.get(2).setText(courseActivityController.getCurrentCourse().getNotes());
        String s = String.format(Locale.CANADA, "%.2f ",
                courseActivityController.getCurrentCourse().calculateAverage()) + "%";
        textViews.get(3).setText(s);
        updateAssignment();
        updateGrade();
    }
    /**
     * Display assignment information
     */
    private void updateAssignment() {
        RecyclerView recyclerView = findViewById(R.id.course_assignment_list);
        assignmentAdapter = new CourseAssignmentAdapter(this,
                courseActivityController, false);
        recyclerView.setAdapter(assignmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        ItemTouchHelper.SimpleCallback itemTouchHelper =
                new CourseRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);
    }
    /**
     * Display grade information
     */
    private void updateGrade() {
        RecyclerView recyclerView = findViewById(R.id.course_grade_list);
        gradeAdapter = new CourseAssignmentAdapter(this,
                courseActivityController, true);
        recyclerView.setAdapter(gradeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
    }
    /**
     * Add new assignment button
     */
    private void assignmentButtonListener() {
        Button addAssignment = findViewById(R.id.course_add_assignment_button);
        addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAssignmentCreationActivity();
            }
        });
    }
    /**
     * Start AssignmentCreationActivity
     */
    private void startAssignmentCreationActivity() {
        Intent assignmentCreation = new Intent(this, AssignmentCreationActivity.class);
        assignmentCreation.putExtra("course", courseActivityController.getCurrentCourse());
        startActivityForResult(assignmentCreation, 111);
    }
    /**
     * Add new grade button
     */
    private void gradeButtonListener() {
        Button addGrade = findViewById(R.id.course_add_grade_button);
        addGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGradeAdditionPopUp();
            }
        });
    }

    private void showGradeAdditionPopUp() {
        new GradeAdditionPopUp(this, courseActivityController).showPopUp();
    }
    /**
     * Delete course button
     */
    private void deleteButtonListener() {
        Button delete = findViewById(R.id.course_delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteCoursePopUp();
            }
        });
    }

    private void showDeleteCoursePopUp() {
        new CourseDeletePopUp(this, courseActivityController).showPopUp();
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

    public void onGradeLongClicked(int i) {
        new GradeEditPopUp(this, courseActivityController, i).showPopUp();

    }

    /**
     * onLongClick method for course name, info ,notes edit
     * @param v the view longclicked
     * @return a boolean
     */
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.course_name:
                new CourseNameEditPopUp(this, courseActivityController).showPopUp();
                return true;
            case R.id.course_info:
                new CourseInfoEditPopUp(this, courseActivityController).showPopUp();
                return true;
            case R.id.course_notes:
                new CourseNotesEditPopUp(this, courseActivityController).showPopUp();
                return true;
        }
        return false;
    }

    /**
     * onSwipe to delete assignment
     * @param viewHolder viewholder of the assignment to delete
     * @param direction direction of swipe
     * @param position position of viewholder
     */
    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        new AssignmentDeletePopUp(this, courseActivityController, position).showPopUp();
        assignmentAdapter.notifyDataSetChanged();
    }

    /**
     * OnResume of Activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        displayCourseInfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 111) && (resultCode == AppCompatActivity.RESULT_OK)) {
            displayCourseInfo();
        }
    }
}
