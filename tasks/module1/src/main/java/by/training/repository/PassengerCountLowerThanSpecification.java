package by.training.repository;

import by.training.entity.RailwayCarriage;

public class PassengerCountLowerThanSpecification implements Specification<RailwayCarriage> {

    private int limit;

    public PassengerCountLowerThanSpecification(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean isSatisfiedBy(RailwayCarriage entity) {
        return entity.getMaxPassengerCount()<=limit;
    }
}
