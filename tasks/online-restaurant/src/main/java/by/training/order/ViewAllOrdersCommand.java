package by.training.order;

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
@AllArgsConstructor
@Bean(name = VIEW_ALL_ORDERS_CMD_NAME)
public class ViewAllOrdersCommand implements ServletCommand {
    private OrderService orderService;
    private DishService dishService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            List<OrderDto> allOrders = orderService.findAll();
            for (OrderDto orderDto : allOrders) {
                long orderId = orderDto.getId();
                orderDto.setTotalPrice(countTotalPrice(dishService.findOrderDishes(orderId)));
            }
            req.setAttribute("orders", allOrders);
            req.setAttribute(VIEW_NAME_REQ_PARAMETER, VIEW_ALL_ORDERS_CMD_NAME);
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        } catch (OrderServiceException | DishServiceException | IOException | ServletException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }

    private double countTotalPrice(List<DishDto> dishList) {
        return dishList.stream()
                .mapToDouble(x -> x.getQuantity() * x.getPrice())
                .sum();
    }
}
