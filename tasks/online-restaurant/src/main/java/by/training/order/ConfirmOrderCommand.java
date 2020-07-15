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
import by.training.wallet.WalletDto;
import by.training.wallet.WalletService;
import by.training.wallet.WalletServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static by.training.application.ApplicationConstants.*;

@Log4j
@AllArgsConstructor
@Bean(name = CONFIRM_ORDER_CMD_NAME)
public class ConfirmOrderCommand implements ServletCommand {
    private static final String DATE_REGEX = "^20[1,2][0-9]-[0,1][0-9]-([0-3])?[0-9]$";
    private OrderService orderService;
    private WalletService walletService;
    private DiscountService discountService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        HttpSession session = req.getSession(false);
        try {
            if (session == null) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                return;
            }
            Object orderObject = session.getAttribute("order");
            if (orderObject instanceof OrderDto) {
                OrderDto orderFromSession = (OrderDto) orderObject;
                UserDto customer = (UserDto) session.getAttribute("user");
                if (customer.isLocked()) {
                    forwardWithError(req, resp, "userLocked");
                    return;
                }

                List<DishDto> dishList = orderFromSession.getOrderDishes();
                DiscountDto discountDto = discountService.getById(customer.getDiscountId());
                boolean canBePayed = operateWithPayment(req, customer, dishList, discountDto.getAmount());

                if (!canBePayed) {
                    forwardWithError(req, resp, "notEnoughMoney");
                    return;
                }

                Optional<OrderDto> optionalOrder = buildOrderDto(req, resp);
                if (!optionalOrder.isPresent()) {
                    forwardWithError(req, resp, "wrongDate");
                    return;
                }

                OrderDto orderDto = optionalOrder.get();
                long customerId = customer.getUserId();
                orderDto.setCustomerId(customerId);
                orderDto.setOrderDishes(dishList);
                orderService.saveOrder(orderDto);
                session.removeAttribute("order");
            }
            resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                    ORDER_SUCCESS_CMD_NAME);
        } catch (IOException | ParseException | OrderServiceException | WalletServiceException | ServletException | DiscountServiceException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String error)
            throws ServletException, IOException {
        req.setAttribute("error", error);
        req.setAttribute(VIEW_NAME_REQ_PARAMETER, VIEW_SHOPPING_BASKET_CMD_NAME);
        req.getRequestDispatcher("jsp/layout.jsp").forward(req, resp);
    }

    private Optional<OrderDto> buildOrderDto(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
        String bookingDateString = req.getParameter("order.bookingDate");
        if (!bookingDateString.matches(DATE_REGEX)) {
            return Optional.empty();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date bookingDate = dateFormat.parse(bookingDateString);
        Date orderDate = new Date();
        return Optional.of(OrderDto.builder()
                .orderDate(orderDate)
                .bookingDate(bookingDate)
                .build());
    }

    private boolean operateWithPayment(HttpServletRequest req, UserDto customer,
                                       List<DishDto> dishList, double discount) throws WalletServiceException {
        String paymentWalletName = req.getParameter("paymentWallet");
        WalletDto paymentWallet = customer.getWallets()
                .stream()
                .filter(x -> x.getName().equals(paymentWalletName))
                .findFirst()
                .orElse(new WalletDto());
        double totalPrice = dishList.stream()
                .mapToDouble(x -> x.getQuantity() * x.getPrice())
                .sum();
        double toPay = (1 - discount / 100) * totalPrice;
        boolean canBePayed = paymentWallet.getAmount() >= toPay;
        if (canBePayed) {
            paymentWallet.setAmount(paymentWallet.getAmount() - toPay);
            walletService.updateWallet(paymentWallet);
        }
        return canBePayed;
    }
}