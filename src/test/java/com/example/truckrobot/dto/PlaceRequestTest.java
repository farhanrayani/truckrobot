package com.example.truckrobot.dto;

import com.example.truckrobot.model.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceRequestTest {

    @Test
    void testPlaceRequestCreation() {
        PlaceRequest request = new PlaceRequest();
        request.setX(2);
        request.setY(3);
        request.setFacing(Direction.EAST);

        assertEquals(2, request.getX());
        assertEquals(3, request.getY());
        assertEquals(Direction.EAST, request.getFacing());
    }

    @Test
    void testPlaceRequestConstructor() {
        PlaceRequest request = new PlaceRequest(1, 4, Direction.WEST);

        assertEquals(1, request.getX());
        assertEquals(4, request.getY());
        assertEquals(Direction.WEST, request.getFacing());
    }

    @Test
    void testPlaceRequestDefaultConstructor() {
        PlaceRequest request = new PlaceRequest();

        assertEquals(0, request.getX()); // Default int value
        assertEquals(0, request.getY()); // Default int value
        assertNull(request.getFacing()); // Default object value
    }
}
