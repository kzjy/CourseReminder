package kz.coursereminder.structure;

import java.io.Serializable;

public class Grade implements Serializable{

    private float grade;
    private float total;
    private float weight;

    public Grade(float grade, float total, float weight) {
        this.grade = grade;
        this.weight = weight;
        this.total = total;
    }
    public float getGrade() {
        return grade;
    }

    public float getTotal() {
        return total;
    }

    public float getWeight() {
        return weight;
    }
}
