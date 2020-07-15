package by.training.validation;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

public class XMLValidator {
    private String pathXSD;

    public XMLValidator(String pathXSD) {
        this.pathXSD = pathXSD;
    }

    public ValidationResult validate(String path) {
        ValidationResult result = new ValidationResult();

        try {
            SchemaFactory factory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(pathXSD));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(path));
        } catch (SAXException | IOException e) {
            result.addError("Validation failed", "XML (path-"+path+") doesnt matches XSD (path-"+pathXSD+")");
        }

        return result;
    }
}

