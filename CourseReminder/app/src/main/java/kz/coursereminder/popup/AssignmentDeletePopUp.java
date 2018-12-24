package kz.coursereminder.popup;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kz.coursereminder.R;
import kz.coursereminder.adapters.CourseAssignmentAdapter;
import kz.coursereminder.controllers.CourseActivityController;

public class AssignmentDeletePopUp extends PopUp {

    int position;
    CourseAssignmentAdapter adapter;

    public AssignmentDeletePopUp(Context context, CourseActivityController controller,
                                 int position, CourseAssignmentAdapter adapter) {
        super(context, controller);
        this.position = position;
        this.adapter = adapter;
    }

    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_assignment_delete);
    }

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

    @Override
    void yesButtonListener() {
        Button yes = dialog.findViewById(R.id.popup_delete_assignment_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.removeAssignment(position);
                adapter.notifyItemRemoved(position);
                dismiss();
            }
        });
    }

    @Override
    Bundle getDialogInput() {
        return null;
    }
}
