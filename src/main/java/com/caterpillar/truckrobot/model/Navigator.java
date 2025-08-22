package com.caterpillar.truckrobot.model;

public class Navigator {
    private Location location;
    private Turn facing;
    private Boolean placed;

    public Navigator() {
        this.placed = false;
    }

    public void place(int x, int y, Turn turn) {
        this.location = new Location(x, y);
        this.facing = turn;
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
            Location newLocation = location.move(facing);
            if (isValidPosition(newLocation)) {
                this.location = newLocation;
            }
        }
    }

    public void reset() {
        this.location = null;
        this.facing = null;
        this.placed = false;
    }

    private Boolean isValidPosition(Location pos) {
        return pos.getX() >= 0 && pos.getX() < 5 && pos.getY() >= 0 && pos.getY() < 5;
    }

    public String report() {
        if (!placed) {
            return "ROBOT MISSING";
        }
        return location.getX() + "," + location.getY() + "," + facing;
    }

    public Boolean isPlaced() {
        return placed;
    }

    public Location getPosition() {
        return location;
    }

    public Turn getFacing() {
        return facing;
    }
}