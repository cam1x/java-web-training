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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static by.training.application.ApplicationConstants.ADD_DISH_TO_ORDER_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = ADD_DISH_TO_ORDER_CMD_NAME)
public class AddDishToOrderCommand implements ServletCommand {
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
            DishDto dishDto = dishService.getDish(dishId);
            Object orderObject = session.getAttribute("order");
            OrderDto orderDto;
            if (orderObject instanceof OrderDto) {
                orderDto = (OrderDto) orderObject;
                List<DishDto> dishList = orderDto.getOrderDishes();
                Optional<DishDto> optionalDish = dishList.stream()
                        .filter(x -> x.getId() == dishDto.getId())
                        .findFirst();
                if (optionalDish.isPresent()) {
                    DishDto updatingDish = optionalDish.get();
                    updatingDish.setQuantity(updatingDish.getQuantity() + 1);
                } else {
                    dishList.add(dishDto);
                }
            } else {
                orderDto = OrderDto.builder()
                        .orderDishes(new ArrayList<>(Collections.singletonList(dishDto)))
                        .build();
            }
            session.setAttribute("order", orderDto);
            resp.sendRedirect(req.getRequestURI() + "?commandName=" + ApplicationConstants.SHOW_MENU_CMD_NAME);
        } catch (IOException | DishServiceException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
