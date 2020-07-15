package by.training.service;

import by.training.entity.CompartmentCarriage;
import by.training.entity.PremiumCarriage;
import by.training.entity.RailwayCarriage;
import by.training.entity.WagonRestaurant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Generator {

    private static final int TEST_DATA_SIZE = 100;
    private static final int MIN_PASSENGER_COUNT = 1;
    private static final int MAX_PASSENGER_COUNT = 200;
    private static final double MIN_LUGGAGE_CAPACITY = 50;
    private static final double MAX_LUGGAGE_CAPACITY = 395.5;
    private static final int MAX_TABLES_COUNT = 100;
    private static final int MAX_TV_COUNT = 25;
    private static final int MAX_CONDITIONERS = 25;

    private Random random = new Random(new Date().getTime());

    public List<RailwayCarriage> generate() {
        List<RailwayCarriage> carriages = new ArrayList<>();
        for (int i=0;i<TEST_DATA_SIZE;i++){
            double luggageCapacity =
                    random.nextDouble()*(MAX_LUGGAGE_CAPACITY-MIN_LUGGAGE_CAPACITY) + MIN_LUGGAGE_CAPACITY;
            int passengerCount =
                    random.nextInt(MAX_PASSENGER_COUNT-MIN_PASSENGER_COUNT) + MIN_PASSENGER_COUNT;

            switch (Math.abs(random.nextInt())%3) {
                case 0: {
                    carriages.add(new CompartmentCarriage(passengerCount, luggageCapacity));
                    break;
                }
                case 1: {
                    carriages.add(new WagonRestaurant(passengerCount,luggageCapacity,
                            random.nextInt(MAX_TABLES_COUNT-1)+1));
                    break;
                }
                case 2: {
                    carriages.add(new PremiumCarriage(passengerCount,luggageCapacity,
                            random.nextInt(MAX_CONDITIONERS-1)+1,
                            random.nextInt(MAX_TV_COUNT-1)+1));
                    break;
                }
                default:
                    throw new IllegalStateException("Invalid value received while filling out data list!");
            }
        }
        return carriages;
    }
}
