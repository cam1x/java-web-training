package by.training.entity;

import java.util.Objects;

public class WagonRestaurant extends RailwayCarriage {

    private int numOfTables;

    public WagonRestaurant(int maxPassengerCount, double maxLuggageCapacity, int numOfTables) {
        super(CarriageType.RESTAURANT, maxPassengerCount, maxLuggageCapacity);
        this.numOfTables=numOfTables;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WagonRestaurant that = (WagonRestaurant) o;
        return numOfTables == that.numOfTables &&
                maxLuggageCapacity == that.maxLuggageCapacity &&
                maxPassengerCount == that.maxPassengerCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numOfTables);
    }

    @Override
    public String toString() {
        return "WagonRestaurant{" +
                "numOfTables=" + numOfTables +
                ", maxPassengerCount=" + maxPassengerCount +
                ", maxLuggageCapacity=" + maxLuggageCapacity +
                ", type=" + type +
                '}';
    }
}
