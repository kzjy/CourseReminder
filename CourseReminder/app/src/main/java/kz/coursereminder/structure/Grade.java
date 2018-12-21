package kz.coursereminder.structure;

import java.io.Serializable;

class Grade implements Serializable{

    private String assignment;
    private int grade;
    private int weight;

    public Grade(String assignment, int grade, int weight) {
        this.assignment = assignment;
        this.grade = grade;
        this.weight = weight;
    }

    public String getAssignment() {
        return assignment;
    }

    public int getGrade() {
        return grade;
    }

    public int getWeight() {
        return weight;
    }
}
