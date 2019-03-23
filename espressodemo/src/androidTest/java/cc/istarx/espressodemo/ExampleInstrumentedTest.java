package cn.istarx.espressodemo;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.matcher.BundleMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String EMAIL = "istarx@163.com";
    private static final String PASSWORD = "istarx.cc";

    // @Rule
    // public ActivityTestRule<EspressoDemoActivity> rule = new ActivityTestRule<>(EspressoDemoActivity.class);
    @Rule
    public IntentsTestRule<EspressoDemoActivity> intentsTestRule =
            new IntentsTestRule<>(EspressoDemoActivity.class);


    public static Matcher<Object> mapValueMatcher(final String expectedText) {
        checkNotNull(expectedText);
        return new BoundedMatcher<Object, Map>(Map.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with item content");
            }

            @Override
            protected boolean matchesSafely(Map item) {
                return hasEntry(equalTo("espresso"), is(expectedText)).matches(item);
            }
        };
    }

    @Test
    public void espressoTestDemo() {
        // onView(withId(R.id.text_view))
        //        .perform(scrollTo(), click(), typeText("Test Text"), closeSoftKeyboard())
        //        .check(matches(allOf(isDisplayed(), withText("Expected Text"))));

        onView(withId(R.id.email)).perform(typeText(EMAIL));
        onView(withId(R.id.password)).perform(typeText(PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());

        String expectedString = String.format("Email is: %s\nPassword is: %s", EMAIL, PASSWORD);
        onView(withId(R.id.intent_content)).check(matches(allOf(isDisplayed(), withText(expectedString))));
    }

    @Test
    public void onDataTest() {
        // onData(allOf(is(instanceOf(Map.class)), hasEntry(equalTo("espresso"), is("fast")))).perform(click());
    }

    @Test
    public void espressoIntentTest() {
        onView(withId(R.id.email)).perform(typeText(EMAIL));
        onView(withId(R.id.password)).perform(typeText(PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());

        intended(allOf(
                toPackage("cn.istarx.espressodemo"),
                not(hasAction("cn.istarx.test.action")),
                hasExtras(allOf(
                        hasEntry(equalTo("email"), equalTo(EMAIL)),
                        hasEntry(equalTo("password"), equalTo(PASSWORD))
                ))));
    }

    @Test
    public void intendingTest() {
        Intent resultData = new Intent();
        String testStr = "Intending test string for test...";
        resultData.putExtra("test", testStr);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        intending(toPackage("cn.istarx.espressodemo")).respondWith(result);

        onView(withId(R.id.intending_button)).perform(click());
        onView(withId(R.id.result_text)).check(matches(allOf(isDisplayed(),withText(testStr))));
    }
}
