package kz.coursereminder.popup;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import kz.coursereminder.R;
import kz.coursereminder.controllers.CourseActivityController;
import kz.coursereminder.display.CourseActivity;
import kz.coursereminder.structure.Course;
import kz.coursereminder.structure.Grade;
import kz.coursereminder.structure.Reminder;


public class GradeAdditionPopUp extends PopUp {

    private int positionSelected;

    public GradeAdditionPopUp(Context context, CourseActivityController controller) {
        super(context, controller);
    }

    @Override
    void inflatePopUp() {
        dialog.setContentView(R.layout.popup_grade_additon);
    }

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

    @Override
    void yesButtonListener() {
        Button yes = dialog.findViewById(R.id.popup_grade_save);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGrade();
                ((CourseActivity) context).notifyAdapterChange();
                ((CourseActivity) context).displayCourseInfo();
                dismiss();
            }
        });

    }

    private void addGrade() {
        Grade grade = new Grade(getPointReceived(), getPointTotal(), getWeight());
        controller.getCurrentCourse().setReminderGrade(grade, positionSelected);
        controller.save();
    }

    private Integer getPointReceived() {
        String pointReceived = ((EditText) dialog.findViewById(R.id.popup_grade_point_received)).
                getText().toString();
        return Integer.valueOf(pointReceived.trim());
    }

    private Integer getPointTotal() {
        String pointTotal = ((EditText) dialog.findViewById(R.id.popup_grade_point_total)).
                getText().toString();
        return Integer.valueOf(pointTotal);
    }

    private Integer getWeight() {
        String weight = ((EditText) dialog.findViewById(R.id.popup_grade_weight)).
                getText().toString();
        return Integer.valueOf(weight);
    }

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

    @Override
    public void showPopUp() {
        super.showPopUp();
        populateSpinner();
        spinnerItemSelectListener();
    }

    @Override
    Bundle getDialogInput() {
        return new Bundle();
    }
}
