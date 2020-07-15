package by.training.validator;

import java.util.Map;
import java.util.Optional;

public class DataValidator {

    public ValidationResult validate(Map<String, String> fields) {
        ValidationResult validationResult = new ValidationResult();

        if (!fields.containsKey("type")) {
            validationResult.addError("Field value not found", "type");
            return validationResult;
        }

        ValidatorFactory factory = new ValidatorFactory();
        Optional<EntityValidator> optionalValidator = factory.getByType(fields.get("type"));
        if (optionalValidator.isPresent()) {
            validationResult = optionalValidator.get().validate(fields);
        } else {
            validationResult.addError("Wrong value", "Wrong type value");
        }

        return validationResult;
    }
}