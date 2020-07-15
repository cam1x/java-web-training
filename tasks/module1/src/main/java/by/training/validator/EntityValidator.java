package by.training.validator;

import java.util.Map;

public interface EntityValidator {
    ValidationResult validate(Map<String, String> fields);
}
