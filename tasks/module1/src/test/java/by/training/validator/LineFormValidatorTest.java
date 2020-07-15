package by.training.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class LineFormValidatorTest {

    @Test
    public void shouldValidateValidLine(){
        String line = "field1: value1, field2: value2";

        ValidationResult expected = new ValidationResult();
        ValidationResult result = new LineFormValidator().validate(line);

        assertEquals(expected,result);
    }

    @Test
    public void shouldValidateInvalidLine(){
        String line = "field1: value1 :value3, field2: value2";

        ValidationResult expected = new ValidationResult();
        expected.addError("Wrong line form", "Line doesnt match (field1:value1, field2:value2...)");
        ValidationResult result = new LineFormValidator().validate(line);

        assertEquals(expected,result);
    }
}