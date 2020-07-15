package by.training.dish;

import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.util.ImageManager;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Arrays;

import static by.training.application.ApplicationConstants.*;

@Log4j
@AllArgsConstructor
@Bean(name = EDIT_DISH_CMD_NAME)
public class EditDishCommand implements ServletCommand {
    private DishService dishService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            String dishIdString = req.getParameter("dishId");
            long dishId = Long.parseLong(dishIdString);
            DishDto updatingDish = dishService.getDish(dishId);
            boolean isChanged = updateDish(updatingDish, req);
            byte[] photo = getPhoto(req);
            if (!Arrays.equals(updatingDish.getPhoto(), photo)) {
                isChanged = true;
                updatingDish.setPhoto(photo);
            }
            if (isChanged) {
                dishService.updateDish(updatingDish);
            }
            resp.sendRedirect(req.getRequestURI() + "?commandName=" + SHOW_MENU_CMD_NAME);
        } catch (DishServiceException | ServletException | IOException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }

    private boolean updateDish(DishDto updatingDish, HttpServletRequest req) {
        boolean isUpdated = false;
        String dishName = req.getParameter("dish.name");
        if (!updatingDish.getName().equals(dishName)) {
            isUpdated = true;
            updatingDish.setName(dishName);
        }
        String dishDescription = req.getParameter("dish.description");
        if (!updatingDish.getDescription().equals(dishDescription)) {
            isUpdated = true;
            updatingDish.setDescription(dishDescription);
        }
        String dishPrice = req.getParameter("dish.price");
        double priceValue;
        if (dishPrice != null && !dishPrice.isEmpty() &&
                updatingDish.getPrice() != (priceValue = Double.parseDouble(dishPrice)) &&
                priceValue > 0) {
            isUpdated = true;
            updatingDish.setPrice(priceValue);
        }
        return isUpdated;
    }

    private byte[] getPhoto(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart(DISH_PHOTO_ATTRIBUTE);
        return ImageManager.getImageBytes(filePart);
    }
}
