package kz.coursereminder.controllers;

import android.content.Context;
import android.widget.Toast;

import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.FileManager;

public class CourseCreationController extends CourseControllers {

    public CourseCreationController(Context context) {
        super(context);
    }

    /**
     * Adds a course to the list of courses on the device
     *
     * @param name  name of the course
     * @param info  course info
     * @param image icon selected for the course
     * @return whether the addition is successful
     */
    public boolean addCourse(String name, String info, int image) {
        Course newCourse = new Course(name, info, image);
        if (checkValidName(newCourse)) {
            courseManager.addCourse(newCourse);
            fileManager.writeFile(CourseManager.COURSES, courseManager);
            return true;
        }
        return false;
    }


}
