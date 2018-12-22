package kz.coursereminder.controllers;

import android.content.Context;
import android.widget.Toast;

import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;


public class CourseActivityController extends CourseControllers {

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
        fileManager.writeFile(CourseManager.COURSES, courseManager);
        makeTosstEditSsved();
    }

    private void makeTosstEditSsved() {
        Toast.makeText(context, "Edits saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Delete the current course and save its changes
     */
    public void deleteCurrentCourse() {
        courseManager.deleteCourse(currentCourse);
        fileManager.writeFile(CourseManager.COURSES, courseManager);
    }
}
