package cc.istarx.hamcrestdemo;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Created by cherlas on 2018/2/11.
 */

public class IsExceptedStringMatcher extends BaseMatcher<String> {
    @Factory
    public static <T> Matcher<String> isExceptedString() {
        return new IsExceptedStringMatcher();
    }

    @Override
    public boolean matches(Object item) {
        String str = (String) item;
        if (str != null && str.length() >= 7 && str.startsWith("ham") && str.endsWith("java")) {
            return true;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a string that start with \"ham\" and end with \"java\"");
    }
}