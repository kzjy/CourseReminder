package kz.coursereminder.structure;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseManager implements Serializable {

    private ArrayList<Course> courses = new ArrayList<>();

    public static final String COURSES = "course_manager.ser";

    public CourseManager() {
        addCourse("Add New", 0);
    }

    public void addCourse(Course course) {
        courses.add(0, course);
    }

    private void addCourse(String name, int image) {
        courses.add(new Course(name, image));
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses.addAll(0, courses);
    }

    public Course getSpecificCourse(String courseName) {
        for (Course course: courses) {
            if (course.getName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }

}
