package com.caterpillar.truckrobot.component;

import com.caterpillar.truckrobot.model.Navigator;
import com.caterpillar.truckrobot.model.Turn;
import org.springframework.stereotype.Component;

@Component
public class NavigatorComponent {
    private Navigator navigator;

    /**
     * Constructor for NavigatorComponent to provide singleton capabilities so that robot states are maintained
     */
    public NavigatorComponent() {
        this.navigator = new Navigator();
    }

    public boolean place(int x, int y, Turn turn) {
        if (isValidPosition(x, y)) {
            navigator.place(x, y, turn);
            return true;
        }
        return false;
    }

    public void move() {

        navigator.move();
    }

    public void turnLeft() {

        navigator.turnLeft();
    }

    public void turnRight() {

        navigator.turnRight();
    }

    public String report() {

        return navigator.report();
    }

    public boolean isRobotPlaced() {

        return navigator.isPlaced();
    }

    private boolean isValidPosition(int x, int y) {

        return x >= 0 && x < 5 && y >= 0 && y < 5;
    }

    public void reset() {

        navigator.reset();
    }
}