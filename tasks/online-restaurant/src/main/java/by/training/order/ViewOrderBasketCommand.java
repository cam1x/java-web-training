package by.training.order;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.discount.DiscountDto;
import by.training.discount.DiscountService;
import by.training.discount.DiscountServiceException;
import by.training.dish.DishDto;
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
import static by.training.application.ApplicationConstants.VIEW_SHOPPING_BASKET_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = VIEW_SHOPPING_BASKET_CMD_NAME)
public class ViewOrderBasketCommand implements ServletCommand {
    private DiscountService discountService;

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
                OrderDto orderDto = (OrderDto) orderObject;
                List<DishDto> dishList = orderDto.getOrderDishes();
                UserDto userDto = (UserDto) session.getAttribute("user");
                double totalPrice = dishList.stream()
                        .mapToDouble(x -> x.getQuantity() * x.getPrice())
                        .sum();

                DiscountDto discountDto = discountService.getById(userDto.getDiscountId());
                double discount = discountDto.getAmount();
                req.setAttribute("discount", discount);
                orderDto.setTotalPrice((1 - discount / 100) * totalPrice);
            }
            req.setAttribute(VIEW_NAME_REQ_PARAMETER, VIEW_SHOPPING_BASKET_CMD_NAME);
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        } catch (IOException | ServletException | DiscountServiceException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
