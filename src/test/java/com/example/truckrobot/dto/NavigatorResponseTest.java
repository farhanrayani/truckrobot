package com.example.truckrobot.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NavigatorResponseTest {

    @Test
    void testRobotResponseCreation() {
        RobotResponse response = new RobotResponse("Test message", "SUCCESS");

        assertEquals("Test message", response.getMessage());
        assertEquals("SUCCESS", response.getStatus());
    }

    @Test
    void testRobotResponseSetters() {
        RobotResponse response = new RobotResponse("Initial", "INITIAL");

        response.setMessage("Updated message");
        response.setStatus("ERROR");

        assertEquals("Updated message", response.getMessage());
        assertEquals("ERROR", response.getStatus());
    }
}
