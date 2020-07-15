package by.training.user;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.discount.DiscountDto;
import by.training.discount.DiscountService;
import by.training.discount.DiscountServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.training.application.ApplicationConstants.VIEW_NAME_REQ_PARAMETER;
import static by.training.application.ApplicationConstants.VIEW_USER_PROFILE_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = VIEW_USER_PROFILE_CMD_NAME)
public class ViewUserProfileCommand implements ServletCommand {
    private UserService userService;
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
            UserDto userDto = (UserDto) session.getAttribute("user");
            UserDto updatedUser = userService.getById(userDto.getUserId());
            if (!updatedUser.equals(userDto)) {
                session.removeAttribute("user");
                session.setAttribute("user", updatedUser);
            }

            req.setAttribute(VIEW_NAME_REQ_PARAMETER, VIEW_USER_PROFILE_CMD_NAME);
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        } catch (ServletException | IOException | UserServiceException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
