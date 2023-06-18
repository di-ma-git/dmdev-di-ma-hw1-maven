package com.dmdev.dima;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {
    @Test
    @DisplayName("Single integration test")
    void testExample() {
        assertTrue(true);
    }
    @Test
    @DisplayName("Multiplication of two numbers")
    void sumTest() {
        assertEquals(6, Main.calculateMulti(2, 3));
    }
}
