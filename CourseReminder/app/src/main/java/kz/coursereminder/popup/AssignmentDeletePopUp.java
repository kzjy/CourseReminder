package kz.coursereminder.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.display.CourseActivity;

public class AssignmentDeletePopUp extends PopUp {

    /**
     * position of assignment to be removed
     */
    private int position;

    /**
     * Constructor
     * @param context context of the activity
     * @param controller the controller of the activity
     */
    public AssignmentDeletePopUp(Context context, CourseActivityController controller,
                                 int position) {
        super(context, controller);
        this.position = position;
    }

    /**
     * Inflate popup with a layout
     */
    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_assignment_delete);
    }

    /**
     * Activate the no button
     */
    @Override
    void noButtonListener() {
        Button no = dialog.findViewById(R.id.popup_delete_assignment_no);
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
        Button yes = dialog.findViewById(R.id.popup_delete_assignment_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.removeAssignment(position);
                ((CourseActivity) context).displayCourseInfo();
                dismiss();
            }
        });
    }

}
