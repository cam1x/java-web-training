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
import java.util.Optional;

import static by.training.application.ApplicationConstants.EDIT_DISH_QUANTITY_CMD_NAME;
import static by.training.application.ApplicationConstants.VIEW_SHOPPING_BASKET_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = EDIT_DISH_QUANTITY_CMD_NAME)
public class EditDishQuantityCommand implements ServletCommand {
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
            String dishIdString = req.getParameter("dishId");
            long dishId = Long.parseLong(dishIdString);
            Object orderObject = session.getAttribute("order");
            if (orderObject instanceof OrderDto) {
                OrderDto orderDto = (OrderDto) orderObject;
                List<DishDto> dishList = orderDto.getOrderDishes();
                Optional<DishDto> optionalDish = dishList.stream()
                        .filter(x -> x.getId() == dishId)
                        .findFirst();
                if (!optionalDish.isPresent()) {
                    resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                            ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                    return;
                }
                DishDto updatingDish = optionalDish.get();
                String dishQuantity = req.getParameter("dish.quantity");
                long quantity;
                if (dishQuantity != null && !dishQuantity.isEmpty() &&
                        updatingDish.getQuantity() != (quantity = Long.parseLong(dishQuantity)) &&
                        quantity > 0) {
                    updatingDish.setQuantity(quantity);
                    dishService.updateDish(updatingDish);
                }
            }
            resp.sendRedirect(req.getRequestURI() + "?commandName=" + VIEW_SHOPPING_BASKET_CMD_NAME);
        } catch (DishServiceException | IOException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
