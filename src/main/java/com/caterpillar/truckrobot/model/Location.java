package com.caterpillar.truckrobot.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private int x;
    private int y;

    public Location move(Turn turn) {
        return switch (turn) {
            case NORTH -> new Location(x, y + 1);
            case SOUTH -> new Location(x, y - 1);
            case EAST -> new Location(x + 1, y);
            case WEST -> new Location(x - 1, y);
        };
    }
}