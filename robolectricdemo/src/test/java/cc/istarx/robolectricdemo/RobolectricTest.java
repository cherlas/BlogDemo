package cn.istarx.robolectricdemo;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowLooper;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@Config(manifest = "AndroidManifest.xml",
//sdk = 27,
        minSdk = 26,
        maxSdk = 28,
//shadows = ShadowDemo.class,
        libraries = {
                "path/to/library1",
                "path/to/library2"
        })
@RunWith(CustomRobolectricRunner.class)
public class RobolectricTest {
    @Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;
    }

    @Test
    @Config(qualifiers = "zh-rCN"/*，
            application = CustomApplication.class*/)
    public void name() {
    }

    @Test
    public void activitySetupTest() {
        // ActivityController<RobolectricDemoActivity> controller = Robolectric.buildActivity(RobolectricDemoActivity.class);

        RobolectricDemoActivity activity = Robolectric.buildActivity(RobolectricDemoActivity.class)
                .create()
                .get();

        assertNotNull(activity);
    }

    @Test
    public void activityLifeCycleTest() {
        ActivityController<RobolectricDemoActivity> controller = Robolectric.buildActivity(RobolectricDemoActivity.class);

        controller.create(); // 相当于 onCreate 周期函数
        // 测试 onCreate 执行之后的状态

        controller.resume(); // 相当于 onResume 周期函数
        // 测试 onResume 执行之后的状态

        controller.pause(); // 相当于 onPause 周期函数
        // 测试 onPause 执行之后的状态

        controller.stop(); // 相当于 onStop 周期函数
        // 测试 onStop 执行之后的状态

        controller.destroy(); // 相当于 onDestroy 周期函数
        // 测试 onDestroy 执行之后的状态
    }

    @Test
    public void jumpActivityTest() {
        RobolectricDemoActivity activity = Robolectric.buildActivity(RobolectricDemoActivity.class)
                .create().get();
        Button button = activity.findViewById(R.id.aty_jump);

        Intent exceptIntent = new Intent(activity, LoginActivity.class);

        button.performClick();
        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();

        assertEquals(exceptIntent.getComponent(), actualIntent.getComponent());
    }

    @Test
    public void dialogTest() {
        RobolectricDemoActivity activity = Robolectric.buildActivity(RobolectricDemoActivity.class)
                .create().get();
        Button button = activity.findViewById(R.id.show_dialog);
        button.performClick();
        ShadowDialog dialog = ShadowApplication.getInstance().getLatestDialog();

        assertNotNull(dialog);
        assertEquals("DialogTest", dialog.getTitle());
    }

    @Test
    public void toastTest() {
        RobolectricDemoActivity activity = Robolectric.buildActivity(RobolectricDemoActivity.class)
                .create().get();
        Button button = activity.findViewById(R.id.toast);
        button.performClick();
        Toast toast = ShadowToast.getLatestToast();

        assertNotNull(toast);
        assertEquals("ToastTest", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void widgetTest() {
        RobolectricDemoActivity activity = Robolectric.buildActivity(RobolectricDemoActivity.class)
                .create().get();
        Button button = activity.findViewById(R.id.toast);

        assertNotNull(button);
        assertTrue(button.isEnabled());
        assertEquals("Toast", button.getText());
    }

    @Test
    public void serviceTest() {
        Intent intent = new Intent();

        MyService service = new MyService("test");
        service.onHandleIntent(intent);

        // verify logical here
    }

    @Test
    public void receiverTest() {
        Intent intent = new Intent("cn.istarx.MyReceiver");
        intent.putExtra("receiver_test", "Receiver Test");

        MyReceiver receiver = new MyReceiver();
        // 验证是否注册
        assertTrue(ShadowApplication.getInstance().hasReceiverForIntent(intent));

        ShadowApplication.getInstance().sendBroadcast(intent);
        assertEquals("Receiver Test", ShadowToast.getTextOfLatestToast());
    }

    /**
     * res/values/strings.xml
     * <string name="login_activity_res">This is Login Activity</string>
     *
     * res/values-zh-rCN/strings.xml
     * <string name="login_activity_res">This is Login Activity with zh-rCN</string>
     */
    @Test
    @Config(qualifiers = "zh-rCN")
    public void resourcesTest() {
        Context context = RuntimeEnvironment.application;
        String str = context.getResources().getString(R.string.login_activity_res);

        assertEquals("This is Login Activity with zh-rCN", str);
    }

    @Test
    public void looperTest() {
        RobolectricLooperDemo.useHandle();

        ShadowLooper looper = ShadowLooper.getShadowMainLooper();
        looper.runOneTask();

        // verify
    }

    @Test
    @Config(shadows = ShadowDemo.class)
    public void customShadowTest() {
        Demo demo = new Demo();
        assertEquals("Shadow Demo implementation", demo.getStr());
    }
}