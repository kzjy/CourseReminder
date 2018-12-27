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

    /**
     * Constructor
     * @param context context of the activity
     * @param controller course activity controller
     * @param position position of the hold
     */
    public GradeEditPopUp(Context context, CourseActivityController controller, int position) {
        super(context, controller);
        this.position = position;
    }

    /**
     * Inflate the layout popup
     */
    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_grade_edit);
        setGradeName();
        setPreviousGradeDetail();
    }

    /**
     * Activate no button
     */
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

    /**
     * Activate yes button
     */
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

    /**
     * Set up assignment
     */
    private void setGradeName() {
        TextView textView = dialog.findViewById(R.id.popup_grade_edit_name);
        textView.setText(controller.getCurrentCourse().getCompletedReminders().get(position).getName());
    }

    /**
     * Set up prev grade for easier editing
     */
    private void setPreviousGradeDetail() {
        EditText prevPointReceived = dialog.findViewById(R.id.popup_grade_point_received_edit);
        prevPointReceived.setText(String.valueOf(controller.getCurrentCourse().
                getCompletedReminders().get(position).getGrade().getGrade()));
        EditText prevPointTotal = dialog.findViewById(R.id.popup_grade_point_total_edit);
        prevPointTotal.setText(String.valueOf(controller.getCurrentCourse().
                getCompletedReminders().get(position).getGrade().getTotal()));
        EditText prevWeight = dialog.findViewById(R.id.popup_grade_weight_edit);
        prevWeight.setText(String.valueOf(controller.getCurrentCourse().
                getCompletedReminders().get(position).getGrade().getWeight()));
    }

    /**
     * Add a grade to the selected assignment at positionSelected
     *
     * @return whether grade addition is successful
     */
    private boolean editGrade() {
        Float[] integerInput = getValidInput();
        if (integerInput != null) {
            Grade grade = new Grade(integerInput[0], integerInput[1],  integerInput[2]);
            controller.editGrade(grade, position);
            return true;
        }
        return false;
    }

    /**
     * Get the edittext inputs for grade
     * @return an array of ints for grade
     */
    private Float[] getValidInput() {
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
        return new Float[]{Float.valueOf(stringInput[0]), Float.valueOf(stringInput[1]),
                Float.valueOf(stringInput[2])};
    }

    /**
     * Make Toast invalid settings
     */
    private void makeToastInvalidInput() {
        Toast.makeText(context, "Invalid grade inputs ", Toast.LENGTH_SHORT).show();
    }
}
