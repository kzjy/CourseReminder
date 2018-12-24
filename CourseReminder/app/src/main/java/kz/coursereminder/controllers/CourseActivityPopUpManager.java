package kz.coursereminder.controllers;

import android.content.Context;

import kz.coursereminder.adapters.CourseAssignmentAdapter;
import kz.coursereminder.popup.AssignmentDeletePopUp;
import kz.coursereminder.popup.CourseDeletePopUp;
import kz.coursereminder.popup.CourseInfoEditPopUp;
import kz.coursereminder.popup.CourseNameEditPopUp;
import kz.coursereminder.popup.CourseNotesEditPopUp;

public class CourseActivityPopUpManager {

    private Context context;
    private CourseActivityController controller;

    public CourseActivityPopUpManager(Context context, CourseActivityController controller) {
        this.context = context;
        this.controller = controller;
    }

    public void showCourseNameEditPopUp() {
        new CourseNameEditPopUp(context, controller).showPopUp();
    }

    public void showCourseInfoEditPopUp() {
        new CourseInfoEditPopUp(context, controller).showPopUp();
    }

    public void showCourseNotesEditPopUp() {
        new CourseNotesEditPopUp(context, controller).showPopUp();
    }

    public void showCourseDeletePopUp() {
        new CourseDeletePopUp(context, controller).showPopUp();
    }

    public void showAssignmentDeletePopUp(int position, CourseAssignmentAdapter adapter) {
        new AssignmentDeletePopUp(context, controller, position, adapter).showPopUp();
    }

}
