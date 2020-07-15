package by.training.dish;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.order.OrderDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static by.training.application.ApplicationConstants.DELETE_DISH_FROM_ORDER_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = DELETE_DISH_FROM_ORDER_CMD_NAME)
public class DeleteDishFromOrderCommand implements ServletCommand {
    private DishService dishService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            HttpSession session = req.getSession(false);
            if (session == null) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                return;
            }
            Object orderObject = session.getAttribute("order");
            if (orderObject instanceof OrderDto) {
                String dishIdString = req.getParameter("dishId");
                long dishId = Long.parseLong(dishIdString);
                DishDto toDelete = dishService.getDish(dishId);

                OrderDto orderDto = (OrderDto) orderObject;
                List<DishDto> dishList = orderDto.getOrderDishes();
                dishList.remove(toDelete);
                //session.setAttribute("orderObject", dishList);
            }
            resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                    ApplicationConstants.VIEW_SHOPPING_BASKET_CMD_NAME);
        } catch (IOException | DishServiceException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
