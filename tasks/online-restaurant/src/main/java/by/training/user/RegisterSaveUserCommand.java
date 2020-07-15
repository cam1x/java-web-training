package by.training.user;

import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.contact.ContactDto;
import by.training.core.Bean;
import by.training.security.CryptographyManager;
import by.training.validator.RegisterDataValidator;
import by.training.validator.ValidationException;
import by.training.validator.ValidationResult;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static by.training.application.ApplicationConstants.*;

@Log4j
@Bean(name = REGISTER_SAVE_CMD_NAME)
public class RegisterSaveUserCommand implements ServletCommand {
    private UserService userService;
    private RegisterDataValidator validator;
    private CryptographyManager cryptographyManager;

    public RegisterSaveUserCommand(UserService userService, CryptographyManager cryptographyManager) {
        this.userService = userService;
        this.cryptographyManager = cryptographyManager;
        validator = new RegisterDataValidator(userService);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        Map<String, String> parsedParameters = parseParameters(req);
        try {
            ValidationResult validationResult = validator.validate(parsedParameters);
            resetInvalidFields(parsedParameters, validationResult);
            UserDto userDto = userFromParameters(parsedParameters);
            if (validationResult.isValid()) {
                userService.registerUser(userDto);
                resp.sendRedirect(req.getRequestURI() + "?commandName=" + LOGIN_VIEW_CMD_NAME);
            } else {
                req.setAttribute("user", userDto);
                log.error(validationResult);
                req.setAttribute("errors", validationResult);
                req.setAttribute(VIEW_NAME_REQ_PARAMETER, REGISTER_VIEW_CMD_NAME);
                req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
            }
        } catch (ValidationException | UserServiceException | ServletException | IOException e) {
            log.error("Failed to register user");
            throw new CommandException(e);
        }
    }

    private Map<String, String> parseParameters(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        result.put(LOGIN_ATTRIBUTE, request.getParameter(LOGIN_ATTRIBUTE));
        result.put(PASSWORD_ATTRIBUTE, request.getParameter(PASSWORD_ATTRIBUTE));
        result.put(REPEAT_PASSWORD_ATTRIBUTE, request.getParameter(REPEAT_PASSWORD_ATTRIBUTE));
        result.put(FIRST_NAME_ATTRIBUTE, request.getParameter(FIRST_NAME_ATTRIBUTE));
        result.put(LAST_NAME_ATTRIBUTE, request.getParameter(LAST_NAME_ATTRIBUTE));
        result.put(EMAIL_ATTRIBUTE, request.getParameter(EMAIL_ATTRIBUTE));
        result.put(PHONE_ATTRIBUTE, request.getParameter(PHONE_ATTRIBUTE));
        return result;
    }

    private UserDto userFromParameters(Map<String, String> parameters) {
        String password = parameters.get(PASSWORD_ATTRIBUTE);
        if (password != null) {
            password = cryptographyManager.encrypt(password);
        }
        ContactDto contactDto = ContactDto.builder()
                .firstName(parameters.get(FIRST_NAME_ATTRIBUTE))
                .lastName(parameters.get(LAST_NAME_ATTRIBUTE))
                .email(parameters.get(EMAIL_ATTRIBUTE))
                .phone(parameters.get(PHONE_ATTRIBUTE))
                .build();
        return UserDto.builder()
                .login(parameters.get(LOGIN_ATTRIBUTE))
                .password(password)
                .contacts(new ArrayList<>(Collections.singletonList(contactDto)))
                .build();
    }

    private void resetInvalidFields(Map<String, String> parameters, ValidationResult validationResult) {
        for (String parameter : parameters.keySet()) {
            if (validationResult.containsError(parameter)) {
                parameters.put(parameter, null);
            }
        }
    }
}