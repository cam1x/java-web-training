package by.training.entity;

public abstract class RailwayCarriage {

    protected int maxPassengerCount;
    protected double maxLuggageCapacity;
    protected CarriageType type;

    public RailwayCarriage(CarriageType type, int maxPassengerCount, double maxLuggageCapacity) {
        this.type = type;
        this.maxPassengerCount=maxPassengerCount;
        this.maxLuggageCapacity=maxLuggageCapacity;
    }

    public int getMaxPassengerCount() {
        return maxPassengerCount;
    }

    public void setMaxPassengerCount(int maxPassengerCount) {
        this.maxPassengerCount = maxPassengerCount;
    }

    public double getMaxLuggageCapacity() {
        return maxLuggageCapacity;
    }

    public void setMaxLuggageCapacity(double maxLuggageCapacity) {
        this.maxLuggageCapacity = maxLuggageCapacity;
    }

    public CarriageType getType() {
        return type;
    }
}
