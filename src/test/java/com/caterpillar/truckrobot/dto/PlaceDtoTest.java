package com.caterpillar.truckrobot.dto;

import com.caterpillar.truckrobot.model.Turn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceDtoTest {

    @Test
    void testPlaceRequestCreation() {
        PlaceDto request = new PlaceDto();
        request.setX(2);
        request.setY(3);
        request.setFacing(Turn.EAST);

        assertEquals(2, request.getX());
        assertEquals(3, request.getY());
        assertEquals(Turn.EAST, request.getFacing());
    }

    @Test
    void testPlaceRequestConstructor() {
        PlaceDto request = new PlaceDto(1, 4, Turn.WEST);

        assertEquals(1, request.getX());
        assertEquals(4, request.getY());
        assertEquals(Turn.WEST, request.getFacing());
    }

    @Test
    void testPlaceRequestDefaultConstructor() {
        PlaceDto request = new PlaceDto();

        assertEquals(0, request.getX()); // Default int value
        assertEquals(0, request.getY()); // Default int value
        assertNull(request.getFacing()); // Default object value
    }
}
