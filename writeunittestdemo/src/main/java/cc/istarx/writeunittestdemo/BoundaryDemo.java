package cc.istarx.writeunittestdemo;

public class BoundaryDemo {

    public static int getScore(int num) {
        if (num <= 0) {
            return 0;
        } else if (num >= 5 && num <= 10) {
            return 5;
        } else {
            return num;
        }
    }
}
