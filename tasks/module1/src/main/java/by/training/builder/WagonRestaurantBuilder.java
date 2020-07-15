package by.training.builder;

import by.training.entity.RailwayCarriage;
import by.training.entity.WagonRestaurant;

import java.util.Map;

public class WagonRestaurantBuilder implements Builder {

    @Override
    public RailwayCarriage build(Map<String,String> fields) {
        int passengers = Integer.parseInt(fields.get("passenger"));
        double luggage = Double.parseDouble(fields.get("luggage"));
        int table = Integer.parseInt(fields.get("table"));
        return new WagonRestaurant(passengers,luggage,table);
    }
}
