package com.dmdev.dima;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppUnitTest {
    @Test
    @DisplayName("Single unit test")
    void testExample() {
        assertTrue(true);
    }
    @Test
    @DisplayName("Summing two numbers")
    void sumTest() {
        assertEquals(11, Main.calculateSum(5, 6));
    }
}
