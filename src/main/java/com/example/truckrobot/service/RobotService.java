package com.example.truckrobot.service;

import com.example.truckrobot.model.Direction;
import com.example.truckrobot.model.Robot;
import org.springframework.stereotype.Service;

@Service
public class RobotService {
    private Robot robot;

    public RobotService() {
        this.robot = new Robot();
    }

    public boolean place(int x, int y, Direction direction) {
        if (isValidPosition(x, y)) {
            robot.place(x, y, direction);
            return true;
        }
        return false;
    }

    public void move() {
        robot.move();
    }

    public void turnLeft() {
        robot.turnLeft();
    }

    public void turnRight() {
        robot.turnRight();
    }

    public String report() {
        return robot.report();
    }

    public boolean isRobotPlaced() {
        return robot.isPlaced();
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 5 && y >= 0 && y < 5;
    }

    public void reset() {
        robot.reset();
    }
}