package cn.istarx.uiautomator;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String PKG = "cn.istarx.uiautomator";
    private static final String EMAIL = "istarx@163.com";
    private static final String PASSWORD = "istarx.cc";
    private static final int TIMEOUT = 3000;
    private UiDevice mDevice;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void loginTest() {
        mDevice.findObject(By.res(PKG + ":id/email")).setText(EMAIL);
        mDevice.findObject(By.res(PKG + ":id/password")).setText(PASSWORD);
        mDevice.findObject(By.res(PKG + ":id/login")).clickAndWait(Until.newWindow(), TIMEOUT);

        String expectedString = String.format("Email is: %s\nPassword is: %s", EMAIL, PASSWORD);
        String actualString = mDevice.findObject(By.res(PKG + ":id/intent_content")).getText();

        assertEquals("Intent message error...", expectedString, actualString);
    }
}
