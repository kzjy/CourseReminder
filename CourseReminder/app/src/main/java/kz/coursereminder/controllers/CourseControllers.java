package kz.coursereminder.controllers;

import android.content.Context;
import android.widget.Toast;


import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;

abstract class CourseControllers {

    Context context;
    FileManager fileManager;
    CourseManager courseManager;

    CourseControllers (Context context) {
        this.context = context;
        this.fileManager = new FileManager(context);
        this.courseManager = fileManager.getCourseManager();
    }

    private void makeToastInvalidName() {
        Toast.makeText(context, "Course name must be between 3 to 12 characters in length ",
                Toast.LENGTH_SHORT).show();
    }

    private void makeToastNameInUse() {
        Toast.makeText(context, "Course name already in use", Toast.LENGTH_SHORT).show();
    }

    private void makeTosstEditSsved() {
        Toast.makeText(context, "Edits saved", Toast.LENGTH_SHORT).show();
    }

    boolean checkValidName(Course course) {
        boolean valid = courseManager.checkValidCourseName(course);
        boolean exists = courseManager.checkExistingCourse(course);
        if (!valid) {
            makeToastInvalidName();
            return false;
        } else if (exists) {
            makeToastNameInUse();
            return false;
        }
        makeTosstEditSsved();
        return true;
    }
}
