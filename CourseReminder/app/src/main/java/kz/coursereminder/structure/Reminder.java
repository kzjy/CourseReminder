package kz.coursereminder.structure;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class Reminder implements Serializable, Comparable<Reminder> {

    /**
     * Name of reminder
     */
    private String name;
    /**
     * date and time of reminder
     */
    private Calendar dateTime;
    /**
     * Grade if it is an assignment or test
     */
    private Grade grade;
    /**
     * Time to notify the user
     */
    private Calendar notificationTime;
    /**
     * Type of reminder
     */
    private String type;

    public Reminder(String name, Calendar dateTime, String type, Calendar notificationTime) {
        this.name = name;
        this.dateTime = dateTime;
        this.type = type;
        this.notificationTime = notificationTime;
    }

    public Calendar getNotificationTime() {
        return notificationTime;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }

    public String getNameDisplayString() {
       return type + ": " + name;
    }

    public String getType() {
        return type;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }


    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Override the equals method, equal when name and due date and time are equal
     * @param obj obj to compare
     * @return whehter self and obj are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Reminder)) {
            return false;
        }
        Reminder other = (Reminder) obj;
        return (name.equals(other.name) && dateTime.equals(other.dateTime));
    }

    /**
     * Override the toString
     * @return string of reminder's name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Override compareTo, greater if self.dateTime is greater than o.dateTime
     * @param o reminder to compare with
     * @return whether self or o is greater
     */
    @Override
    public int compareTo(@NonNull Reminder o) {
        return this.dateTime.compareTo(o.dateTime);
    }

    /**
     * Return a string representation of time in dateTime
     * @return string of time in dateTime
     */
    private String getTimeDisplayString() {
        if (dateTime.get(Calendar.MINUTE) < 10) {
            return dateTime.get(Calendar.HOUR_OF_DAY) + " : 0" +
                    dateTime.get(Calendar.MINUTE);
        }
        return dateTime.get(Calendar.HOUR_OF_DAY) + " : " + dateTime.get(Calendar.MINUTE);
    }

    /**
     * Return a string representation of date in dateTime
     * @return string of date in dateTime
     */
    private String getDateDisplayString() {
        String month = new DateFormatSymbols().getMonths()[dateTime.get(Calendar.MONTH)];
        return month + " " + dateTime.get(Calendar.DATE) + ", " + dateTime.get(Calendar.YEAR);
    }

    /**
     * Get dateTime string display
     * @return a string representation of dateTime
     */
    public String getDueDateTimeDisplayString() {
        String[] dates = new String[] { "filler", "SUNDAY", "MONDAY", "TUESDAY", //
                "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        return dates[dateTime.get(Calendar.DAY_OF_WEEK)] + " - " + getDateDisplayString() +
                " at " + getTimeDisplayString();
    }

    /**
     * Check if c has the same date as dateTime
     * @param c calendar
     * @return whether it is on the same day
     */
    public boolean getSameDate(Calendar c) {
        return (c.get(Calendar.DATE) == dateTime.get(Calendar.DATE)
                && c.get(Calendar.MONTH) == dateTime.get(Calendar.MONTH)
                && c.get(Calendar.YEAR) == dateTime.get(Calendar.YEAR));
    }
}
