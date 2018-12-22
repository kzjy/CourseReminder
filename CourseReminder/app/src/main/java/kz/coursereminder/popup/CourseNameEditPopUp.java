package kz.coursereminder.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.display.CourseActivity;

public class CourseNameEditPopUp extends PopUp {

    CourseActivityController controller;

    public CourseNameEditPopUp(Context context, CourseActivityController controller) {
        this.context = context;
        this.controller = controller;
        this.dialog = new Dialog(context);
    }


    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_course_name_edit);
        String name = controller.getCurrentCourse().getName();
        ((EditText) dialog.findViewById(R.id.course_name_edit)).setText(name);
    }

    @Override
    void noButtonListener() {
        Button no = dialog.findViewById(R.id.course_name_edit_discard);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    void yesButtonListener() {
        Button yes = dialog.findViewById(R.id.course_name_edit_save);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getDialogInput();
                controller.saveEdits((String) bundle.get("name"), "name");
                dismiss();
                ((CourseActivity) context).displayCourseInfo();
            }
        });

    }

    @Override
    Bundle getDialogInput() {
        Bundle bundle = new Bundle();
        Log.v("3","");
        String edit = ((EditText) dialog.findViewById(R.id.course_name_edit)).getText().toString();
        Log.v(edit, "");
        bundle.putString("name", edit);
        return bundle;
    }
}
