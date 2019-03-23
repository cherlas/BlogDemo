package cn.istarx.powermockdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareEverythingForTest;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Field;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.internal.verification.VerificationModeFactory.atLeast;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberMatcher.constructor;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
//@PrepareForTest()
@PowerMockIgnore({"cn.istarx.*","android.*"})
// 屏蔽静态初始化块
@SuppressStaticInitializationFor("cn.istarx.powermockdemo.SuppressDemo")
public class ExamplePowerMockTest {

    @Test
    @PrepareOnlyThisForTest()
    @PrepareEverythingForTest()
    public void annotationTest() {
    }

    @Test
    public void WhiteBoxTest() throws Exception {
        String name = "whitebox";

        // 不执行构造函数而实例化
        WhiteboxDemo demo = Whitebox.newInstance(WhiteboxDemo.class);

        // 静态私有属性的赋值
        Whitebox.setInternalState(WhiteboxDemo.class, "demo", demo);
        // 非静态私有属性的赋值
        Whitebox.setInternalState(demo, "name", name);
        Field field = Whitebox.getField(WhiteboxDemo.class, "name");
        assertEquals(name, field.get(demo));

        // 执行私有的构建函数
        WhiteboxDemo demo1 = Whitebox.invokeConstructor(WhiteboxDemo.class);
        assertNotNull(demo1);

        // 执行私有方法
        Whitebox.invokeMethod(demo, "setName", "setName");
        assertEquals("setName", Whitebox.getInternalState(demo, "name")); // 获取私有变量
    }

    @Test
    public void suppressTest() {
        // 屏蔽方法的执行
        suppress(method(SuppressDemo.class, "setSecondName", String.class));
        // 屏蔽构造函数，
        suppress(constructor(SuppressDemo.class, String.class));
        // 屏蔽变量
        suppress(field(SuppressDemo.class, "firstName"));

        // 屏蔽构造函数之后可以利用 WhiteBox.newInstance() 方法实例化参数
        SuppressDemo demo = Whitebox.newInstance(SuppressDemo.class);
        // firstName 变量被屏蔽，下面这句断言位真
        assertNull(demo.getFirstName());
        // 静态初始化块被屏蔽，下面这句断言位真
        assertEquals(0, SuppressDemo.id);
        // 对象成员变量都是默认值
        assertNull(demo.secondName);
        // 由于 setSecondName() 方法被屏蔽，所以 secondName 不会被更新
        assertNull(demo.updateSecondName("suppress"));
    }

    @Test
    @PrepareForTest(WithStaticMethod.class)
    public void mockStaticMethodTest() {
        PowerMockito.mockStatic(WithStaticMethod.class);
        when(WithStaticMethod.getNum()).thenReturn(11);
        when(WithStaticMethod.getNumWithNum(9)).thenReturn(19);

        assertEquals(11, WithStaticMethod.getNum());
        assertEquals(19, WithStaticMethod.getNumWithNum(9));

        PowerMockito.verifyStatic(WithStaticMethod.class, times(1));
        WithStaticMethod.getNum();
        PowerMockito.verifyStatic(WithStaticMethod.class, atLeast(1));
        WithStaticMethod.getNumWithNum(eq(9));
    }

    @Test
    public void verifyPrivateMethod() throws Exception {
        VerifyPrivateAndConstructor pr = mock(VerifyPrivateAndConstructor.class);

        when(pr.getStr()).thenCallRealMethod();
        pr.getStr();

        PowerMockito.verifyPrivate(pr, times(1)).invoke("privateMethod", "test");
        pr.getStr();
    }

    @Test(expected = IllegalArgumentException.class)
    @PrepareForTest(VerifyPrivateAndConstructor.class)
    public void mockConstructorTest() throws Exception {

        whenNew(VerifyPrivateAndConstructor.class).withAnyArguments().thenThrow(new IllegalArgumentException("error message"));
        new VerifyPrivateAndConstructor("test");
    }
}