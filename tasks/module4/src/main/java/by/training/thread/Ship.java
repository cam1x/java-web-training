package by.training.thread;

import by.training.thread.state.SailingToPortState;
import by.training.thread.state.ShipState;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ship implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(Ship.class);
    private Lock lock = new ReentrantLock();

    private ShipType shipType;
    private String name;
    private long capacity;
    private AtomicLong currCongestion = new AtomicLong(0);
    private ShipOperationType operationType;
    private BerthPool berthPool;
    private ShipState state = new SailingToPortState();

    public Ship(ShipType shipType, String name, long capacity,
                long currCongestion, ShipOperationType operationType, BerthPool berthPool) {
        this.name = name;
        this.shipType = shipType;
        this.operationType = operationType;
        this.berthPool = berthPool;
        setCapacity(capacity);
        setCurrCongestion(currCongestion);
    }

    public String getShipName() {
        return name;
    }

    public ShipOperationType getOperationType() {
        return operationType;
    }

    public long getCapacity() {
        return capacity;
    }

    public long getCurrCongestion() {
        return currCongestion.get();
    }

    public void setCurrCongestion(long currCongestion) {
        lock.lock();
        try {
            if (currCongestion > 0) {
                this.currCongestion.set(currCongestion);
            }
        } finally {
            lock.unlock();
        }
    }

    public void setCapacity(long capacity) {
        lock.lock();
        try {
            if (capacity > 0) {
                this.capacity = capacity;
            }
        } finally {
            lock.unlock();
        }
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    public void load(long num) {
        lock.lock();
        try {
            if (num > 0 && currCongestion.get() + num <= capacity) {
                currCongestion.set(currCongestion.addAndGet(num));
            }
        } finally {
            lock.unlock();
        }
    }

    public void unload(long num) {
        lock.lock();
        try {
            if (num > 0 && currCongestion.get() - num >= 0) {
                currCongestion.set(currCongestion.addAndGet(-num));
            }
        } finally {
            lock.unlock();
        }
    }

    public void nextState() {
        state.next(this);
    }

    public void prevState() {
        state.prev(this);
    }

    @Override
    public void run() {
        try {
            LOGGER.info("Ship "+ name + ": " + state.getStatus());
            nextState();
            LOGGER.info("Ship "+ name + ": " + state.getStatus());
            Optional<Berth> optionalBerth;
            TimeUnit.MILLISECONDS.sleep(500);

            do {
                optionalBerth = berthPool.getBerth(this);
            } while (!optionalBerth.isPresent());

            nextState();
            TimeUnit.MILLISECONDS.sleep(500);
            LOGGER.info("Ship " + name + ": " + state.getStatus());
            Berth berth = optionalBerth.get();
            Thread berthTread = new Thread(berth);
            berthTread.start();
            berthTread.join();
            TimeUnit.MILLISECONDS.sleep(500);
            berthPool.returnBerth(berth);
            nextState();
            LOGGER.info("Ship " + getShipName() + ": " + state.getStatus());
        } catch (BerthException | InterruptedException e) {
            LOGGER.error("The following error (" + e.getMessage() + ") occurred while processing the ship " +getShipName());
        }
    }

    @Override
    public String toString() {
        return "Ship{" +
                "shipType=" + shipType +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", currCongestion=" + currCongestion +
                ", operationType=" + operationType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return capacity == ship.capacity &&
                shipType == ship.shipType &&
                Objects.equals(name, ship.name) &&
                Objects.equals(currCongestion, ship.currCongestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipType, name, capacity, currCongestion);
    }
}