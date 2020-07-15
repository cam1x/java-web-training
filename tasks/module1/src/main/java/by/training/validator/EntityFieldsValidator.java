package by.training.validator;

import java.util.Map;

/*
   Helper class for checking a field and its value:
     1) whether the field is contained (in the parsed line)
     2) whether the field value of the required type
     3) whether the field is valid (belongs to valid field values)
 */

public class EntityFieldsValidator {

    public ValidationResult validatePositiveIntField(Map<String, String> fields, String field) {
        ValidationResult validationResult = new ValidationResult();

        if (!fields.containsKey(field)) {
            validationResult.addError("Field value not found", field);
            return validationResult;
        }

        String value = fields.get(field);
        try {
            if(Integer.parseInt(value)<=0) {
                validationResult.addError("Wrong value", field + " number should be positive");
            }
        }
        catch (NumberFormatException e) {
            validationResult.addError("Wrong value", field + " value should be numerical(int)");
        }

        return validationResult;
    }

    public ValidationResult validatePositiveDoubleField(Map<String, String> fields, String field) {
        ValidationResult validationResult = new ValidationResult();

        if (!fields.containsKey(field)) {
            validationResult.addError("Field value not found", field);
            return validationResult;
        }

        String value = fields.get(field);
        try {
            if(Double.parseDouble(value)<=0) {
                validationResult.addError("Wrong value", field + " number should be positive");
            }
        }
        catch (NumberFormatException e) {
            validationResult.addError("Wrong value", field + " value should be numerical(double)");
        }

        return validationResult;
    }

    public ValidationResult validateFieldsNumber(Map<String, String> fields, final int NUM) {
        ValidationResult validationResult = new ValidationResult();

        if (fields.size()==NUM) {
            return validationResult;
        }

        if(fields.size()>NUM) {
            validationResult.addError("Wrong line form", "Too much fields");
        } else {
            validationResult.addError("Wrong line form", "Not enough fields to build entity");
        }

        return validationResult;
    }
}
