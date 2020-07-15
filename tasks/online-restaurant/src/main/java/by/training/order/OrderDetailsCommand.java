package by.training.order;

import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.discount.DiscountDto;
import by.training.discount.DiscountService;
import by.training.discount.DiscountServiceException;
import by.training.user.UserDto;
import by.training.user.UserService;
import by.training.user.UserServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.application.ApplicationConstants.ORDER_DETAILS_CMD_NAME;
import static by.training.application.ApplicationConstants.VIEW_NAME_REQ_PARAMETER;

@Log4j
@AllArgsConstructor
@Bean(name = ORDER_DETAILS_CMD_NAME)
public class OrderDetailsCommand implements ServletCommand {
    private OrderService orderService;
    private UserService userService;
    private DiscountService discountService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String orderIdString = req.getParameter("orderId");
        long orderId = Long.parseLong(orderIdString);
        try {
            OrderDto orderDto = orderService.getById(orderId);
            long customerId = orderDto.getCustomerId();
            UserDto userDto = userService.getById(customerId);

            DiscountDto discountDto = discountService.getById(userDto.getDiscountId());
            double discountAmount = discountDto.getAmount();

            req.setAttribute("discount", discountAmount);
            req.setAttribute("user", userDto);
            req.setAttribute(VIEW_NAME_REQ_PARAMETER, ORDER_DETAILS_CMD_NAME);
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        } catch (OrderServiceException | ServletException | IOException | UserServiceException | DiscountServiceException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
