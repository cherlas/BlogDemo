package cc.istarx.powermockdemo;

public class VerifyPrivateAndConstructor {

    public VerifyPrivateAndConstructor(String str) {
        str = "constructor with argument";
    }

    private String privateMethod(String str) {
        return str;
    }

    public String getStr() {
        return privateMethod("test");
    }
}
