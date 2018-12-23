package kz.coursereminder.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;

public class AssignmentCreationPopUp extends PopUp {

    private CourseActivityController controller;

    public AssignmentCreationPopUp (Context context, CourseActivityController controller) {
        this.context = context;
        this.dialog = new Dialog(context);
        this.controller = controller;
    }

    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_assignment_creation);
    }

    @Override
    void noButtonListener() {

    }

    @Override
    void yesButtonListener() {

    }

    @Override
    Bundle getDialogInput() {
        return null;
    }
}
