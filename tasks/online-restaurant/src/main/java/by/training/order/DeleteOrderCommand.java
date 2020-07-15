package by.training.order;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.application.ApplicationConstants.DELETE_ORDER_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = DELETE_ORDER_CMD_NAME)
public class DeleteOrderCommand implements ServletCommand {
    private OrderService orderService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String orderIdString = req.getParameter("orderId");
        long orderId = Long.parseLong(orderIdString);
        try {
            orderService.deleteOrder(orderId);
            resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                    ApplicationConstants.VIEW_ORDER_HISTORY_CMD_NAME);
        } catch (OrderServiceException | IOException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
