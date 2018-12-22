package kz.coursereminder.popup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public abstract class PopUp {

    Context context;
    Dialog dialog;

    /**
     * Display popup and set it transparent
     * @param dialog dialog to pop up
     */
    private void setPopUpTransparent(Dialog dialog) {
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        dialog.show();
    }

    void dismiss() {
        dialog.dismiss();
    }

    public void showPopUp() {
        inflatePopUp();
        noButtonListener();
        yesButtonListener();
        setPopUpTransparent(dialog);
    }

    abstract void inflatePopUp();

    abstract void noButtonListener();

    abstract void yesButtonListener();

    abstract Bundle getDialogInput();
}
