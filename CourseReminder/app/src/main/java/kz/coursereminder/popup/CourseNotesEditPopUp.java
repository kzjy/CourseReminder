package kz.coursereminder.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.display.CourseActivity;

public class CourseNotesEditPopUp extends PopUp{

    /**
     * Constructor of the popup
     * @param context context of the activity
     * @param controller controller of the activity
     */
    public CourseNotesEditPopUp(Context context, CourseActivityController controller) {
        super(context, controller);
    }

    /**
     * Inflate popup with a layout
     */
    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_course_notes_edit);
        String notes = controller.getCurrentCourse().getNotes();
        ((EditText) dialog.findViewById(R.id.course_notes_edit)).setText(notes);
    }

    /**
     * Activate the no button
     */
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

    /**
     * Activate the yes button
     *
     */
    @Override
    void yesButtonListener() {
        Button yes = dialog.findViewById(R.id.course_notes_save);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = getNotesEdit();
                controller.saveEdits(s, "notes");
                dismiss();
                ((CourseActivity) context).displayCourseInfo();
            }
        });
    }

    /**
     * Get the string input of new edit
     * @return the edited input
     */
    private String getNotesEdit() {
        return ((EditText) dialog.findViewById(R.id.course_notes_edit)).getText().toString();
    }

}
