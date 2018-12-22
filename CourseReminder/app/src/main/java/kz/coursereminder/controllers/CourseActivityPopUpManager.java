package kz.coursereminder.controllers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.EditText;

import kz.coursereminder.R;

public class CourseActivityPopUpManager {

    private Context context;
    private View.OnClickListener clickListener;
    /**
     * Arraylist of all dialogs
     * index 0 -> delete course
     * index 1 -> edit course name
     * index 2 -> edit info
     * index 3 -> edit notes
     */
    private Dialog[] popUps = new Dialog[4];

    public CourseActivityPopUpManager(Context context, View.OnClickListener listener) {
        this.clickListener = listener;
        this.context = context;
        for (int i = 0; i < 4; i++) {
            popUps[i] = new Dialog(context);
        }
    }

    public void inflateDialogs() {
        popUps[0].setContentView(R.layout.popup_delete_course);
        popUps[1].setContentView(R.layout.popup_course_name_edit);
        popUps[2].setContentView(R.layout.popup_course_info_edit);
        popUps[3].setContentView(R.layout.popup_course_notes_edit);
    }

    /**
     * Dismiss popup at index
     *
     * @param index index of popup in arraylist
     */
    public void dismissPopUp(int index) {
        popUps[index].dismiss();
    }

    /**
     * Show pop up for delete course confirmation
     */
    public void showDeletePopUp() {
        Dialog confirm = popUps[0];
        (confirm.findViewById(R.id.popup_delete_no)).setOnClickListener(clickListener);
        (confirm.findViewById(R.id.popup_delete_yes)).setOnClickListener(clickListener);
        setPopUpTransparent(confirm);
    }

    public void showCourseNameEditPopUp(String name) {
        Dialog courseEdit = popUps[1];
        (courseEdit.findViewById(R.id.course_name_edit_discard)).setOnClickListener(clickListener);
        (courseEdit.findViewById(R.id.course_name_edit_save)).setOnClickListener(clickListener);
        ((EditText) courseEdit.findViewById(R.id.course_name_edit)).setText(name);
        setPopUpTransparent(courseEdit);
    }

    public void showCourseInfoPopUp(String info) {
        Dialog courseInfo = popUps[2];
        (courseInfo.findViewById(R.id.course_info_discard)).setOnClickListener(clickListener);
        (courseInfo.findViewById(R.id.course_info_save)).setOnClickListener(clickListener);
        ((EditText) courseInfo.findViewById(R.id.course_info_edit)).setText(info);
        setPopUpTransparent(courseInfo);
    }

    public void showCourseNotesPopUp(String notes) {
        Dialog courseNotes = popUps[3];
        (courseNotes.findViewById(R.id.course_notes_discard)).setOnClickListener(clickListener);
        (courseNotes.findViewById(R.id.course_notes_save)).setOnClickListener(clickListener);
        ((EditText) courseNotes.findViewById(R.id.course_notes_edit)).setText(notes);
        setPopUpTransparent(courseNotes);
    }

    private void setPopUpTransparent(Dialog dialog) {
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        dialog.show();
    }

    public String getDialogInput(String name) {
        switch (name) {
            case ("name"):
                return ((EditText) popUps[1].findViewById(R.id.course_name_edit)).getText().toString();
            case ("info"):
                return ((EditText) popUps[2].findViewById(R.id.course_info_edit)).getText().toString();
            default:
                return ((EditText) popUps[3].findViewById(R.id.course_notes_edit)).getText().toString();
        }
    }
}
