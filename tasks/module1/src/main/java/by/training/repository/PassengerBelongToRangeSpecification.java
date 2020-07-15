package by.training.repository;

import by.training.entity.RailwayCarriage;

public class PassengerBelongToRangeSpecification implements Specification<RailwayCarriage> {

    private int lowPassengerLimit;
    private int topPassengerLimit;

    public PassengerBelongToRangeSpecification(int lowPassengerLimit, int topPassengerLimit) {
        this.lowPassengerLimit = lowPassengerLimit;
        this.topPassengerLimit = topPassengerLimit;
    }

    @Override
    public boolean isSatisfiedBy(RailwayCarriage entity) {
        return entity.getMaxPassengerCount()>=lowPassengerLimit &&
                entity.getMaxPassengerCount()<= topPassengerLimit;
    }
}

