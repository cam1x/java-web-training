package by.training.validation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.Objects;

@RunWith(JUnit4.class)
public class XMLValidatorTest {
    private static String shipXSDPath;
    private static String poolXSDPath;

    private XMLValidator validator;

    @BeforeClass
    public static void prepare() {
        shipXSDPath = new File(Objects.requireNonNull(XMLValidatorTest.class.getClassLoader()
                .getResource("ships.xsd")).getFile()).getAbsolutePath();
        poolXSDPath = new File(Objects.requireNonNull(XMLValidatorTest.class.getClassLoader()
                .getResource("pool.xsd")).getFile()).getAbsolutePath();
    }

    @Test
    public void shouldValidateValidShipXml() {
        validator = new XMLValidator(shipXSDPath);
        String shipXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ships.xml")).getFile()).getAbsolutePath();

        ValidationResult validationResult = validator.validate(shipXMLPath);

        Assert.assertTrue(validationResult.isValid());
    }

    @Test
    public void shouldValidateValidPoolXml() {
        validator = new XMLValidator(poolXSDPath);
        String poolXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("pool.xml")).getFile()).getAbsolutePath();

        ValidationResult validationResult = validator.validate(poolXMLPath);

        Assert.assertTrue(validationResult.isValid());
    }

    @Test
    public void shouldReturnInvalidResultFromInvalidShipXMLFirst() {
        validator = new XMLValidator(shipXSDPath);
        String shipXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("shipsInvalid.xml")).getFile()).getAbsolutePath();

        ValidationResult validationResult = validator.validate(shipXMLPath);
        ValidationResult expectedResult = new ValidationResult();
        expectedResult.addError("Validation failed", "XML (path-"+shipXMLPath+") doesnt matches XSD (path-"+ shipXSDPath +")");

        Assert.assertFalse(validationResult.isValid());
        Assert.assertEquals(expectedResult, validationResult);
    }

    @Test
    public void shouldReturnInvalidResultFromInvalidEmptyXML() {
        validator = new XMLValidator(poolXSDPath);
        String poolXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("empty.xml")).getFile()).getAbsolutePath();

        ValidationResult validationResult = validator.validate(poolXMLPath);
        ValidationResult expectedResult = new ValidationResult();
        expectedResult.addError("Validation failed", "XML (path-"+poolXMLPath+") doesnt matches XSD (path-"+poolXSDPath+")");

        Assert.assertFalse(validationResult.isValid());
        Assert.assertEquals(expectedResult, validationResult);
    }

    @Test
    public void shouldReturnInvalidResultFromNotXMLFile() {
        validator = new XMLValidator(poolXSDPath);
        String poolXMLPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("pool.xsd")).getFile()).getAbsolutePath();

        ValidationResult validationResult = validator.validate(poolXMLPath);
        ValidationResult expectedResult = new ValidationResult();
        expectedResult.addError("Validation failed", "XML (path-"+poolXMLPath+") doesnt matches XSD (path-"+poolXSDPath+")");

        Assert.assertFalse(validationResult.isValid());
        Assert.assertEquals(expectedResult, validationResult);
    }
}
