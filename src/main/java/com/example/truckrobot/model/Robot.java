package com.example.truckrobot.model;

public class Robot {
    private Position position;
    private Direction facing;
    private boolean placed;

    public Robot() {
        this.placed = false;
    }

    public void place(int x, int y, Direction direction) {
        this.position = new Position(x, y);
        this.facing = direction;
        this.placed = true;
    }

    public void turnLeft() {
        if (placed) {
            this.facing = facing.turnLeft();
        }
    }

    public void turnRight() {
        if (placed) {
            this.facing = facing.turnRight();
        }
    }

    public void move() {
        if (placed) {
            Position newPosition = position.move(facing);
            if (isValidPosition(newPosition)) {
                this.position = newPosition;
            }
        }
    }

    public void reset() {
        this.position = null;
        this.facing = null;
        this.placed = false;
    }

    private boolean isValidPosition(Position pos) {
        return pos.getX() >= 0 && pos.getX() < 5 && pos.getY() >= 0 && pos.getY() < 5;
    }

    public String report() {
        if (!placed) {
            return "ROBOT MISSING";
        }
        return position.getX() + "," + position.getY() + "," + facing;
    }

    public boolean isPlaced() {
        return placed;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getFacing() {
        return facing;
    }
}