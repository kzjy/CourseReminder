package kz.coursereminder.structure;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.DateFormatSymbols;

public class ReminderDateTime implements Serializable, Comparable<ReminderDateTime> {

    private Integer[] date;
    private Integer[] time;

    public ReminderDateTime(Integer[] date, Integer[] time) {
        this.date = date;
        this.time = time;
    }

    public Integer[] getDate() {
        return this.date;
    }

    public Integer[] getTime() {
       return this.time;
    }

    public String getTimeDisplayString() {
        if (time[1].equals(0)) {
            return time[0] + ": 00";
        }
        return String.valueOf(time[0]) + ": " + String.valueOf(time[1]);
    }

    public String getDateDisplayString() {
        String month = new DateFormatSymbols().getMonths()[date[1]];
        return month + " " + date[0] + ", " + date[2];
    }

    public String getDueDateTimeDisplayString() {
        return getDateDisplayString() + " at " + getTimeDisplayString();
    }

    /**
     * Compare with other for same day
     * @param other other reminderDatetime
     * @return whether it is the same day
     */
    public boolean getSameDate(ReminderDateTime other) {
        for (int i = 0; i < date.length; i ++) {
            if (!date[i].equals(other.date[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(@NonNull ReminderDateTime o) {
        Integer[] otherTime = o.getTime();
        Integer[] otherDate = o.getDate();
        int compareDate = compareDate(otherDate);
        if (compareDate == 0) {
            return compareTime(otherTime);
        }
        return compareDate;
    }

    /**
     * Compare date with otherDate, this.date closer is -1, same day is 0, further is 1
     * @param otherDate other date in integer array
     * @return an int
     */
    private int compareDate(Integer[] otherDate) {
        Integer[] thisDate = getDate();
        if (thisDate[2] > otherDate[2]) {
            return 1;
        } else if (thisDate[2] < otherDate[2]) {
            return -1;
        } else if (thisDate[1] > otherDate[1]) {
            return 1;
        } else if (thisDate[1] < otherDate[1]) {
            return -1;
        } else if (thisDate[0] > otherDate[0]) {
            return 1;
        } else if (thisDate[0] < otherDate[0]) {
            return -1;
        }
        return 0;
    }

    /**
     * Compare time with otherTime, this.time closer is -1, same time is 0, later is 1
     * @param otherTime other time in integer array
     * @return an int
     */
    private int compareTime(Integer[] otherTime) {
        Integer[] thisTime = getTime();
        if (thisTime[0] > otherTime[0]) {
            return 1;
        } else if (thisTime[0] < otherTime[0]) {
            return -1;
        } else if (thisTime[1] > otherTime[1]) {
            return 1;
        }
        return -1;
    }
}
