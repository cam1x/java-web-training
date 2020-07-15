package by.training.thread;

import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.training.thread.ShipParkingLotConstants.*;

public class BerthPool {
    private final static Logger LOGGER = Logger.getLogger(Berth.class);
    private Lock lock = new ReentrantLock();

    private TimeUnit timeUnit;
    private long millis;
    private Semaphore semaphore;
    private Port port;
    private Queue<Berth> availableBerths;/*
    moorings available for issuing ships*/
    private Map<String, List<Condition>> waitingShips = new HashMap<>();/*
    ships waiting in the parking lot*/

    public BerthPool(Port port, TimeUnit timeUnit, long millis) {
        this.port = port;
        semaphore = new Semaphore(port.getNumOfBerths(), true);
        availableBerths = new LinkedList<>(port.getBerths());
        this.timeUnit = timeUnit;
        this.millis = millis;
    }

    /**
     * Looking for a ship handling berth
     *
     * @param ship ship requesting berth
     * @return Optional.ofEmpty() if ship cannot be processed or timed out,
     *         otherwise - free berth
     * @throws BerthException if interrupted exception appears
     */
    public Optional<Berth> getBerth(Ship ship) throws BerthException {
        lock.lock();
        try {
            LOGGER.info("Trying to find bench for ship " + ship.getShipName());
            /*
            * If the berth search time exceeds the limit, then the ship goes to the parking lot.
            * The search will continue after the berth is vacated.
            */
            if (!semaphore.tryAcquire(millis, timeUnit)) {
                LOGGER.info("The waiting time for the ship " + ship.getShipName() + " has expired....");
                LOGGER.info("Ship: " + ship.getShipName() +
                        " goes to the parking lot, after the berth is vacated, the operation will be repeated");
                addShipToParkingLot(TIMED_OUT);
                return Optional.empty();
            }
            /*
            * If a berth was found for the ship, but the port warehouse size is insufficient for processing the ship,
            * the berth again becomes available for search and the ship goes to the parking lot.
            * The search will continue after the berth, which processed the ship of the opposite type, is released
            * (after the loading is completed, the ships will continue to search for the required unloading,
            * after unloading - requiring the loading).
            */
            if (!canBeServed(ship)) {
                semaphore.release();
                LOGGER.info("Bench found for ship " + ship.getShipName() + " but it cannot be served....");
                LOGGER.info("Ship: " + ship.getShipName() +
                        " goes to the parking lot, after the berth is vacated, the operation will be repeated");
                String toAddShipType = getFromOperatingType(ship.getOperationType());
                addShipToParkingLot(toAddShipType);
                return Optional.empty();
            }
            Berth berth = availableBerths.poll();
            LOGGER.info("Ship " + ship.getShipName() + " got berth " + Objects.requireNonNull(berth).getBerthName());
            bookContainersFromPort(ship);
            Objects.requireNonNull(berth).setCurrShip(ship);
            return Optional.of(berth);
        } catch (InterruptedException e) {
            throw new BerthException(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * free berth after ship maintenance
     *
     * @param berth returning berth
     */
    public void returnBerth(Berth berth) {
        lock.lock();
        try {
            availableBerths.add(berth);
            notifyWaitingInParkingLotShips(TIMED_OUT);
            notifyWaitingInParkingLotShips(getAlertType(berth));
            updatePortWarehouseStatus(berth);
            semaphore.release();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Checks if the port warehouse size is
     * insufficient for processing the ship
     *
     * @param ship requesting ship
     * @return true if ship can be served
     *         otherwise - false
     */
    private boolean canBeServed(Ship ship) {
        switch (ship.getOperationType()) {
            case LOAD: {
                return port.getCurrCongestion() - port.getBookedToLoad() >=
                        ship.getCapacity() - ship.getCurrCongestion();
            }
            case UNLOAD: {
                return port.getCapacity() - port.getCurrCongestion() - port.getBookedToUnload() >=
                        ship.getCurrCongestion();
            }
            default: {
                return false;
            }
        }
    }

    /**
     * Reserves the required number of containers
     * for loading/unloading from the port warehouse
     *
     * @param ship requesting ship
     */
    private void bookContainersFromPort(Ship ship) {
        switch (ship.getOperationType()) {
            case LOAD: {
                port.bookToLoad(ship.getCapacity() - ship.getCurrCongestion());
                break;
            }
            case UNLOAD: {
                port.bookToUnload(ship.getCurrCongestion());
                break;
            }
        }
    }

    /**
     * Adds a ship of the appropriate type to the parking lot
     *
     * @param type adding to lot ship type
     * @throws InterruptedException when trying to stop
     * an already stopped thread(ship)
     */
    private void addShipToParkingLot(String type) throws InterruptedException {
        Condition condition = lock.newCondition();
        if (waitingShips.containsKey(type)) {
            waitingShips.get(type).add(condition);
        } else {
            waitingShips.put(type, new ArrayList<>(Collections.singletonList(condition)));
        }
        condition.await();
    }

    /**
     * Informs ships in parking lot of the appropriate type
     * about the possibility of re-requesting the berth
     *
     * @param type notifying ship type
     */
    private void notifyWaitingInParkingLotShips(String type) {
        if (waitingShips.containsKey(type)) {
            List<Condition> toSignalShipList = waitingShips.get(type);
            if (toSignalShipList!=null) {
                toSignalShipList.forEach(Condition::signal);
                toSignalShipList.clear();
            }
        }
    }

    /**
     * Updates port warehouse status after berth completion
     *
     * @param berth maintenance pier
     */
    private void updatePortWarehouseStatus(Berth berth) {
        port.processBerth(berth);
    }
}