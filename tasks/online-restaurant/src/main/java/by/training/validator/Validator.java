package by.training.validator;

import java.util.Map;

public interface Validator {
    ValidationResult validate(Map<String, String> map) throws ValidationException;
}