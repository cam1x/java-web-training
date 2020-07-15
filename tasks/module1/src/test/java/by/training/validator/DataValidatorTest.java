package by.training.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class DataValidatorTest {

    @Test
    public void shouldValidateValidData() {
        Map<String,String> values = new HashMap<>();
        values.put("type", "compartment");
        values.put("passenger", "50");
        values.put("luggage", "19");
        values.put("coupe", "117");

        ValidationResult expected = new ValidationResult();
        ValidationResult actual = new DataValidator().validate(values);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldValidateInvalidDataFirst() {
        Map<String,String> values = new HashMap<>();
        values.put("passenger", "50");
        values.put("luggage", "19");
        values.put("coupe", "117");

        ValidationResult expected = new ValidationResult();
        expected.addError("Field value not found", "type");
        ValidationResult actual = new DataValidator().validate(values);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldValidateInvalidDataSecond() {
        Map<String,String> values = new HashMap<>();
        values.put("type", "compartment");
        values.put("passenger", "50");
        values.put("coupe", "117");

        ValidationResult expected = new ValidationResult();
        expected.addError("Wrong line form", "Not enough fields to build entity");
        expected.addError("Field value not found", "luggage");
        ValidationResult actual = new DataValidator().validate(values);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldValidateInvalidDataThird() {
        Map<String,String> values = new HashMap<>();
        values.put("type", "compartment");
        values.put("passenger", "50");
        values.put("luggage", "number");
        values.put("coupe", "-18");

        ValidationResult expected = new ValidationResult();
        expected.addError("Wrong value", "luggage" + " value should be numerical(double)");
        expected.addError("Wrong value", "coupe" + " number should be positive");
        ValidationResult actual = new DataValidator().validate(values);

        assertEquals(expected, actual);
    }
}
