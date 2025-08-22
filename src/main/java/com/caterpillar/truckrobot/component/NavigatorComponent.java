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

    public Boolean doPlacing(int x, int y, Turn turn) {
        if (verifyValidPosition(x, y)) {
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

    public Boolean isRobotPlaced() {

        return navigator.isPlaced();
    }

    private Boolean verifyValidPosition(int x, int y) {
        Boolean xCordinate = false;
        Boolean yCordinate = false;

        if(x >= 0 && x < 5){
            xCordinate = true;
        } else{
            xCordinate = false;
        }

        if(y >= 0 && y < 5){
            yCordinate = true;
        } else{
            yCordinate = false;
        }
        if(xCordinate & yCordinate){
            return true;
        } else{
            return false;
        }

    }

    public void reset() {

        navigator.reset();
    }
}