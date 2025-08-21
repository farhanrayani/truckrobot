package com.example.truckrobot.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testPositionCreation() {
        Position position = new Position(2, 3);
        assertEquals(2, position.getX());
        assertEquals(3, position.getY());
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, NORTH, 0, 1",
        "0, 0, SOUTH, 0, -1",
        "0, 0, EAST, 1, 0",
        "0, 0, WEST, -1, 0",
        "2, 3, NORTH, 2, 4",
        "2, 3, SOUTH, 2, 2",
        "2, 3, EAST, 3, 3",
        "2, 3, WEST, 1, 3"
    })
    void testMove_AllDirections(int startX, int startY, Direction direction, int expectedX, int expectedY) {
        Position start = new Position(startX, startY);
        Position result = start.move(direction);

        assertEquals(expectedX, result.getX());
        assertEquals(expectedY, result.getY());
    }

    @Test
    void testMove_DoesNotModifyOriginalPosition() {
        Position original = new Position(1, 1);
        Position moved = original.move(Direction.NORTH);

        assertEquals(1, original.getX());
        assertEquals(1, original.getY());
        assertEquals(1, moved.getX());
        assertEquals(2, moved.getY());
    }

    @Test
    void testPositionToString() {
        Position position = new Position(3, 4);
        String result = position.toString();
        assertTrue(result.contains("x=3"));
        assertTrue(result.contains("y=4"));
    }
}