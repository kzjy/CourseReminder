package kz.coursereminder.structure;

import java.io.Serializable;
import java.util.Calendar;

public class ReminderManager implements Serializable {

    /**
     * Activar reminders that will go off
     */
    private Reminder[] activeReminders = new Reminder[40];

    public Reminder[] getActiveReminders() {
        return activeReminders;
    }

    /**
     * Add a reminder to activereminder
     * @param r reminder to be added
     * @return index of activereminders added, -1 if not added
     */
    public int addReminder(Reminder r) {
        for (int i = 0; i < activeReminders.length; i++) {
            if (activeReminders[i] == null) {
                activeReminders[i] = r;
                return i;
            }
        }
        return -1;
    }

    /**
     * Remove a reminder r from active reminder
     * @param r reminder to remove
     * @return the index of removal, -1 if no reminder r is in there
     */
    public int removeReminder(Reminder r) {
        for (int i = 0; i < activeReminders.length; i++) {
            if (r.equals(activeReminders[i])) {
                activeReminders[i] = null;
                return i;
            }
        }
        return -1;
    }

    /**
     * Remove reminder from activereminder once its past notification time
     */
    public void removePastReminder() {
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < activeReminders.length; i++) {
            if (activeReminders[i] != null
                    && activeReminders[i].getNotificationTime().compareTo(c) <= 0) {
                activeReminders[i] = null;
            }
        }
    }

    /**
     * Override to string of remindermanager
     * @return string representation of reminder manager
     */
    @Override
    public String toString() {
        String s = "{";
        for (Reminder r : activeReminders) {
            if (r != null) {
                s += r.getName() + ", ";
            } else {
                s += "null, ";
            }
        }
        return s + "}";
    }
}
