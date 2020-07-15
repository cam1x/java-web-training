package by.training.service;

import by.training.entity.RailwayCarriage;
import by.training.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public class RailwayCarriageServiceTest {

     private static final int PASSENGER_MERGE_START = 10;
     private static final int PASSENGER_MERGE_END = 100;
     private static final double LUGGAGE_MERGE_START = 7.7;
     private static final double LUGGAGE_MERGE_END = 124.6;

     private RailwayCarriageService service;
     private List<RailwayCarriage> data;

     @Before
     public void initialize() {
         service=new RailwayCarriageService(new CarriageRepository());
         data = new Generator().generate();
         for (RailwayCarriage carriage:data){
             service.save(carriage);
         }
     }

     @Test
     public void shouldReturnAllCarriages() {
         assertEquals(data, service.getAll());
     }

    @Test
    public void shouldSortByPassengerCountDescending() {

        List<RailwayCarriage> carriages = service.sort(Comparator.comparing(RailwayCarriage::getMaxPassengerCount).reversed());
        List<RailwayCarriage> sortedData = data.stream()
                .sorted(Comparator.comparing(RailwayCarriage::getMaxPassengerCount).reversed())
                .collect(Collectors.toList());

        assertEquals(sortedData, carriages);
    }

    @Test
    public void shouldSortByPassengerCountAscending() {
        List<RailwayCarriage> carriages = service.sort(Comparator.comparing(RailwayCarriage::getMaxPassengerCount));
        List<RailwayCarriage> sortedData = data.stream()
                .sorted(Comparator.comparing(RailwayCarriage::getMaxPassengerCount))
                .collect(Collectors.toList());

        assertEquals(sortedData, carriages);
    }

    @Test
    public void shouldSortByLuggageCapacityDescending() {
        List<RailwayCarriage> carriages = service.sort(Comparator.comparing(RailwayCarriage::getMaxLuggageCapacity).reversed());
        List<RailwayCarriage> sortedData = data.stream()
                .sorted(Comparator.comparing(RailwayCarriage::getMaxLuggageCapacity).reversed())
                .collect(Collectors.toList());

        assertEquals(sortedData, carriages);
    }

    @Test
    public void shouldSortByLuggageCapacityAscending() {
        List<RailwayCarriage> carriages = service.sort(Comparator.comparing(RailwayCarriage::getMaxLuggageCapacity));
        List<RailwayCarriage> sortedData = data.stream()
                .sorted(Comparator.comparing(RailwayCarriage::getMaxLuggageCapacity))
                .collect(Collectors.toList());

        assertEquals(sortedData, carriages);
    }

    @Test
    public void shouldSortByPassengerThenLuggageDescending() {
        List<RailwayCarriage> carriages = service.sort(Comparator.comparing(RailwayCarriage::getMaxPassengerCount)
                .thenComparing(RailwayCarriage::getMaxLuggageCapacity).reversed());
        List<RailwayCarriage> sortedData = data.stream()
                .sorted(Comparator.comparing(RailwayCarriage::getMaxPassengerCount)
                        .thenComparing(RailwayCarriage::getMaxLuggageCapacity).reversed())
                .collect(Collectors.toList());

        assertEquals(sortedData, carriages);
    }

    @Test
    public void shouldSortByPassengerThenLuggageAscending() {
        List<RailwayCarriage> carriages = service.sort(Comparator.comparing(RailwayCarriage::getMaxPassengerCount)
                .thenComparing(RailwayCarriage::getMaxLuggageCapacity));
        List<RailwayCarriage> sortedData = data.stream()
                .sorted(Comparator.comparing(RailwayCarriage::getMaxPassengerCount)
                        .thenComparing(RailwayCarriage::getMaxLuggageCapacity))
                .collect(Collectors.toList());

        assertEquals(sortedData, carriages);
    }

    @Test
    public void shouldCalcPassengerCount() {
        int sumOfPassengers = service.calcPassenger();
        int expected = data.stream()
                .mapToInt(RailwayCarriage::getMaxPassengerCount)
                .sum();

        assertEquals(expected,sumOfPassengers);
    }

    @Test
    public void shouldCalcLuggageCapacity() {
        Double sumOfLuggage = service.calcCapacity();

        double expected = data.stream()
                .mapToDouble(RailwayCarriage::getMaxLuggageCapacity)
                .sum();
        assertTrue(Math.abs(expected - sumOfLuggage) < 1e-6);
    }

    @Test
    public void shouldFindHigherPassengerThan() {
        List<RailwayCarriage> carriages =
                service.find(new PassengerCountHigherThanSpecification(PASSENGER_MERGE_START));
        List<RailwayCarriage> filteredData = data.stream()
                .filter(x->x.getMaxPassengerCount()>=PASSENGER_MERGE_START)
                .collect(Collectors.toList());

        assertEquals(filteredData, carriages);
    }

    @Test
    public void shouldFindLowerPassengerThan() {
        List<RailwayCarriage> carriages =
                service.find(new PassengerCountLowerThanSpecification(PASSENGER_MERGE_END));
        List<RailwayCarriage> filteredData = data.stream()
                .filter(x->x.getMaxPassengerCount()<=PASSENGER_MERGE_END)
                .collect(Collectors.toList());

        assertEquals(filteredData, carriages);
    }

    @Test
    public void shouldFindByPassengerMerge() {
        List<RailwayCarriage> carriages =
                service.find(new PassengerBelongToRangeSpecification(PASSENGER_MERGE_START,PASSENGER_MERGE_END));
        List<RailwayCarriage> filteredData = data.stream()
                .filter(x->x.getMaxPassengerCount()>=PASSENGER_MERGE_START && x.getMaxPassengerCount()<=PASSENGER_MERGE_END)
                .collect(Collectors.toList());

        assertEquals(filteredData, carriages);
    }

    @Test
    public void shouldFindHigherLuggageThan() {
        List<RailwayCarriage> carriages =
                service.find(new LuggageCapacityHigherThanSpecification(LUGGAGE_MERGE_START));
        List<RailwayCarriage> filteredData = data.stream()
                .filter(x->x.getMaxLuggageCapacity()>=PASSENGER_MERGE_START)
                .collect(Collectors.toList());

        assertEquals(filteredData, carriages);
    }

    @Test
    public void shouldFindLowerLuggageThan() {
        List<RailwayCarriage> carriages =
                service.find(new LuggageCapacityLowerThanSpecification(LUGGAGE_MERGE_END));
        List<RailwayCarriage> filteredData = data.stream()
                .filter(x->x.getMaxLuggageCapacity()<=LUGGAGE_MERGE_END)
                .collect(Collectors.toList());

        assertEquals(filteredData, carriages);
    }

    @Test
    public void shouldFindByLuggageMerge() {
        List<RailwayCarriage> carriages =
                service.find(new LuggageBelongToRangeSpecification(LUGGAGE_MERGE_START, LUGGAGE_MERGE_END));
        List<RailwayCarriage> filteredData = data.stream()
                .filter(x->x.getMaxLuggageCapacity()>=LUGGAGE_MERGE_START && x.getMaxLuggageCapacity()<=LUGGAGE_MERGE_END)
                .collect(Collectors.toList());

        assertEquals(filteredData, carriages);
    }
}