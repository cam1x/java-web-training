package by.training.valiator;

import by.training.validator.ValidationResult;
import by.training.validator.XMLValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.Objects;

@RunWith(JUnit4.class)
public class XMLValidatorTest {

    private static XMLValidator xmlValidator;
    private static String xsdPath;

    @BeforeClass
    public static void prepare() {
        xsdPath = new File(Objects.requireNonNull(XMLValidatorTest.class.getClassLoader()
                .getResource("EntitiesSchema.xsd")).getFile()).getAbsolutePath();
        xmlValidator = new XMLValidator(xsdPath);
    }

    @Test
    public void shouldReturnValidResultFromValidXML() {
        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("ValidEntitiesFirst.xml")).getFile()).getAbsolutePath();

        ValidationResult validationResult = xmlValidator.validate(xmlPath);

        Assert.assertTrue(validationResult.isValid());
    }

    @Test
    public void shouldReturnInvalidResultFromInvalidXMLFirst() {
        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("InvalidEntitiyTagName.xml")).getFile()).getAbsolutePath();

        ValidationResult validationResult = xmlValidator.validate(xmlPath);
        ValidationResult expectedResult = new ValidationResult();
        expectedResult.addError("Validation failed", "XML (path-"+xmlPath+") doesnt matches XSD (path-"+xsdPath+")");

        Assert.assertFalse(validationResult.isValid());
        Assert.assertEquals(expectedResult, validationResult);
    }

    @Test
    public void shouldReturnInvalidResultFromInvalidXMLSecond() {
        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("InvalidEntitiyTagValue.xml")).getFile()).getAbsolutePath();

        ValidationResult validationResult = xmlValidator.validate(xmlPath);
        ValidationResult expectedResult = new ValidationResult();
        expectedResult.addError("Validation failed", "XML (path-"+xmlPath+") doesnt matches XSD (path-"+xsdPath+")");

        Assert.assertFalse(validationResult.isValid());
        Assert.assertEquals(expectedResult, validationResult);
    }

    @Test
    public void shouldReturnInvalidResultFromInvalidEmptyXML() {
        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("InvalidEmpty.xml")).getFile()).getAbsolutePath();

        ValidationResult validationResult = xmlValidator.validate(xmlPath);
        ValidationResult expectedResult = new ValidationResult();
        expectedResult.addError("Validation failed", "XML (path-"+xmlPath+") doesnt matches XSD (path-"+xsdPath+")");

        Assert.assertFalse(validationResult.isValid());
        Assert.assertEquals(expectedResult, validationResult);
    }

    @Test
    public void shouldReturnInvalidResultFromNotXMLFile() {
        String xmlPath = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource("EntitiesSchema.xsd")).getFile()).getAbsolutePath();

        ValidationResult validationResult = xmlValidator.validate(xmlPath);
        ValidationResult expectedResult = new ValidationResult();
        expectedResult.addError("Validation failed", "XML (path-"+xmlPath+") doesnt matches XSD (path-"+xsdPath+")");

        Assert.assertFalse(validationResult.isValid());
        Assert.assertEquals(expectedResult, validationResult);
    }
}
