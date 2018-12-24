package kz.coursereminder.display;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;

import java.util.Calendar;

import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.Reminder;

public class AssignmentCreationActivity extends AppCompatActivity {

    private CourseActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_creation);
        setTitle("New Reminder ");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        Course course = (Course) intent.getSerializableExtra("course");
        controller = new CourseActivityController(this, course.getName());
        timeSelectListener();
        dateSelectListener();
    }

    /**
     * action bar menu stuff
     *
     * @param menu menu
     * @return ?
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assignment_creation_menu, menu);
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
            case R.id.create_assignment:
                boolean creationSuccesful = createTask();
                if (creationSuccesful) {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void timeSelectListener() {
        final TextView timeSelect = findViewById(R.id.assignment_choose_time);
        timeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(timeSelect);
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AssignmentCreationActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                                String nonZeroMinute = (minutes == 0) ?  "00": String.valueOf(minutes);
                                String s = hourOfDay + " : " + nonZeroMinute;
                                timeSelect.setText(s);
                            }
                        }, 0, 0, false);
                timePickerDialog.show();
            }
        });

    }

    private void dateSelectListener() {
        final TextView dateSelect = findViewById(R.id.assignment_choose_date);
        dateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(dateSelect);
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AssignmentCreationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String s = dayOfMonth + " / " + (month + 1) + " / " + year;
                                dateSelect.setText(s);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private boolean createTask() {
        String taskName = ((EditText) findViewById(R.id.assignment_creation_name)).getText().toString();
        boolean taskIsTest = ((Switch) findViewById(R.id.assignment_test_switch)).isChecked();
        String taskDate = ((TextView) findViewById(R.id.assignment_choose_date)).getText().toString();
        String taskTime = ((TextView) findViewById(R.id.assignment_choose_time)).getText().toString();
        Reminder reminder = new Reminder(taskName, taskDate, taskTime, taskIsTest);
        return controller.addTask(reminder);
    }
}
