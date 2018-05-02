package cc.istarx.mockitodemo;

public interface StudentDatabaseHelper {

    public Student queryStudentWithId(int id);

    public void updateNewScoreWithId(int id, int score);
}
