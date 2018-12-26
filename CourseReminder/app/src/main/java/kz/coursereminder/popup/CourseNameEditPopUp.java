package kz.coursereminder.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.display.CourseActivity;

public class CourseNameEditPopUp extends PopUp {

    /**
     * Constructor
     * @param context context of the activity
     * @param controller the controller of the activity
     */
    public CourseNameEditPopUp(Context context, CourseActivityController controller) {
        super(context, controller);
    }

    /**
     * Inflate popup with a layout
     */
    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_course_name_edit);
        String name = controller.getCurrentCourse().getName();
        ((EditText) dialog.findViewById(R.id.course_name_edit)).setText(name);
    }

    /**
     * Activate the no button
     */
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

    /**
     * Activate the yes button
     *
     */
    @Override
    void yesButtonListener() {
        Button yes = dialog.findViewById(R.id.course_name_edit_save);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = getNameEdit();
                controller.saveEdits(s, "name");
                dismiss();
                ((CourseActivity) context).displayCourseInfo();
            }
        });

    }

    /**
     * Get new name from input
     * @return edited name
     */
    private String getNameEdit() {
        return ((EditText) dialog.findViewById(R.id.course_name_edit)).getText().toString();

    }

}
