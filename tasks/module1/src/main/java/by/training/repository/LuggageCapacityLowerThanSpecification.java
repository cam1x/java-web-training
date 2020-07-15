package by.training.repository;

import by.training.entity.RailwayCarriage;

public class LuggageCapacityLowerThanSpecification implements Specification<RailwayCarriage> {

    private double limit;

    public LuggageCapacityLowerThanSpecification(double limit) {
        this.limit = limit;
    }

    @Override
    public boolean isSatisfiedBy(RailwayCarriage entity) {
        return entity.getMaxLuggageCapacity()<=limit;
    }
}
