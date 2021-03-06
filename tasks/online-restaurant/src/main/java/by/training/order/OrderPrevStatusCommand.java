package by.training.order;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.order.state.OrderState;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.training.application.ApplicationConstants.ORDER_PREV_STATUS_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = ORDER_PREV_STATUS_CMD_NAME)
public class OrderPrevStatusCommand implements ServletCommand {
    private OrderService orderService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String orderIdString = req.getParameter("orderId");
        long orderId = Long.parseLong(orderIdString);
        try {
            OrderDto changingOrder = orderService.getById(orderId);
            String statusBeforeChange = changingOrder.getStatus();
            OrderState orderState = changingOrder.getState();
            orderState.prev(changingOrder);
            String statusAfterChange = changingOrder.getState().getStatus();
            if (!statusBeforeChange.equals(statusAfterChange)) {
                changingOrder.setStatus(statusAfterChange);
                orderService.updateOrder(changingOrder);
            }
            resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                    ApplicationConstants.VIEW_ALL_ORDERS_CMD_NAME);
        } catch (OrderServiceException | IOException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
