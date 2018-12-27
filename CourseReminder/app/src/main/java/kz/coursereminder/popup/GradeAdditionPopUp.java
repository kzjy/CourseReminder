package kz.coursereminder.popup;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.display.CourseActivity;
import kz.coursereminder.structure.Grade;
import kz.coursereminder.structure.Reminder;


public class GradeAdditionPopUp extends PopUp {

    private int positionSelected;

    /**
     * Constructor
     *
     * @param context    context of the activity
     * @param controller the controller of the activity
     */
    public GradeAdditionPopUp(Context context, CourseActivityController controller) {
        super(context, controller);
    }

    /**
     * Inflate popup with a layout
     */
    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_grade_additon);
    }

    /**
     * Activate the no button
     */
    @Override
    void noButtonListener() {
        Button no = dialog.findViewById(R.id.popup_grade_discard);
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
        Button yes = dialog.findViewById(R.id.popup_grade_save);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean addSuccesful = addGrade();
                if (addSuccesful) {
                    ((CourseActivity) context).displayCourseInfo();
                    dismiss();
                } else {
                    makeToastInvalidInput();
                }
            }
        });

    }

    /**
     * Add a grade to the selected assignment at positionSelected
     *
     * @return whether grade addition is successful
     */
    private boolean addGrade() {
        Float[] integerInput = getValidInput();
        if (integerInput != null) {
            Grade grade = new Grade(integerInput[0],  integerInput[1],  integerInput[2]);
            controller.addGradeToAssignment(grade, positionSelected);
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
        stringInput[0] = ((EditText) dialog.findViewById(R.id.popup_grade_point_received)).
                getText().toString().trim();
        stringInput[1] = ((EditText) dialog.findViewById(R.id.popup_grade_point_total)).
                getText().toString().trim();
        stringInput[2] = ((EditText) dialog.findViewById(R.id.popup_grade_weight)).
                getText().toString().trim();
        if (!controller.checkGradeInput(stringInput)) {
            return null;
        }
        return new Float[]{Float.valueOf(stringInput[0]), Float.valueOf(stringInput[1]),
                Float.valueOf(stringInput[2])};
    }


    private void makeToastInvalidInput() {
        Toast.makeText(context, "Invalid grade inputs ", Toast.LENGTH_SHORT).show();
    }

    /**
     * Updates the current selected position of the spinner
     */
    private void spinnerItemSelectListener() {
        Spinner spinner = dialog.findViewById(R.id.popup_grade_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                positionSelected = 0;
            }
        });
    }

    /**
     * Set up spinner drop down items
     */
    private void populateSpinner() {
        List<String> reminderList = new ArrayList<>();
        for (Reminder r : controller.getCurrentCourse().getReminders()) {
            reminderList.add(r.getNameDisplayString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, reminderList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = dialog.findViewById(R.id.popup_grade_spinner);
        spinner.setAdapter(adapter);
    }

    /**
     * Show the popup
     */
    @Override
    public void showPopUp() {
        super.showPopUp();
        populateSpinner();
        spinnerItemSelectListener();
    }

}
