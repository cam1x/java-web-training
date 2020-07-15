package by.training.thread;

import java.util.Optional;

class ShipParkingLotConstants {
    static final String TIMED_OUT = "timedOut";
    private static final String LOADING_WAITING = "loading";
    private static final String UNLOADING_WAITING = "unloading";

    /**
     * @param operationType ship type
     * @return parking constant from ship operating type
     */
    static String getFromOperatingType(ShipOperationType operationType) {
        switch (operationType) {
            case UNLOAD: {
                return UNLOADING_WAITING;
            }
            case LOAD: {
                return LOADING_WAITING;
            }
            default: {
                return "";
            }
        }
    }

    /**
     * @param berth income berth
     * @return alerting constant(opposite for income)
     */
    static String getAlertType(Berth berth) {
        Optional<ShipOperationType> operationType = berth.getOperationType();
        if (!operationType.isPresent()) {
            return "";
        }
        switch (operationType.get()) {
            case LOAD: {
                return UNLOADING_WAITING;
            }
            case UNLOAD: {
                return LOADING_WAITING;
            }
            default: {
                return "";
            }
        }
    }
}
