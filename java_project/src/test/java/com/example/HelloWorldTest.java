package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class HelloWorldTest {

    @Test
    public void testGreet() {
        HelloWorld h = new HelloWorld();
        assertEquals("Hello from Java!", h.greet());
    }
}
