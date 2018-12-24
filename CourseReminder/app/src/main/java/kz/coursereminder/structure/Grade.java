package kz.coursereminder.structure;

import java.io.Serializable;

public class Grade implements Serializable{

    private int grade;
    private int total;
    private int weight;

    public Grade(int grade, int total, int weight) {
        this.grade = grade;
        this.weight = weight;
        this.total = total;
    }
    public int getGrade() {
        return grade;
    }

    public int getTotal() {
        return total;
    }

    public int getWeight() {
        return weight;
    }
}
