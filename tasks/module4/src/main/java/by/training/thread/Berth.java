package by.training.thread;

import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Berth implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(Berth.class);
    private Lock lock = new ReentrantLock();

    private String berthName;
    private Ship currShip;//ship currently being processed at berth
    private AtomicLong numOfLoadedToShipContainers = new AtomicLong(0);
    private AtomicLong numOfUnloadedFromShipContainers = new AtomicLong(0);

    public Berth(String berthName) {
        this.berthName = berthName;
    }

    public String getBerthName() {
        return berthName;
    }

    public Optional<Ship> getCurrShip() {
        return (currShip==null) ? Optional.empty() : Optional.of(currShip);
    }

    public long getNumOfLoadedToShipContainers() {
        return numOfLoadedToShipContainers.get();
    }

    public long getNumOfUnloadedFromShipContainers() {
        return numOfUnloadedFromShipContainers.get();
    }

    /**
     * The type of operation on the quay coincides
     * with the type of operation of the ship being processed
     *
     * @return current berth operation type if the pier has a ship
     *         otherwise returns Optional.empty()
     */
    public Optional<ShipOperationType> getOperationType() {
        return (currShip==null) ? Optional.empty() : Optional.of(currShip.getOperationType());
    }

    /**
     * Serves the ship arriving at the pier
     */
    @Override
    public void run() {
        try {
            switch (currShip.getOperationType()) {
                case UNLOAD: {
                    unload();
                    break;
                }
                case LOAD: {
                    load();
                    break;
                }
            }
        } catch (BerthException ex) {
            LOGGER.error("While loading to ship " + currShip.getShipName() +
                    " from berth there was a problem: " + ex.getMessage());
        } catch (NullPointerException ex) {
            LOGGER.error("An attempt to start the work of the berth " + berthName +
                    " , to which the ship did not arrive");
        }
    }

    void setCurrShip(Ship currShip) {
        this.currShip = currShip;
    }

    /**
     * Replenishes the number of containers
     * in the warehouse with a berth after loading the ship
     */
    void loadBerthWarehouse() {
        numOfLoadedToShipContainers.set(0);
    }

    /**
     * Unloads a berth warehouse from
     * containers loaded from the ship
     */
    void unloadBerthWarehouse() {
        numOfUnloadedFromShipContainers.set(0);
    }

    /**
     * Loads the ship to full
     *
     * @throws BerthException if berth was interrupted
     */
    private void load() throws BerthException {
        lock.lock();
        try {
            LOGGER.info("Loading on berth " + berthName + " started.....");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new BerthException(e);
            }
            long toLoad = currShip.getCapacity() - currShip.getCurrCongestion();
            currShip.load(toLoad);
            numOfLoadedToShipContainers.set(numOfLoadedToShipContainers.addAndGet(toLoad));
            LOGGER.info("Loaded: " + toLoad + " containers");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Unloads the ship completely
     *
     * @throws BerthException if berth was interrupted
     */
    private void unload() throws BerthException {
        lock.lock();
        try {
            LOGGER.info("Unloading on berth " + berthName + " started.....");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new BerthException(e);
            }
            long toUnload = currShip.getCurrCongestion();
            currShip.unload(toUnload);
            numOfUnloadedFromShipContainers.set(numOfUnloadedFromShipContainers.addAndGet(toUnload));
            LOGGER.info("Unloaded: " + toUnload + " containers");
        } finally {
            lock.unlock();
        }
    }
}