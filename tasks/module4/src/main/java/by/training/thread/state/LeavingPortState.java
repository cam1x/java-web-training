package by.training.thread.state;

import by.training.thread.Ship;

public class LeavingPortState implements ShipState {
    @Override
    public void next(Ship ship) {
    }

    @Override
    public void prev(Ship ship) {
        ship.setState(new ProcessingState());
    }

    @Override
    public String getStatus() {
        return "Leaves port";
    }
}