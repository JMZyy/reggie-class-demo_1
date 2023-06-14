package edu.czjt.reggie.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by jinkun.tian on 2023/5/10
 */
class CustomExceptionTest {

    /**
     * 抛出一个error
     */
    // @Test
    public void throwErrorTest(){
        throw new StackOverflowError("Stack overflow occurred");
    }

    /**
     * 在测试中，捕获我们抛出的error
     */
    @Test
    public void assertErrorTest(){
        assertThrows(StackOverflowError.class,() -> {
            throw new StackOverflowError("Stack overflow occurred");
        });
    }

    /**
     * assert 一个运行时的error
     */
    @Test
    public void OOMErrorTest(){
        assertThrows(OutOfMemoryError.class, () -> {
            int[] arr = new int[Integer.MAX_VALUE];
        });
    }

    /**
     * 抛出自定义exception
     */
    @Test
    public void customExceptionTest(){
        assertThrows(CustomException.class, ()->{
           throw new CustomException("这是一个定制的Exception。");
        });

    }
}
