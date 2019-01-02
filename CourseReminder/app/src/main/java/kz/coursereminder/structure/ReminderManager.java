package kz.coursereminder.structure;

import java.io.Serializable;

public class ReminderManager implements Serializable {

    private Reminder[] activeReminders = new Reminder[40];

    public int addReminder(Reminder r) {
        for (int i = 0; i < activeReminders.length; i++) {
            if (activeReminders[i] == null) {
                activeReminders[i] = r;
                return i;
            }
        }
        return -1;
    }

    public void removeReminder(Reminder r) {
        for (int i = 0; i < activeReminders.length; i++) {
            if (r.equals(activeReminders[i])) {
                activeReminders[i] = null;
            }
        }
    }
}
