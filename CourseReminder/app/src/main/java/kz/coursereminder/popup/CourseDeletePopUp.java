package kz.coursereminder.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.display.CourseActivity;

public class CourseDeletePopUp extends PopUp {

    private CourseActivityController controller;

    public CourseDeletePopUp(Context context, CourseActivityController controller) {
        this.controller = controller;
        this.context = context;
        this.dialog = new Dialog(context);
    }

    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_delete_course);
    }

    @Override
    void noButtonListener() {
        Button no = dialog.findViewById(R.id.popup_delete_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    void yesButtonListener() {
        Button yes = dialog.findViewById(R.id.popup_delete_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.deleteCurrentCourse();
                dismiss();
                ((CourseActivity) context).back();
            }
        });
    }

    @Override
    Bundle getDialogInput() {
        return null;
    }
}
