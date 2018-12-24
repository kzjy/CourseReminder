package kz.coursereminder.structure;

import java.io.Serializable;
import java.util.ArrayList;

import kz.coursereminder.R;

public class Course implements Serializable {

    /**
     * ArrayList for keeping tack of reminders and tests
     */
    private ArrayList<Reminder> reminders = new ArrayList<>();
    /**
     * Arraylist for completed reminders
     */
    private ArrayList<Reminder> completedReminders = new ArrayList<>();
    /**
     * Short notes for the course
     */
    private String notes = "Click and Hold to edit";
    /**
     * Name of the course
     */
    private String name;
    /**
     * Course info
     */
    private String info;
    /**
     * Icon of the course
     */
    private int image;

    public Course(String name, String info, int image) {
        this.name = name;
        this.info = info;
        if (info.equals("")) {
            this.info = "Click and Hold to edit";
        }
        selectImage(image);
    }

    public ArrayList<Reminder> getReminders() {
        return reminders;
    }

    public ArrayList<Reminder> getCompletedReminders() {
        return completedReminders;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public String getInfo() {
        return info;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void addTask(Reminder reminder) {
        reminders.add(reminder);
    }

    public void removeTask(int position) {
        reminders.remove(position);
    }

    public void setReminderGrade(Grade grade, int position) {
        Reminder temp = reminders.get(position);
        reminders.remove(temp);
        temp.setGrade(grade);
        completedReminders.add(temp);
    }

    public float calculateAverage() {
        if (completedReminders.isEmpty()) {
            return 0;
        }
        int totalWeight = 0;
        float average = 0;
        for (Reminder r: completedReminders) {
            totalWeight += r.getGrade().getWeight();
        }
        for (Reminder r: completedReminders) {
            average += ((float) r.getGrade().getGrade()/ r.getGrade().getTotal()) *
                    ((float) r.getGrade().getWeight()/ totalWeight);
        }
        return average;
    }

    /**
     * Choose an icon based on image
     * @param image the icon to be added
     */
    private void selectImage(int image) {
        switch (image) {
            case 0:
                this.image = R.drawable.course_icoin_add;
                break;
            case 1:
                this.image = R.drawable.course_icon_art;
                break;
            case 2:
                this.image = R.drawable.course_icon_biology;
                break;
            case 3:
                this.image = R.drawable.course_icon_chem;
                break;
            case 4:
                this.image = R.drawable.course_icon_cs;
                break;
            case 5:
                this.image = R.drawable.course_icon_econ;
                break;
            case 6:
                this.image = R.drawable.course_icon_eng;
                break;
            case 7:
                this.image = R.drawable.course_icon_film;
                break;
            case 8:
                this.image = R.drawable.course_icon_lit;
                break;
            case 9:
                this.image = R.drawable.course_icon_math;
                break;
            case 10:
                this.image = R.drawable.course_icon_music;
                break;
            case 11:
                this.image = R.drawable.course_icon_physics;
                break;
            case 12:
                this.image = R.drawable.course_icon_stats;
                break;
            default:
                this.image = R.drawable.course_icoin_add;
        }
    }
}
