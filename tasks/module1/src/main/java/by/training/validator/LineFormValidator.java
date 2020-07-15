package by.training.validator;

public class LineFormValidator {

    public ValidationResult validate(String line) {
        ValidationResult validationResult = new ValidationResult();

        if (!line.matches("(?:(?:\\s*\\b\\w+\\b\\s*)+\\:(?:\\s*\\-?\\b\\w+\\b\\s*)+\\,?)+")) {
            validationResult.addError("Wrong line form",
                    "Line doesnt match (field1:value1, field2:value2...)");
        }

        return validationResult;
    }
}
