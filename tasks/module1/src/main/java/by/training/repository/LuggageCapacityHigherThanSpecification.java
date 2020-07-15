package by.training.repository;

import by.training.entity.RailwayCarriage;

public class LuggageCapacityHigherThanSpecification implements Specification<RailwayCarriage> {

    private double limit;

    public LuggageCapacityHigherThanSpecification(double limit) {
        this.limit = limit;
    }

    @Override
    public boolean isSatisfiedBy(RailwayCarriage entity) {
        return entity.getMaxLuggageCapacity()>=limit;
    }
}
