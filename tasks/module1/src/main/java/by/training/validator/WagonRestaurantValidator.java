package by.training.validator;

import java.util.Map;

public class WagonRestaurantValidator implements EntityValidator {

    @Override
    public ValidationResult validate(Map<String, String> fields) {
        EntityFieldsValidator validator = new EntityFieldsValidator();
        return validator.validateFieldsNumber(fields,4)
                .and(validator.validatePositiveIntField(fields,"passenger"))
                .and(validator.validatePositiveDoubleField(fields, "luggage"))
                .and(validator.validatePositiveIntField(fields,"table"));
    }
}
