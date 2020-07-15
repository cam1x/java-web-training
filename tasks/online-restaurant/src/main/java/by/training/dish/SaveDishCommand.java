package by.training.dish;

import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.util.ImageManager;
import by.training.validator.SaveDishValidator;
import by.training.validator.ValidationException;
import by.training.validator.ValidationResult;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static by.training.application.ApplicationConstants.*;

@Log4j
@Bean(name = SAVE_DISH_CMD_NAME)
public class SaveDishCommand implements ServletCommand {
    private DishService dishService;
    private SaveDishValidator validator;

    public SaveDishCommand(DishService dishService) {
        this.dishService = dishService;
        validator = new SaveDishValidator();
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        Map<String, String> parsedParameters = parseParameters(req);
        try {
            ValidationResult validationResult = validator.validate(parsedParameters);
            resetInvalidFields(parsedParameters, validationResult);
            DishDto dishDto = dishFromParameters(parsedParameters);
            dishDto.setPhoto(getPhoto(req));
            if (validationResult.isValid() && dishDto.getPhoto() != null) {
                log.error(dishDto.getName());
                dishService.saveDish(dishDto);
                resp.sendRedirect(req.getRequestURI() + "?commandName=" + SHOW_MENU_CMD_NAME);
            } else {
                req.setAttribute("error", validationResult);
                req.setAttribute("viewName", SHOW_MENU_CMD_NAME);
                req.getRequestDispatcher("jsp/layout.jsp").forward(req, resp);
            }
        } catch (ValidationException | DishServiceException | IOException | ServletException e) {
            log.error("Failed to save dish");
            throw new CommandException(e);
        }
    }

    private Map<String, String> parseParameters(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        result.put(DISH_NAME_ATTRIBUTE, request.getParameter(DISH_NAME_ATTRIBUTE));
        result.put(DISH_PRICE_ATTRIBUTE, request.getParameter(DISH_PRICE_ATTRIBUTE));
        result.put(DISH_DESCRIPTION_ATTRIBUTE, request.getParameter(DISH_DESCRIPTION_ATTRIBUTE));
        return result;
    }

    private DishDto dishFromParameters(Map<String, String> parameters) {
        String priceString = parameters.get(DISH_PRICE_ATTRIBUTE);
        double price = 0;
        if (priceString != null && !priceString.isEmpty()) {
            price = Double.parseDouble(priceString);
        }

        return DishDto.builder()
                .name(parameters.get(DISH_NAME_ATTRIBUTE))
                .price(price)
                .description(parameters.get(DISH_DESCRIPTION_ATTRIBUTE))
                .build();
    }

    private byte[] getPhoto(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart(DISH_PHOTO_ATTRIBUTE);
        return ImageManager.getImageBytes(filePart);
    }

    private void resetInvalidFields(Map<String, String> parameters, ValidationResult validationResult) {
        for (String parameter : parameters.keySet()) {
            if (validationResult.containsError(parameter)) {
                parameters.put(parameter, null);
            }
        }
    }
}
