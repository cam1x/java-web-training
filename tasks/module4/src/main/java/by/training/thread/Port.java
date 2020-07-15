package by.training.thread;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private final static Logger LOGGER = Logger.getLogger(Berth.class);
    private Lock lock = new ReentrantLock();

    private long capacity;
    private AtomicLong currCongestion = new AtomicLong(0);
    private AtomicLong bookedToLoad = new AtomicLong(0);/*The number
    of containers that are still in stock but frozen to load a specific ship*/
    private AtomicLong bookedToUnload = new AtomicLong(0);/*Space \\
    reserved for unloading n containers from a specific ship*/
    private List<Berth> berths;

    public Port(long capacity, long currCongestion, List<Berth> berths) {
        setCapacity(capacity);
        setCurrCongestion(currCongestion);
        this.berths = new ArrayList<>(berths);
    }

    public long getBookedToLoad() {
        return bookedToLoad.get();
    }

    public long getBookedToUnload() {
        return bookedToUnload.get();
    }

    public List<Berth> getBerths() {
        return new ArrayList<>(berths);
    }

    public int getNumOfBerths() {
        return berths.size();
    }

    public long getCapacity() {
        return capacity;
    }

    public long getCurrCongestion() {
        return currCongestion.get();
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

    /**
     * Books containers from warehouse for loading to ship
     *
     * @param toLoad number of containers
     */
    public void bookToLoad(long toLoad) {
        lock.lock();
        try {
            if (toLoad > 0 && toLoad <= currCongestion.get()) {
                bookedToLoad.set(bookedToLoad.addAndGet(toLoad));
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Books place in warehouse for unloaded from ship containers
     *
     * @param toUnload number of containers
     */
    public void bookToUnload(long toUnload) {
        lock.lock();
        try {
            if (toUnload > 0 && toUnload <= capacity - currCongestion.get()) {
                bookedToUnload.set(bookedToUnload.addAndGet(toUnload));
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Remove booked status
     *
     * @param toRebook number of containers
     */
    public void rebookToLoad(long toRebook) {
        lock.lock();
        try {
            if (toRebook > 0 && toRebook <= bookedToLoad.get()) {
                bookedToLoad.set(bookedToLoad.addAndGet(-toRebook));
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Remove booked status
     *
     * @param toRebook number of containers
     */
    public void rebookToUnload(long toRebook) {
        lock.lock();
        try {
            if (toRebook > 0 && toRebook <= bookedToUnload.get()) {
                bookedToUnload.set(bookedToUnload.addAndGet(-toRebook));
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Adds containers to warehouse
     *
     * @param num number of containers
     */
    public void addContainers(long num) {
        lock.lock();
        try {
            if (num > 0 && currCongestion.get() + num <= capacity) {
                currCongestion.set(currCongestion.addAndGet(num));
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Remove containers from warehouse
     *
     * @param num number of containers
     */
    public void removeContainers(long num) {
        lock.lock();
        try {
            if (num > 0 && currCongestion.get() - num >= 0) {
                currCongestion.set(currCongestion.addAndGet(-num));
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Updates port warehouse status after berth completion
     *
     * @param berth maintenance pier
     */
    public void processBerth(Berth berth) {
        lock.lock();
        try {
            Optional<ShipOperationType> operationType = berth.getOperationType();
            if (!operationType.isPresent()) {
                return;
            }
            switch (operationType.get()) {
                case LOAD: {
                    processLoadedBerth(berth);
                    break;
                }
                case UNLOAD: {
                    processUnloadedBerth(berth);
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Updates port warehouse status after ship loading
     *
     * @param berth berth completed loading the ship
     */
    private void processLoadedBerth(Berth berth) {
        long loaded = berth.getNumOfLoadedToShipContainers();
        rebookToLoad(loaded);
        removeContainers(loaded);
        berth.loadBerthWarehouse();
        LOGGER.info("Port status after loading the ship: " + currCongestion + "\\" + capacity);
    }

    /**
     * Updates port warehouse status after ship unloading
     *
     * @param berth berth completed the unloading of the ship
     */
    private void processUnloadedBerth(Berth berth) {
        long unloaded = berth.getNumOfUnloadedFromShipContainers();
        rebookToUnload(unloaded);
        addContainers(unloaded);
        berth.unloadBerthWarehouse();
        LOGGER.info("Port status after ship unloading: " + currCongestion + "\\" + capacity);
    }
}