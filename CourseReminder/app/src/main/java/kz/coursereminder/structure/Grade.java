package kz.coursereminder.structure;

import java.io.Serializable;

class Grade implements Serializable{

    private Task task;
    private int grade;
    private int weight;

    public Grade(Task task, int grade, int weight) {
        this.grade = grade;
        this.weight = weight;

    }

    public int getGrade() {
        return grade;
    }

    public int getWeight() {
        return weight;
    }
}
