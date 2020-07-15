package by.training.dish;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.application.ApplicationConstants.DELETE_DISH_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = DELETE_DISH_CMD_NAME)
public class DeleteDishCommand implements ServletCommand {
    private DishService dishService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String dishIdString = req.getParameter("dishId");
        long dishId = Long.parseLong(dishIdString);
        try {
            dishService.deleteDish(dishId);
            resp.sendRedirect(req.getRequestURI() + "?commandName=" + ApplicationConstants.SHOW_MENU_CMD_NAME);
        } catch (DishServiceException | IOException e) {
            log.error("Failed to delete dish with id=" + dishId);
            throw new CommandException(e);
        }
    }
}
