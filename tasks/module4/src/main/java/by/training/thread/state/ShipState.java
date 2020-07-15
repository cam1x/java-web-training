package by.training.thread.state;

import by.training.thread.Ship;

public interface ShipState {
    void next(Ship ship);
    void prev(Ship ship);
    String getStatus();
}