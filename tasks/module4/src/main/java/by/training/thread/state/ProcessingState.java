package by.training.thread.state;

import by.training.thread.Ship;

public class ProcessingState implements ShipState {
    @Override
    public void next(Ship ship) {
        ship.setState(new LeavingPortState());
    }

    @Override
    public void prev(Ship ship) {
        ship.setState(new WaitingBerthState());
    }

    @Override
    public String getStatus() {
        return "Moored to a free berth";
    }
}