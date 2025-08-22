package com.caterpillar.truckrobot.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private int x;
    private int y;

    public Location move(Turn turn) {
        switch (turn) {
            case NORTH:
                return new Location(x, y + 1);
            case SOUTH:
                return new Location(x, y - 1);
            case EAST:
                return new Location(x + 1, y);
            case WEST:
                return new Location(x - 1, y);
            default:
                throw new IllegalArgumentException("Invalid turn: " + turn);
        }
    }
}