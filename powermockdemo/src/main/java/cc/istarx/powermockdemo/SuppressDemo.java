package cn.istarx.powermockdemo;

public class SuppressDemo {
    public String firstName = "test";
    public String secondName;
    public static int id;

    static {
        id = 123;
    }

    public SuppressDemo(String secondName) {
        this.secondName = secondName;
    }

    public void setSecondName (String secondName) {
        this.secondName = secondName;
    }

    public String updateSecondName (String name) {
        setSecondName(name);
        return this.secondName;
    }

    public String getFirstName() {
        return firstName;
    }
}
