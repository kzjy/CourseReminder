package kz.coursereminder.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.display.CourseActivity;

public class CourseInfoEditPopUp extends PopUp {

    /**
     * Constructor
     * @param context context of the activity
     * @param controller the controller of the activity
     */
    public CourseInfoEditPopUp(Context context, CourseActivityController controller) {
        super(context, controller);
    }

    /**
     * Inflate popup with a layout
     */
    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_course_info_edit);
        String info = controller.getCurrentCourse().getInfo();
        ((EditText) dialog.findViewById(R.id.course_info_edit)).setText(info);
    }
    /**
     * Activate the no button
     */
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
    /**
     * Activate the yes button
     */
    @Override
    void yesButtonListener() {
        Button yes = dialog.findViewById(R.id.course_info_save);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = getInfoEdit();
                controller.saveEdits(s, "info");
                dismiss();
                ((CourseActivity) context).displayCourseInfo();
            }
        });
    }

    /**
     * Get info edit from input
     * @return edited info
     */
    private String getInfoEdit() {
        return ((EditText) dialog.findViewById(R.id.course_info_edit)).getText().toString();
    }

}
