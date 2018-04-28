package cc.istarx.writeunittestdemo;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoundaryDemoTest {

    @Test
    public void getScoreTest() {
        assertEquals(0, BoundaryDemo.getScore(Integer.MIN_VALUE));
        assertEquals(0, BoundaryDemo.getScore(-1));
        assertEquals(0, BoundaryDemo.getScore(0));
        assertEquals(1, BoundaryDemo.getScore(1));
        assertEquals(4, BoundaryDemo.getScore(4));
        assertEquals(5, BoundaryDemo.getScore(5));
        assertEquals(5, BoundaryDemo.getScore(6));
        assertEquals(5, BoundaryDemo.getScore(9));
        assertEquals(5, BoundaryDemo.getScore(10));
        assertEquals(11, BoundaryDemo.getScore(11));
        assertEquals(Integer.MAX_VALUE, BoundaryDemo.getScore(Integer.MAX_VALUE));
    }
}