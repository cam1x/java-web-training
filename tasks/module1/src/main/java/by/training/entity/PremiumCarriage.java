package by.training.entity;

import java.util.Objects;

public class PremiumCarriage extends RailwayCarriage {

    private int numOfConditioners;
    private int numOfTVs;

    public PremiumCarriage(int maxPassengerCount, double maxLuggageCapacity,
                           int numOfConditioners, int numOfTVs) {
        super(CarriageType.PREMIUM, maxPassengerCount, maxLuggageCapacity);
        this.numOfConditioners=numOfConditioners;
        this.numOfTVs=numOfTVs;
    }

    public int getNumOfConditioners() {
        return numOfConditioners;
    }

    public void setNumOfConditioners(int numOfConditioners) {
        this.numOfConditioners = numOfConditioners;
    }

    public int getNumOfTVs() {
        return numOfTVs;
    }

    public void setNumOfTVs(int numOfTVs) {
        this.numOfTVs = numOfTVs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PremiumCarriage that = (PremiumCarriage) o;
        return numOfConditioners == that.numOfConditioners &&
                numOfTVs == that.numOfTVs &&
                maxLuggageCapacity == that.maxLuggageCapacity &&
                maxPassengerCount == that.maxPassengerCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numOfConditioners, numOfTVs);
    }

    @Override
    public String toString() {
        return "PremiumCarriage{" +
                "numOfConditioners=" + numOfConditioners +
                ", numOfTVs=" + numOfTVs +
                ", maxPassengerCount=" + maxPassengerCount +
                ", maxLuggageCapacity=" + maxLuggageCapacity +
                ", type=" + type +
                '}';
    }
}
