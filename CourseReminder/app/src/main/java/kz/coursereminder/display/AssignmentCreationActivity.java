package kz.coursereminder.display;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.Reminder;

public class AssignmentCreationActivity extends ThemedActivity {

    /**
     * Controller of the activity
     */
    private CourseActivityController controller;

    /**
     * Input notification time , -30 by default
     */
    private int minutesBeforeNotification = -30;

    /**
     * Input type, Assignment by default
     */
    private String selectedType = "Assignment";

    /**
     * Calendar of current time
     */
    private Calendar dueDate = Calendar.getInstance();

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
        setImageBackground();
        populateNotificationSpinner();
        notificationSpinnerListener();
        populateTypeSpinner();
        typeSpinnerListener();
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


    /**
     * Set the background image of this activity
     */
    public void setImageBackground() {
        ((ImageView) findViewById(R.id.assignment_background)).setImageDrawable(getBackgroundDrawable());
    }

    /**
     * Timepicker dialog listener
     */
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
                                dueDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                dueDate.set(Calendar.MINUTE, minutes);
                                String nonZeroMinute = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
                                String s = hourOfDay + " : " + nonZeroMinute;
                                timeSelect.setText(s);
                            }
                        }, 0, 0, false);
                timePickerDialog.show();
            }
        });

    }

    /**
     * The datepicker dialog listener
     */
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
                                dueDate.set(Calendar.YEAR, year);
                                dueDate.set(Calendar.MONTH, month);
                                dueDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                String s = dayOfMonth + " / " + (month + 1) + " / " + year;
                                dateSelect.setText(s);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    /**
     * Add notification time options to the spinner
     */
    private void populateNotificationSpinner() {
        List<String> notificationTime = new ArrayList<>();
        controller.addNotificationSpinnerOptions(notificationTime);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, notificationTime);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner notificationSpinner = findViewById(R.id.setting_notification_spinner);
        notificationSpinner.setAdapter(adapter);

    }

    /**
     * Activate the notification spinner listener
     */
    private void notificationSpinnerListener() {
        Spinner notificationSpinner = findViewById(R.id.setting_notification_spinner);
        notificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                minutesBeforeNotification = (- 1) * controller.calculateSpinnerMinutesBefore(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Hide the keyboard
     *
     * @param view the view to hide from
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Set the type spinner and add items to it
     */
    private void populateTypeSpinner() {
        List<String> type = new ArrayList<>();
        type.add("Assignment");
        type.add("Meet up");
        type.add("Study");
        type.add("Test");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner typeSpinner = findViewById(R.id.assignment_type);
        typeSpinner.setAdapter(adapter);
    }

    /**
     * Activate the type spinner listener
     */
    private void typeSpinnerListener() {
        Spinner typeSpinner = findViewById(R.id.assignment_type);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = controller.getTypeSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Create a task via controller
     *
     * @return whether creation was successful
     */
    private boolean createTask() {
        String taskName = ((EditText) findViewById(R.id.assignment_creation_name)).getText().toString();
        String date  = ((TextView) findViewById(R.id.assignment_choose_date)).getText().toString();
        String time = ((TextView) findViewById(R.id.assignment_choose_time)).getText().toString();
        if (date.contains("/") && time.contains(":") && !taskName.equals("")) {
            Reminder reminder = createReminder(taskName);
            return controller.addReminder(reminder);
        }
        makeToastTaskFieldNotCompleted();
        return false;
    }

    /**
     * Create a new reminder with the input fields
     * @param taskName name of reminder
     * @return new reminder
     */
    @NonNull
    private Reminder createReminder(String taskName) {
        Calendar c = (Calendar) dueDate.clone();
        c.add(Calendar.MINUTE, minutesBeforeNotification);
        c.set(Calendar.SECOND, 0);
        return new Reminder(taskName, dueDate, selectedType, c);
    }

    /**
     * Make toast fill in details
     */
    private void makeToastTaskFieldNotCompleted() {
        Toast.makeText(this, "Please fill in reminder name, date, time, and notification " +
                "settings", Toast.LENGTH_SHORT).show();
    }
}
