package by.training.order;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.discount.DiscountService;
import by.training.dish.DishDto;
import by.training.dish.DishService;
import by.training.dish.DishServiceException;
import by.training.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static by.training.application.ApplicationConstants.VIEW_NAME_REQ_PARAMETER;
import static by.training.application.ApplicationConstants.VIEW_ORDER_HISTORY_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = VIEW_ORDER_HISTORY_CMD_NAME)
public class ViewOrderHistoryCommand implements ServletCommand {
    private OrderService orderService;
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
            double totalPrice = 0;
            UserDto userDto = (UserDto) session.getAttribute("user");
            List<OrderDto> orderList = orderService.findUserOrders(userDto.getUserId());
            for (OrderDto orderDto : orderList) {
                long orderId = orderDto.getId();
                double orderPrice = countTotalPrice(dishService.findOrderDishes(orderId));
                totalPrice += orderPrice;
                orderDto.setTotalPrice(orderPrice);
            }
            req.setAttribute("totalPrice", totalPrice);
            req.setAttribute("orders", orderList);
            req.setAttribute(VIEW_NAME_REQ_PARAMETER, VIEW_ORDER_HISTORY_CMD_NAME);
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        } catch (ServletException | IOException | OrderServiceException | DishServiceException e) {
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
