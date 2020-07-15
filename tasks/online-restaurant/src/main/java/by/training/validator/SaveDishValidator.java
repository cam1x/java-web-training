package by.training.validator;

import java.util.Map;

import static by.training.application.ApplicationConstants.*;

public class SaveDishValidator implements Validator {
    private static final String PRICE_REGEX = "[1-9][0-9]*(\\.[0-9]+)?";
    private static final int MAX_NAME_LENGTH = 45;
    private static final int MAX_DESCRIPTION_LENGTH = 1500;

    @Override
    public ValidationResult validate(Map<String, String> map) throws ValidationException {
        ValidationResult validationResult = new ValidationResult();
        if (map.get(DISH_NAME_ATTRIBUTE).isEmpty() || map.get(DISH_PRICE_ATTRIBUTE).isEmpty()) {
            validationResult.addError("Wrong input", "Name and price fields are required!");
            return validationResult;
        }

        String price = map.get(DISH_PRICE_ATTRIBUTE);
        if (!price.matches(PRICE_REGEX)) {
            validationResult.addError(DISH_PRICE_ATTRIBUTE, "Not numerical!");
        }

        String name = map.get(DISH_NAME_ATTRIBUTE);
        if (name.length() > MAX_NAME_LENGTH) {
            validationResult.addError(DISH_NAME_ATTRIBUTE, "Out of limit(" + MAX_NAME_LENGTH + ")!");
        }

        String description = map.get(DISH_DESCRIPTION_ATTRIBUTE);
        if (description!=null && description.length() > MAX_DESCRIPTION_LENGTH) {
            validationResult.addError(DISH_DESCRIPTION_ATTRIBUTE, "Out of limit(" + MAX_DESCRIPTION_LENGTH + ")!");
        }

        return validationResult;
    }
}
