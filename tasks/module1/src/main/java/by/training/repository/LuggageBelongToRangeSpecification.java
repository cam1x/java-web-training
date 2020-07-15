package by.training.repository;

import by.training.entity.RailwayCarriage;

public class LuggageBelongToRangeSpecification implements Specification<RailwayCarriage> {

    private double lowLuggageLimit;
    private double topLuggageLimit;

    public LuggageBelongToRangeSpecification(double lowLuggageLimit, double topLuggageLimit) {
        this.lowLuggageLimit = lowLuggageLimit;
        this.topLuggageLimit = topLuggageLimit;
    }

    @Override
    public boolean isSatisfiedBy(RailwayCarriage entity) {
        return entity.getMaxLuggageCapacity()>=lowLuggageLimit &&
                entity.getMaxLuggageCapacity()<=topLuggageLimit;
    }
}
