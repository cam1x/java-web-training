package by.training.repository;

import by.training.entity.RailwayCarriage;

public class PassengerCountHigherThanSpecification implements Specification<RailwayCarriage>{

    private int limit;

    public PassengerCountHigherThanSpecification(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean isSatisfiedBy(RailwayCarriage entity) {
        return entity.getMaxPassengerCount()>=limit;
    }
}
