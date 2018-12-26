package kz.coursereminder.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.display.CourseActivity;
import kz.coursereminder.structure.Grade;

public class GradeEditPopUp extends PopUp {

    private int position;

    public GradeEditPopUp(Context context, CourseActivityController controller, int position) {
        super(context, controller);
        this.position = position;
    }

    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_grade_edit);
        setGradeName();
    }

    @Override
    void noButtonListener() {
        Button no = dialog.findViewById(R.id.popup_grade_discard_edit);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    void yesButtonListener() {
        Button yes = dialog.findViewById(R.id.popup_grade_save_edit);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean editSuccessful = editGrade();
                if (editSuccessful) {
                    ((CourseActivity) context).displayCourseInfo();
                    dismiss();
                } else {
                    makeToastInvalidInput();
                }
            }
        });
    }

    private void setGradeName() {
        TextView textView = dialog.findViewById(R.id.popup_grade_edit_name);
        textView.setText(controller.getCurrentCourse().getCompletedReminders().get(position).getName());
    }


    /**
     * Add a grade to the selected assignment at positionSelected
     *
     * @return whether grade addition is successful
     */
    private boolean editGrade() {
        Integer[] integerInput = getValidInput();
        if (integerInput != null) {
            Grade grade = new Grade((float) integerInput[0], (float) integerInput[1], (float) integerInput[2]);
            controller.editGrade(grade, position);
            return true;
        }
        return false;
    }

    /**
     * Get the edittext inputs for grade
     * @return an array of ints for grade
     */
    private Integer[] getValidInput() {
        String[] stringInput = new String[3];
        stringInput[0] = ((EditText) dialog.findViewById(R.id.popup_grade_point_received_edit)).
                getText().toString().trim();
        stringInput[1] = ((EditText) dialog.findViewById(R.id.popup_grade_point_total_edit)).
                getText().toString().trim();
        stringInput[2] = ((EditText) dialog.findViewById(R.id.popup_grade_weight_edit)).
                getText().toString().trim();
        if (!controller.checkGradeInput(stringInput)) {
            return null;
        }
        return new Integer[]{Integer.valueOf(stringInput[0]), Integer.valueOf(stringInput[1]),
                Integer.valueOf(stringInput[2])};
    }


    private void makeToastInvalidInput() {
        Toast.makeText(context, "Invalid grade inputs ", Toast.LENGTH_SHORT).show();
    }
}
