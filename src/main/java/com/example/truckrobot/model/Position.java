package com.example.truckrobot.model;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position move(Direction direction) {
        return switch (direction) {
            case NORTH -> new Position(x, y + 1);
            case SOUTH -> new Position(x, y - 1);
            case EAST -> new Position(x + 1, y);
            case WEST -> new Position(x - 1, y);
        };
    }

    @Override
    public String toString() {
        return "Position{x=" + x + ", y=" + y + '}';
    }
}