package cn.istarx.mockitodemo;

import android.os.RemoteException;

public class StudentServer {

    private StudentDatabaseHelper databaseHelper;

    public StudentServer(StudentDatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public boolean examination(int id, int score) {
        Student student = databaseHelper.queryStudentWithId(id);
        if (student == null) {
            return false;
        }

        if (score > student.getScore()) {
            try {
                databaseHelper.updateNewScoreWithId(id, score);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
