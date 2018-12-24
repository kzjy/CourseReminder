package kz.coursereminder.controllers;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;

import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.Task;


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

    public boolean addTask(Task task) {
        if (task.getTime().equals("at") || task.getDate().equals("") || task.getName().equals("")) {
            makeToastTaskFieldNotCompleted();
            return false;
        }
        currentCourse.addTask(task);
        save();
        return true;
    }

    public void removeAssignment(int position) {
        currentCourse.removeTask(position);
        save();
    }

    private void save() {
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
