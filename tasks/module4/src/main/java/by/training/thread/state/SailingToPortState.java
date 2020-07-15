package by.training.thread.state;

import by.training.thread.Ship;

public class SailingToPortState implements ShipState {
    @Override
    public void next(Ship ship) {
        ship.setState(new WaitingBerthState());
    }

    @Override
    public void prev(Ship ship) {
    }

    @Override
    public String getStatus() {
        return "Arrived at the port";
    }
}