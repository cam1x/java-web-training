package by.training.builder;

import by.training.entity.CompartmentCarriage;
import by.training.entity.PremiumCarriage;
import by.training.entity.RailwayCarriage;
import by.training.entity.WagonRestaurant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class BuilderTest {

    @Test
    public void shouldBuildCompartmentCarriage() {
        Map<String,String> values = new HashMap<>();
        values.put("type", "compartment");
        values.put("passenger", "50");
        values.put("luggage", "19");
        values.put("coupe", "117");

        RailwayCarriage expected = new CompartmentCarriage(50,19,117);
        Optional<Builder> optionalBuilder = new BuilderFactory().getByType(values.get("type"));
        assertTrue(optionalBuilder.isPresent());
        RailwayCarriage actual = optionalBuilder.get().build(values);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldBuildRestaurantWagon() {
        Map<String,String> values = new HashMap<>();
        values.put("type", "restaurant");
        values.put("passenger", "50");
        values.put("luggage", "19");
        values.put("table", "29");

        RailwayCarriage expected = new WagonRestaurant(50,19,29);
        Optional<Builder> optionalBuilder = new BuilderFactory().getByType(values.get("type"));
        assertTrue(optionalBuilder.isPresent());
        RailwayCarriage actual = optionalBuilder.get().build(values);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldBuildPremiumCarriage() {
        Map<String,String> values = new HashMap<>();
        values.put("type", "premium");
        values.put("passenger", "20");
        values.put("luggage", "48");
        values.put("tv", "19");
        values.put("conditioner", "31");

        RailwayCarriage expected = new PremiumCarriage(20,48,19,31);
        Optional<Builder> optionalBuilder = new BuilderFactory().getByType(values.get("type"));
        assertTrue(optionalBuilder.isPresent());
        RailwayCarriage actual = optionalBuilder.get().build(values);

        assertEquals(expected, actual);
    }
}
