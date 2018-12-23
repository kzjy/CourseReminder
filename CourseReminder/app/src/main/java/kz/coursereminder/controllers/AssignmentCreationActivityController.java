package kz.coursereminder.controllers;

import android.content.Context;

import kz.coursereminder.structure.CourseManager;
import kz.coursereminder.structure.Task;
import kz.coursereminder.structure.Course;

public class AssignmentCreationActivityController extends CourseControllers{

    private Course course;

    public AssignmentCreationActivityController(Context context, Course course) {
        super(context);
        this.course = course;
    }

    public void addTask(Task task) {
        course.addTask(task);
        fileManager.writeFile(CourseManager.COURSES, courseManager);
    }
}
