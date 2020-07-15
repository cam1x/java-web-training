package by.training.builder;

import by.training.entity.PremiumCarriage;
import by.training.entity.RailwayCarriage;

import java.util.Map;

public class PremiumCarriageBuilder implements Builder {

    @Override
    public RailwayCarriage build(Map<String,String> fields) {
        int passengers = Integer.parseInt(fields.get("passenger"));
        double luggage = Double.parseDouble(fields.get("luggage"));
        int tvs = Integer.parseInt(fields.get("tv"));
        int conditioners = Integer.parseInt(fields.get("conditioner"));
        return new PremiumCarriage(passengers,luggage,tvs,conditioners);
    }
}
