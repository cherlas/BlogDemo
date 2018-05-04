package cc.istarx.mockitodemo;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.verification.Timeout;
import org.mockito.verification.VerificationMode;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

public class MockDemoUnitTest {

    @Test(expected = IndexOutOfBoundsException.class)
    public void stubbingTest() {
        // mock 一个对象
        ArrayList<String> mockList = mock(ArrayList.class);

        // 插桩
        when(mockList.get(0)).thenReturn("mock stub test");

        //验证
        assertEquals("mock stub test", mockList.get(0));
        assertNull(mockList.get(1)); // 因为 mockList.get(1) 方法没有插桩，所以返回 null；

        // spy 一个对象
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> spyList = spy(arrayList);

        spyList.add("one");
        spyList.add("two");

        assertEquals("one", spyList.get(0));
        // 下面语句会输出 two.
        System.out.println(spyList.get(1));
        // spyList.get(2) 会抛出 IndexOutOfBoundsException
        assertNull(spyList.get(2));
    }

    @Test
    public void argMatcherTest() {
        ArrayList<String> mockList = mock(ArrayList.class);

        when(mockList.get(anyInt())).thenReturn("arg matcher0");
        assertEquals("arg matcher0", mockList.get(0));
        assertEquals("arg matcher0", mockList.get(4));

        when(mockList.get(intThat(i -> i > 5))).thenReturn("big than 5");
        assertEquals("big than 5", mockList.get(999));

        when(mockList.indexOf(argThat((String str) -> str.length() > 5))).thenReturn(5);
        assertEquals(5, mockList.indexOf("arg matcher test"));
    }

    @Test
    public void stubVoidMethodTest() {
        StudentDatabaseHelper helper = mock(StudentDatabaseHelper.class);
        Object object = new Object();

        // 执行该 stub 的方法时不做任何事
        doNothing().when(helper).updateNewScoreWithId(anyInt(), anyInt());

        // 执行该 stub 的方法时执行该方法真正的方法体，由于本方法是 abstract 的，所以此处在编译时会报错
        // doCallRealMethod().when(helper).updateNewScoreWithId(anyInt(), anyInt());

        // 执行该 stub 的方法时抛出一个异常
        doThrow(RuntimeException.class).when(helper).updateNewScoreWithId(anyInt(), anyInt());

        // 执行该 stub 的方法时执行特定的操作
        doAnswer(invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments.length == 2) {
                return arguments[0];
            } else {
                return arguments[1];
            }
        }).when(helper).updateNewScoreWithId(anyInt(), anyInt());
    }

    @Test
    public void consecutiveStubTest() {
        Student mockStudent = mock(Student.class);

        when(mockStudent.getName())
                .thenReturn("name1")
                .thenReturn("name2")
                .thenReturn("name3")
                .thenReturn("name4");

        assertEquals("name1", mockStudent.getName());
        assertEquals("name2", mockStudent.getName());
        assertEquals("name3", mockStudent.getName());
        assertEquals("name4", mockStudent.getName());
    }

    @Test
    public void verifyTest() {
        Student mockStudent = mock(Student.class);

        // stub 方法
        when(mockStudent.getName()).thenReturn("name1");
        doNothing().when(mockStudent).setScore(99);

        // 调用 stub 之后的方法
        mockStudent.setScore(99);
        mockStudent.getName();

        // 验证
        verify(mockStudent).setScore(99);
        verify(mockStudent).getName();
    }

    @Test
    public void verifyCallTimesTest() {
        Student mockStudent = mock(Student.class);

        mockStudent.setScore(1);

        mockStudent.setScore(2);
        mockStudent.setScore(2);

        verify(mockStudent).setScore(1);
        verify(mockStudent, times(1)).setScore(1);
        verify(mockStudent, atLeast(1)).setScore(1);

        verify(mockStudent, times(2)).setScore(2);
        verify(mockStudent, atMost(5)).setScore(2);
        verify(mockStudent, atLeastOnce()).setScore(2);

        verify(mockStudent, never()).setScore(4);
    }

    @Test
    public void verifyOrderTest() {
        Student singleMockStudent = mock(Student.class);

        singleMockStudent.setScore(1);
        singleMockStudent.setScore(2);

        // 实例化一个 inOrder 对象
        InOrder inOrder = Mockito.inOrder(singleMockStudent);

        // 验证参数为 1 的方法先于参数为 2 的方法运行
        inOrder.verify(singleMockStudent).setScore(1);
        inOrder.verify(singleMockStudent).setScore(2);

        Student firstStudent = mock(Student.class);
        Student secondStudent = mock(Student.class);

        firstStudent.setScore(3);
        secondStudent.setScore(4);

        // 实例化 inOrder 对象，参数为需要进行验证调用次序的 mock 对象
        inOrder = Mockito.inOrder(firstStudent, secondStudent);

        // verify
        inOrder.verify(firstStudent).setScore(3);
        inOrder.verify(secondStudent).setScore(4);
    }

    @Test
    public void verifyInteractionTest() {
        Student firstStudent = mock(Student.class);
        Student secondStudent = mock(Student.class);

        firstStudent.setScore(1);

        // 正常的验证行为
        verify(firstStudent).setScore(1);
        verify(secondStudent, never()).setScore(anyInt());

        // 交互验证 secondStudent 从未进行交互
        verifyZeroInteractions(secondStudent);
    }

    @Test(expected = NoInteractionsWanted.class)
    public void noMoreInteractionsTest() {
        Student student = mock(Student.class);

        student.setScore(1);

        // 验证失败，抛出 NoInteractionsWanted 异常
        verifyNoMoreInteractions(student);
    }

    @Test
    public void argumentCaptureTest() {
        Student student = mock(Student.class);
        // 实例化 ArgumentCaptor 对象
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

        student.setScore(100);

        // 先验证再捕捉参数
        verify(student).setScore(captor.capture());
        assertEquals(100, (int) captor.getValue());
    }

    @Test
    public void setDefaultReturnValues() {
        Student student = mock(Student.class);

        // 利用 thenAnswer() 设置默认返回值
        when(student.getName()).thenAnswer(invocation -> "default value");

        assertEquals("default value", student.getName());
    }

    @Test
    public void serializableMockTest() {
        Student student = mock(Student.class, withSettings().serializable());
        assertNotNull(student);
    }

    @Test
    public void resetMockTest() {
        Student student = mock(Student.class);
        doNothing().when(student).setScore(99);
        student.setScore(99);
        reset(student);
    }

    @Test
    public void timeoutTest() {
        TimeoutDemo demo = mock(TimeoutDemo.class);

        doCallRealMethod().when(demo).timeoutMethod();
        demo.timeoutMethod();


        // 延迟 200ms 再验证该方法是否执行过
        verify(demo,timeout(200)).timeoutMethod();
        // 等价与下面这句
        verify(demo, timeout(200).times(1)).timeoutMethod();

        // 自定义验证模式
        verify(demo,new Timeout(200, new VerificationMode() {
            @Override
            public void verify(VerificationData data) {
                // custom verify mode
            }

            @Override
            public VerificationMode description(String description) {
                return null;
            }
        })).timeoutMethod();
    }
}
