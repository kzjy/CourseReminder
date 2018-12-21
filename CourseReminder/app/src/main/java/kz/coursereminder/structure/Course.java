package kz.coursereminder.structure;

import java.io.Serializable;
import java.util.ArrayList;

import kz.coursereminder.R;

public class Course implements Serializable {

    /**
     * ArrayList for keeping tack of assignments and tests
     */
    private ArrayList<Assignment> assignments = new ArrayList<>();
    /**
     * Arraylist for keeping tack of grades
     */
    private ArrayList<Grade> grades = new ArrayList<>();
    /**
     * Short notes for the course
     */
    private String notes;
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
        selectImage(image);
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public String getInfo() {
        return info;
    }

    public String getNotes() {
        return notes;
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
