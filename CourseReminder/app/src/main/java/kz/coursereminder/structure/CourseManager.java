package kz.coursereminder.structure;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseManager implements Serializable {

    /**
     * Arraylist of current courses
     */
    private ArrayList<Course> courses = new ArrayList<>();
    /**
     * Remindermanager for active reminders
     */
    private ReminderManager reminderManager;

    /**
     * Serializable filename
     */
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

    public ReminderManager getReminderManager() {
        return reminderManager;
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

    /**
     * Return an arraylist of all the reminders
     * @return arraylist of all reminders
     */
    private ArrayList<Reminder> getAllReminders() {
        ArrayList<Reminder> allReminders = new ArrayList<>();
        for (Course c: courses) {
            allReminders.addAll(c.getReminders());
        }
        return allReminders;
    }

    /**
     * Add a reminder to the course, and to reminderManager
     * @param course course to add reminder in
     * @param reminder reminder to be added
     * @return whether addition is successful
     */
    public boolean addReminderToCourse(Course course, Reminder reminder) {
        if (courses.contains(course)) {
            int i = reminderManager.addReminder(reminder);
            if (i == -1) {
                return false;
            }
            course.addTask(reminder);
            return true;
        }
        return false;
    }

    /**
     * Clean up deleted and past reminders in reminderManager
     */
    public void cleanUpReminderManager() {
        ArrayList<Reminder> allReminder = getAllReminders();
        for (Reminder r: reminderManager.getActiveReminders()) {
            if (r != null && !allReminder.contains(r)) {
                Log.v("cleaned up", r.getName());
                reminderManager.removeReminder(r);
            }
        }
        reminderManager.removePastReminder();
    }

}
