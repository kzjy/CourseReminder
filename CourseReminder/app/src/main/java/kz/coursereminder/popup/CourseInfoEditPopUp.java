package kz.coursereminder.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.display.CourseActivity;

public class CourseInfoEditPopUp extends PopUp {

    CourseActivityController controller;

    public CourseInfoEditPopUp(Context context, CourseActivityController controller) {
        this.context = context;
        this.controller = controller;
        this.dialog = new Dialog(context);
    }


    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_course_info_edit);
        String info = controller.getCurrentCourse().getInfo();
        ((EditText) dialog.findViewById(R.id.course_info_edit)).setText(info);
    }

    @Override
    void noButtonListener() {
        Button no = dialog.findViewById(R.id.course_info_discard);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    void yesButtonListener() {
        Button yes = dialog.findViewById(R.id.course_info_save);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getDialogInput();
                controller.saveEdits((String) bundle.get("info"), "info");
                dismiss();
                ((CourseActivity) context).displayCourseInfo();
            }
        });
    }

    @Override
    Bundle getDialogInput() {
        Bundle bundle = new Bundle();
        String edit = ((EditText) dialog.findViewById(R.id.course_info_edit)).getText().toString();
        bundle.putString("info", edit);
        return bundle;
    }

}
