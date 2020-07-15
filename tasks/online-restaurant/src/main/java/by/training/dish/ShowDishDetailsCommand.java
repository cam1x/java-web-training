package by.training.dish;

import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.training.application.ApplicationConstants.*;

@Log4j
@AllArgsConstructor
@Bean(name = SHOW_DISH_DETAILS_CMD_NAME)
public class ShowDishDetailsCommand implements ServletCommand {
    private DishService dishService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String dishIdString = req.getParameter("dishId");
        long dishId = Long.parseLong(dishIdString);
        try {
            DishDto dishDto = dishService.getDish(dishId);
            req.setAttribute("dish", dishDto);
            req.setAttribute(VIEW_NAME_REQ_PARAMETER, SHOW_DISH_DETAILS_CMD_NAME);
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        } catch (DishServiceException | IOException | ServletException e) {
            log.error("Failed to prepare details of dish by id: " + dishId);
            throw new CommandException(e);
        }
    }
}
