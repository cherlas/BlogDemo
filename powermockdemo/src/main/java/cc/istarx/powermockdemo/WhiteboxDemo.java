package cc.istarx.powermockdemo;

public class WhiteboxDemo {
    private static WhiteboxDemo demo;
    private String name;

    private WhiteboxDemo() {
    }

    public WhiteboxDemo getInstance() {
        if (demo == null) {
            demo = new WhiteboxDemo();
        }
        return demo;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
