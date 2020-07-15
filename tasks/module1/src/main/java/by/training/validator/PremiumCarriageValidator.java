package by.training.validator;

import java.util.Map;

public class PremiumCarriageValidator implements EntityValidator {

    @Override
    public ValidationResult validate(Map<String, String> fields) {
        EntityFieldsValidator validator = new EntityFieldsValidator();
        return validator.validateFieldsNumber(fields, 5)
                .and(validator.validatePositiveIntField(fields,"passenger"))
                .and(validator.validatePositiveDoubleField(fields,"luggage"))
                .and(validator.validatePositiveIntField(fields,"tv"))
                .and(validator.validatePositiveIntField(fields,"conditioner"));
    }
}
