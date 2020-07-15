package by.training.dish;

import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.dish.DishDto;
import by.training.dish.DishService;
import by.training.dish.DishServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.training.application.ApplicationConstants.*;

@Log4j
@Bean(name = SHOW_MENU_CMD_NAME)
@AllArgsConstructor
public class ShowMenuCommand implements ServletCommand {
    private DishService dishService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            List<DishDto> dishes = dishService.findAllDishes();
            req.setAttribute("dishes", dishes);
            req.setAttribute(VIEW_NAME_REQ_PARAMETER, SHOW_MENU_CMD_NAME);
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        } catch (ServletException | IOException | DishServiceException e) {
            log.error("Failed to show restaurant contacts");
            throw new CommandException(e);
        }
    }
}