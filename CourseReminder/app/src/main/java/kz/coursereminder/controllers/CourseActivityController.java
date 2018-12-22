package kz.coursereminder.controllers;

import android.content.Context;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.CourseManager;


public class CourseActivityController extends  CourseControllers{

    private Course currentCourse;

    public CourseActivityController (Context context , String courseName) {
        super(context);
        this.currentCourse = courseManager.getSpecificCourse(courseName);
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

    public void saveEdit(String newName, String newInfo, String newNotes) {
        Course dummyCourse = new Course(newName, newInfo, 0);
        if (checkValidName(dummyCourse)) {
            currentCourse.setName(newName);
        }
        currentCourse.setInfo(newInfo);
        currentCourse.setNotes(newNotes);
        fileManager.writeFile(CourseManager.COURSES, courseManager);
    }
}
