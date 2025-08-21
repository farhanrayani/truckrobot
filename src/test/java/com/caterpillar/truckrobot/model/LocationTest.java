package com.caterpillar.truckrobot.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void testPositionCreation() {
        Location location = new Location(2, 3);
        assertEquals(2, location.getX());
        assertEquals(3, location.getY());
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
    void testMove_AllDirections(int startX, int startY, Turn turn, int expectedX, int expectedY) {
        Location start = new Location(startX, startY);
        Location result = start.move(turn);

        assertEquals(expectedX, result.getX());
        assertEquals(expectedY, result.getY());
    }

    @Test
    void testMove_DoesNotModifyOriginalPosition() {
        Location original = new Location(1, 1);
        Location moved = original.move(Turn.NORTH);

        assertEquals(1, original.getX());
        assertEquals(1, original.getY());
        assertEquals(1, moved.getX());
        assertEquals(2, moved.getY());
    }
}