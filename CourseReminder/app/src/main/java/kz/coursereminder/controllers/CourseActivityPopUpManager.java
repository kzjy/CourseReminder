package kz.coursereminder.controllers;

import android.content.Context;

import kz.coursereminder.popup.AssignmentCreationPopUp;
import kz.coursereminder.popup.CourseDeletePopUp;
import kz.coursereminder.popup.CourseInfoEditPopUp;
import kz.coursereminder.popup.CourseNameEditPopUp;
import kz.coursereminder.popup.CourseNotesEditPopUp;
import kz.coursereminder.popup.PopUp;

public class CourseActivityPopUpManager {

    private Context context;
    /**
     * Arraylist of all dialogs
     * index 0 -> delete course
     * index 1 -> edit course name
     * index 2 -> edit info
     * index 3 -> edit notes
     */
    private PopUp[] popUps = new PopUp[5];

    public CourseActivityPopUpManager(Context context, CourseActivityController controller) {
        this.context = context;
        addPopUps(controller);
    }

    private void addPopUps(CourseActivityController controller) {
        popUps[0] = new CourseNameEditPopUp(context, controller);
        popUps[1] = new CourseInfoEditPopUp(context, controller);
        popUps[2] = new CourseNotesEditPopUp(context, controller);
        popUps[3] = new CourseDeletePopUp(context, controller);
        popUps[4] = new AssignmentCreationPopUp(context, controller);
    }

    public void showPopUp(int index) {
        popUps[index].showPopUp();
    }

}
