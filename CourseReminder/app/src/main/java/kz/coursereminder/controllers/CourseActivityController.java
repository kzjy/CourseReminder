package kz.coursereminder.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import kz.coursereminder.display.CourseActivity;
import kz.coursereminder.display.MainActivity;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.Grade;
import kz.coursereminder.structure.Reminder;


public class CourseActivityController extends CourseCreationController implements Serializable {

    private static final String TAG = "CourseActivityControlle";

    private Course currentCourse;

    public CourseActivityController(Context context, String courseName) {
        super(context);
        this.currentCourse = courseManager.getSpecificCourse(courseName);
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

    /**
     * Save an edit to the course name, info or notes
     *
     * @param input the edit to be saved
     * @param type  one of name, info or notes
     */
    public void saveEdits(String input, String type) {
        switch (type) {
            case ("name"):
                Course dummyCourse = new Course(input, "", 0);
                if (checkValidName(dummyCourse)) {
                    currentCourse.setName(input);
                }
                break;
            case ("info"):
                currentCourse.setInfo(input);
                break;
            case ("notes"):
                currentCourse.setNotes(input);
                break;
        }
        save();
        makeTosstEditSsved();
    }

    /**
     * Make Toast edit saved
     */
    private void makeTosstEditSsved() {
        Toast.makeText(context, "Edits saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Make Toast maximum reminder exceeded
     */
    private void makeToastMaximumReminderExceeded() {
        Toast.makeText(context, "You have reached 50 reminders, " +
                "delete inactive ones and try again", Toast.LENGTH_SHORT).show();
    }

    /**
     * Check stringInput for empty or 0 inputs
     *
     * @param stringInput string input of grade
     * @return whether it is valid
     */
    public boolean checkNotValidGradeInput(String[] stringInput) {
        for (int i = 0; i < stringInput.length; i++) {
            if (stringInput[i].equals("") || stringInput[i].equals(".")) {
                return true;
            }
            if (i == 1) {
                if (Float.valueOf(stringInput[i]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Add a reminder to the current course
     *
     * @param reminder reminder to be added
     * @return whether addition is successful
     */
    public boolean addReminder(Reminder reminder) {
        boolean succesful = courseManager.addReminderToCourse(currentCourse, reminder);
        save();
        if (!succesful) {
            makeToastMaximumReminderExceeded();
        }
        return succesful;
    }

    /**
     * Removes an assignment from course
     *
     * @param position position of assignment in list
     */
    public void removeAssignment(int position) {
        currentCourse.removeTask(position);
        courseManager.cleanUpReminderManager();
        save();
        alarmManager.setUpAlarms();
    }

    /**
     * Assign a grade to an assignment
     *
     * @param grade grade to be added
     * @param positionSelected position in list of assignment
     */
    public void addGradeToAssignment(Grade grade, int positionSelected) {
        currentCourse.setReminderGrade(grade, positionSelected);
        courseManager.cleanUpReminderManager();
        save();
        alarmManager.setUpAlarms();
    }

    /**
     * Edit grade in completed assignments
     *
     * @param grade new grade to be changed
     * @param positionSelected position of the assignment
     */
    public void editGrade(Grade grade, int positionSelected) {
        currentCourse.editReminderGrade(grade, positionSelected);
        save();
    }

    /**
     * Switch between the time of notification before due date
     * @param position position selected
     * @return minutes before notification
     */
    public int calculateSpinnerMinutesBefore(int position) {
        int[] list = new int[] {30, 60, 120, 240, 1440};
        if (position < list.length) {
            return list[position];
        }
        return 30;
    }

    /**
     * Add options for the drop down menu in reminder creation
     * @param notificationTime list of notification times
     */
    public void addNotificationSpinnerOptions(List<String> notificationTime) {
        notificationTime.add("30 minutes prior");
        notificationTime.add("1 hour prior");
        notificationTime.add("2 hours prior");
        notificationTime.add("4 hours prior");
        notificationTime.add("1 day prior");
    }

    public String getTypeSelected(int position) {
        String[] list = new String[] {"Assignment", "Meet up", "Study", "Test"};
        if (position < list.length) {
            return list[position];
        }
        return "Assignment";
    }
    /**
     * Save the course manager
     */
    private void save() {
        fileManager.writeFile(CourseManager.COURSES, courseManager);
    }

    /**
     * Delete the current course and save its changes
     */
    public void deleteCurrentCourse() {
        courseManager.deleteCourse(currentCourse);
        save();
        alarmManager.setUpAlarms();
    }

    /**
     * Reads the most recent coursemanager file and updates the controller
     */
    public void updateController() {
        fileManager.loadFile(CourseManager.COURSES);
        this.courseManager = fileManager.getCourseManager();
        currentCourse = courseManager.getSpecificCourse(currentCourse.getName());
    }

}
