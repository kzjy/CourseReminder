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

public class CourseNotesEditPopUp extends PopUp{

    public CourseNotesEditPopUp(Context context, CourseActivityController controller) {
        super(context, controller);
    }

    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_course_notes_edit);
        String notes = controller.getCurrentCourse().getNotes();
        ((EditText) dialog.findViewById(R.id.course_notes_edit)).setText(notes);
    }

    @Override
    void noButtonListener() {
        Button no = dialog.findViewById(R.id.course_notes_discard);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    void yesButtonListener() {
        Button yes = dialog.findViewById(R.id.course_notes_save);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getDialogInput();
                controller.saveEdits((String) bundle.get("notes"), "notes");
                dismiss();
                ((CourseActivity) context).displayCourseInfo();
            }
        });
    }

    /**
     *
     * @return
     */
    @Override
    Bundle getDialogInput() {
        Bundle bundle = new Bundle();
        String edit = ((EditText) dialog.findViewById(R.id.course_notes_edit)).getText().toString();
        bundle.putString("notes", edit);
        return bundle;
    }
}
