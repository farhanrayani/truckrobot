package com.caterpillar.truckrobot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class NavigatorTest {

    private Navigator navigator;

    @BeforeEach
    void setUp() {
        navigator = new Navigator();
    }

    @Test
    void testInitialState() {
        assertFalse(navigator.isPlaced());
        assertEquals("ROBOT MISSING", navigator.report());
        assertNull(navigator.getPosition());
        assertNull(navigator.getFacing());
    }

    @Test
    void testPlace_ValidPosition() {
        navigator.place(1, 2, Turn.NORTH);

        assertTrue(navigator.isPlaced());
        assertEquals(1, navigator.getPosition().getX());
        assertEquals(2, navigator.getPosition().getY());
        assertEquals(Turn.NORTH, navigator.getFacing());
        assertEquals("1,2,NORTH", navigator.report());
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, NORTH",
        "4, 4, SOUTH",
        "2, 3, EAST",
        "1, 1, WEST"
    })
    void testPlace_BoundaryPositions(int x, int y, Turn turn) {
        navigator.place(x, y, turn);

        assertTrue(navigator.isPlaced());
        assertEquals(x, navigator.getPosition().getX());
        assertEquals(y, navigator.getPosition().getY());
        assertEquals(turn, navigator.getFacing());
    }

    @Test
    void testMove_ValidMovement() {
        navigator.place(1, 1, Turn.NORTH);
        navigator.move();

        assertEquals("1,2,NORTH", navigator.report());
    }

    @Test
    void testMove_PreventsFallingOffTable() {
        // Test north boundary
        navigator.place(2, 4, Turn.NORTH);
        navigator.move(); // Should not move off table
        assertEquals("2,4,NORTH", navigator.report());

        // Test south boundary
        navigator.place(2, 0, Turn.SOUTH);
        navigator.move(); // Should not move off table
        assertEquals("2,0,SOUTH", navigator.report());

        // Test east boundary
        navigator.place(4, 2, Turn.EAST);
        navigator.move(); // Should not move off table
        assertEquals("4,2,EAST", navigator.report());

        // Test west boundary
        navigator.place(0, 2, Turn.WEST);
        navigator.move(); // Should not move off table
        assertEquals("0,2,WEST", navigator.report());
    }

    @Test
    void testMove_WithoutPlacement() {
        navigator.move(); // Should be ignored
        assertFalse(navigator.isPlaced());
        assertEquals("ROBOT MISSING", navigator.report());
    }

    @Test
    void testTurnLeft() {
        navigator.place(1, 1, Turn.NORTH);
        navigator.turnLeft();

        assertEquals("1,1,WEST", navigator.report());
    }

    @Test
    void testTurnLeft_WithoutPlacement() {
        navigator.turnLeft(); // Should be ignored
        assertFalse(navigator.isPlaced());
    }

    @Test
    void testTurnRight() {
        navigator.place(1, 1, Turn.NORTH);
        navigator.turnRight();

        assertEquals("1,1,EAST", navigator.report());
    }

    @Test
    void testTurnRight_WithoutPlacement() {
        navigator.turnRight(); // Should be ignored
        assertFalse(navigator.isPlaced());
    }

    @Test
    void testReset() {
        navigator.place(2, 3, Turn.EAST);
        assertTrue(navigator.isPlaced());

        navigator.reset();

        assertFalse(navigator.isPlaced());
        assertEquals("ROBOT MISSING", navigator.report());
        assertNull(navigator.getPosition());
        assertNull(navigator.getFacing());
    }

    @Test
    void testComplexMovementSequence() {
        // Test sequence: PLACE 1,2,EAST MOVE MOVE LEFT MOVE REPORT
        navigator.place(1, 2, Turn.EAST);
        navigator.move(); // 2,2,EAST
        navigator.move(); // 3,2,EAST
        navigator.turnLeft(); // 3,2,NORTH
        navigator.move(); // 3,3,NORTH

        assertEquals("3,3,NORTH", navigator.report());
    }
}