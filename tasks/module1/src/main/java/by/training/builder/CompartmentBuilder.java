package by.training.builder;

import by.training.entity.CompartmentCarriage;
import by.training.entity.RailwayCarriage;

import java.util.Map;

public class CompartmentBuilder implements Builder {

    @Override
    public RailwayCarriage build(Map<String,String> fields) {
        int passengers = Integer.parseInt(fields.get("passenger"));
        double luggage = Double.parseDouble(fields.get("luggage"));
        int coupe = Integer.parseInt(fields.get("coupe"));
        return new CompartmentCarriage(passengers,luggage,coupe);
    }
}
