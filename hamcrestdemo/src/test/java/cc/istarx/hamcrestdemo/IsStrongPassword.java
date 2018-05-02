package cc.istarx.hamcrestdemo;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by cherlas on 2018/2/11.
 */

public class IsStrongPassword extends TypeSafeMatcher<String> {
    @Factory
    public static <T> Matcher<String> isStrongPassword() {
        return new IsStrongPassword();
    }

    @Override
    protected boolean matchesSafely(String item) {
        if (containsSymbol(item) && containsDigit(item) && item.length() >= 6) {
            return true;
        }
        return false;
    }

    private boolean containsDigit(String password) {
        for (char ch : password.toCharArray()) {
            if (Character.isDigit(ch)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSymbol(String password) {
        for (char ch : password.toCharArray()) {
            if ((int) ch >= 33 && (int) ch <= 47) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a string which a strong password");
    }
}
