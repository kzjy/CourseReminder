package kz.coursereminder.structure;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseManager implements Serializable {

    private ArrayList<Course> courses = new ArrayList<>();
    private ReminderManager reminderManager;

    public static final String COURSES = "course_manager.ser";

    public CourseManager() {
        Course addNew = new Course("Add New", "", 0);
        addCourse(addNew);
        reminderManager = new ReminderManager();
    }

    /**
     * Add a new course to the list of courses
     * @param course New Course to be added
     */
    public void addCourse(Course course) {
        courses.add(0, course);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Return a Course in courses with courseName
     * @param courseName name of the course
     * @return a course in courses
     */
    public Course getSpecificCourse(String courseName) {
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }

    /**
     * Check whether course has a valid course name
     * @param course course to be checked
     * @return whether it is a valid name
     */
    public boolean checkValidCourseName(Course course) {
        String name = course.getName();
        return (name.length() >= 3 ) && (name.length() <= 12);
    }

    /**
     * Check whether course name is already in use
     * @param course course to be checked
     * @return whether it is in use
     */
    public boolean checkExistingCourse(Course course) {
        String name = course.getName();
        for (Course c : courses) {
            if (c.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Delete a course from courses if it is valid
     * @param course course to be deleted
     * @return whether deletion was successful
     */
    public boolean deleteCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
            return true;
        }
        return false;
    }


    public boolean addReminderToCourse(Course course, Reminder reminder) {
        if (courses.contains(course)) {
            int i = reminderManager.addReminder(reminder);
            if (i == -1) {
                return false;
            }
            course.addTask(reminder);
        }
        return false;
    }

    public void removeReminderFromCourse(Course course, int position, Reminder reminder) {
        reminderManager.removeReminder(reminder);
        course.removeTask(position);
    }
}
