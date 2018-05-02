package cc.istarx.hamcrestdemo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static cc.istarx.hamcrestdemo.IsExceptedStringMatcher.isExceptedString;
import static cc.istarx.hamcrestdemo.IsStrongPassword.isStrongPassword;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by cherlas on 2018/2/11.
 */
@RunWith(JUnit4.class)
public class HamcrestTest {
    private List<String> hamcrestTestList;

    @Before
    public void setUp() {
        hamcrestTestList = new ArrayList<>();
        hamcrestTestList.add("first element");
        hamcrestTestList.add("second element");
        hamcrestTestList.add("third element");
    }

    @Test
    public void assertWithJunitTest() {
        assertTrue(hamcrestTestList.contains("first element")
                || hamcrestTestList.contains("second element")
                || hamcrestTestList.contains("third element"));
    }

    @Test
    public void assertWithHamcrestTest(){
        assertThat(hamcrestTestList, hasItem(anyOf(equalTo("first element"),
                equalTo("second element"), equalTo("third element"))));
    }

    @Test(expected = AssertionError.class)
    public void isExceptedStringTest(){
        String str="hamcrestjava";
        assertThat(str,isExceptedString());
        str=null;
        assertThat(str,isExceptedString());
    }

    @Test(expected = AssertionError.class)
    public void IsStrongPasswordTest() {
        String password;
        password = "!1s2dxs";
        assertThat(password, isStrongPassword());
        password = "123dsda";
        assertThat(password, isStrongPassword());
    }
}
