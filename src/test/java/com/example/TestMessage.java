package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMessage {

    @Test
    public void testA() {
        String actual = "HELLO";
        String expected = "HELLO";
        Assertions.assertEquals(expected,actual,"May be values are different");
    }

}
