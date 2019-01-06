package kz.coursereminder.controllers;

import android.content.Context;
import android.widget.Toast;

import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;

public class CourseCreationController extends Controller {

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

    /**
     * Make Toast invalid name
     */
    private void makeToastInvalidName() {
        Toast.makeText(context, "Course name must be between 3 to 12 characters in length ",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Make Toast name in use
     */
    private void makeToastNameInUse() {
        Toast.makeText(context, "Course name already in use", Toast.LENGTH_SHORT).show();
    }

    /**
     * Check whether name fits naming criteria
     * @param course course to be checked
     * @return whether name of the course is valid
     */
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
        return true;
    }

}
