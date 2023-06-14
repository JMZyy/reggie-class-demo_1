package edu.czjt.reggie.common;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jinkun.tian on 2023/5/12
 */
public class TestForClass {

    @Test
    public void assertTest(){

        assertEquals(1,1);
        assertNotNull("aaa");
        // assertNotNull(null);
        assertTrue(1==1);
        assertTrue(returnTrue());
        assertThrows(CustomException.class, ()->{
            throw new CustomException("这是一个定制的Exception。");
        });

    }

    static boolean returnTrue(){
        return true;
    }

    @BeforeAll
    static public void beforeClass() {
        System.out.println("before all.");
    }

    @AfterAll
    static public void afterClass(){
        System.out.println("after all.");
    }

    @Test
    public void test(){
        System.out.println("hello");
    }

    @Test
    public void test1(){
        System.out.println("hello 1");
    }

    @Test
    public void test3(){
        System.out.println("hello 2");
    }
}
