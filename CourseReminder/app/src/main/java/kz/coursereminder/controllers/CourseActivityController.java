package kz.coursereminder.controllers;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;

import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.Reminder;


public class CourseActivityController extends CourseControllers implements Serializable {

    private Course currentCourse;

    public CourseActivityController(Context context, String courseName) {
        super(context);
        this.currentCourse = courseManager.getSpecificCourse(courseName);
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }


    public void saveEdits(String input, String type) {
        switch (type) {
            case ("name"):
                Course dummyCourse = new Course(input, "", 0);
                if (checkValidName(dummyCourse)) {
                    currentCourse.setName(input);
                }
                break;
            case("info"):
                currentCourse.setInfo(input);
                break;
            case("notes"):
                currentCourse.setNotes(input);
                break;
        }
        save();
        makeTosstEditSsved();
    }

    private void makeTosstEditSsved() {
        Toast.makeText(context, "Edits saved", Toast.LENGTH_SHORT).show();
    }

    public boolean addTask(Reminder reminder) {
        if (reminder.getTime().equals("at") || reminder.getDate().equals("") || reminder.getName().equals("")) {
            makeToastTaskFieldNotCompleted();
            return false;
        }
        currentCourse.addTask(reminder);
        save();
        return true;
    }

    public void removeAssignment(int position) {
        currentCourse.removeTask(position);
        save();
    }

    public void save() {
        fileManager.writeFile(CourseManager.COURSES, courseManager);
    }

    /**
     * Delete the current course and save its changes
     */
    public void deleteCurrentCourse() {
        courseManager.deleteCourse(currentCourse);
        save();
    }


    public void updateController() {
        fileManager.loadFile(CourseManager.COURSES);
        this.courseManager = fileManager.getCourseManager();
        currentCourse = courseManager.getSpecificCourse(currentCourse.getName());
    }

    private void makeToastTaskFieldNotCompleted() {
        Toast.makeText(context, "Please fill in task name, date, and time", Toast.LENGTH_SHORT).show();
    }
}
