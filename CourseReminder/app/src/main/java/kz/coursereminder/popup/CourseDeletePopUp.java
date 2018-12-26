package kz.coursereminder.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.display.CourseActivity;

public class CourseDeletePopUp extends PopUp {

    /**
     * Constructor
     * @param context context of the activity
     * @param controller the controller of the activity
     */
    public CourseDeletePopUp(Context context, CourseActivityController controller) {
        super(context, controller);
    }

    /**
     * Inflate popup with a layout
     */
    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_delete_course);
    }

    /**
     * Activate the no button
     */
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

    /**
     * Activate the yes button
     */
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
}
