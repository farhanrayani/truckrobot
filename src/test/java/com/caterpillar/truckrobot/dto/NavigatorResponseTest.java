package com.caterpillar.truckrobot.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NavigatorResponseTest {

    @Test
    void testRobotResponseCreation() {
        ResponseDto response = new ResponseDto("Test message", "SUCCESS");

        assertEquals("Test message", response.getMessage());
        assertEquals("SUCCESS", response.getStatus());
    }

    @Test
    void testRobotResponseSetters() {
        ResponseDto response = new ResponseDto("Initial", "INITIAL");

        response.setMessage("Updated message");
        response.setStatus("ERROR");

        assertEquals("Updated message", response.getMessage());
        assertEquals("ERROR", response.getStatus());
    }
}
