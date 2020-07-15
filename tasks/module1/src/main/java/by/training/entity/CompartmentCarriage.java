package by.training.entity;

import java.util.Objects;

public class CompartmentCarriage extends RailwayCarriage {

    private int numOfCoupe;

    public CompartmentCarriage(int maxPassengerCount, double maxLuggageCapacity) {
        this(maxPassengerCount, maxLuggageCapacity, (int)Math.ceil(maxPassengerCount/4.0));
    }

    public CompartmentCarriage(int maxPassengerCount, double maxLuggageCapacity, int numOfCoupe) {
        super(CarriageType.COMPARTMENT, maxPassengerCount, maxLuggageCapacity);
        this.numOfCoupe=numOfCoupe;
    }

    public int getNumOfCoupe() {
        return numOfCoupe;
    }

    public void setNumOfCoupe(int numOfCoupe) {
        this.numOfCoupe = numOfCoupe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompartmentCarriage that = (CompartmentCarriage) o;
        return numOfCoupe == that.numOfCoupe &&
                maxLuggageCapacity == that.maxLuggageCapacity &&
                maxPassengerCount == that.maxPassengerCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numOfCoupe);
    }

    @Override
    public String toString() {
        return "CompartmentCarriage{" +
                "numOfCoupe=" + numOfCoupe +
                ", maxPassengerCount=" + maxPassengerCount +
                ", maxLuggageCapacity=" + maxLuggageCapacity +
                ", type=" + type +
                '}';
    }
}
