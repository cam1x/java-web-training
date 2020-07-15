package by.training.thread.state;

import by.training.thread.Ship;

public class WaitingBerthState implements ShipState {
    @Override
    public void next(Ship ship) {
        ship.setState(new ProcessingState());
    }

    @Override
    public void prev(Ship ship) {
        ship.setState(new SailingToPortState());
    }

    @Override
    public String getStatus() {
        return "Expects available berth from dispatcher";
    }
}