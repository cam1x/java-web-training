package by.training.discount;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.user.UserDto;
import by.training.user.UserService;
import by.training.user.UserServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.application.ApplicationConstants.EDIT_DISCOUNT_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = EDIT_DISCOUNT_CMD_NAME)
public class EditDiscountCommand implements ServletCommand {
    private UserService userService;
    private DiscountService discountService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String userIdString = req.getParameter("userId");
        long userId = Long.parseLong(userIdString);
        try {
            UserDto userDto = userService.getById(userId);
            DiscountDto changingDiscount = discountService.getById(userDto.getDiscountId());
            String discountAmount = req.getParameter("discount.amount");
            double amount;
            if (discountAmount != null && !discountAmount.isEmpty()
                    && (amount = Double.parseDouble(discountAmount)) > 0 && amount < 100) {
                changingDiscount.setAmount(amount);
                discountService.updateDiscount(changingDiscount);
            }
            resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                    ApplicationConstants.VIEW_ALL_ORDERS_CMD_NAME);
        } catch (DiscountServiceException | UserServiceException | IOException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
