package by.training.validator;

import by.training.core.Bean;
import by.training.user.UserService;
import by.training.user.UserServiceException;
import lombok.extern.log4j.Log4j;

import java.util.Map;

import static by.training.application.ApplicationConstants.*;

@Bean
@Log4j
public class RegisterDataValidator implements Validator {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String PHONE_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    private UserService userService;

    public RegisterDataValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ValidationResult validate(Map<String, String> map) throws ValidationException {
        ValidationResult validationResult = new ValidationResult();
        if ("".equals(map.get(LOGIN_ATTRIBUTE)) || "".equals(map.get(PASSWORD_ATTRIBUTE)) ||
                "".equals(map.get(REPEAT_PASSWORD_ATTRIBUTE)) || "".equals(map.get(FIRST_NAME_ATTRIBUTE)) ||
                "".equals(map.get(LAST_NAME_ATTRIBUTE)) || "".equals(map.get(EMAIL_ATTRIBUTE)) ||
                "".equals(map.get(PHONE_ATTRIBUTE))) {
            validationResult.addError("general", "notFilled");
            return validationResult;
        }

        String inputtedLogin = map.get(LOGIN_ATTRIBUTE);
        try {
            if (userService.isLoginTaken(inputtedLogin)) {
                validationResult.addError(LOGIN_ATTRIBUTE, "loginTaken");
            }
        } catch (UserServiceException e) {
            throw new ValidationException(e);
        }

        if (!map.get(PASSWORD_ATTRIBUTE).equals(map.get(REPEAT_PASSWORD_ATTRIBUTE))) {
            validationResult.addError(PASSWORD_ATTRIBUTE, "passwordsNotMath");
        }

        if (!map.get(EMAIL_ATTRIBUTE).matches(EMAIL_REGEX)) {
            validationResult.addError(EMAIL_ATTRIBUTE, "emailFormat");
        }

        if (!map.get(PHONE_ATTRIBUTE).matches(PHONE_REGEX)) {
            validationResult.addError(PHONE_ATTRIBUTE, "phoneFormat");
        }

        return validationResult;
    }
}
