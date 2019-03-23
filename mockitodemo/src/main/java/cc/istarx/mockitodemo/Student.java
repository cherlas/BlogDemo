package cn.istarx.mockitodemo;

public class Student {
    public String name;
    public int studentId;
    public int score;

    public Student(String name, int id, int score) {
        this.name = name;
        this.studentId = id;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
