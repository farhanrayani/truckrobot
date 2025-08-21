package com.example.truckrobot.model;

public class Location {
    private final int x;
    private final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Location move(Turn turn) {
        return switch (turn) {
            case NORTH -> new Location(x, y + 1);
            case SOUTH -> new Location(x, y - 1);
            case EAST -> new Location(x + 1, y);
            case WEST -> new Location(x - 1, y);
        };
    }

    @Override
    public String toString() {
        return "Position{x=" + x + ", y=" + y + '}';
    }
}