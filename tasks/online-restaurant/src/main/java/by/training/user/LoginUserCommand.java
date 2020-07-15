package by.training.user;

import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.security.SecurityService;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.application.ApplicationConstants.*;

@Log4j
@Bean(name = LOGIN_CMD_NAME)
public class LoginUserCommand implements ServletCommand {
    private SecurityService securityService;

    public LoginUserCommand(UserService userService) {
        securityService = SecurityService.getInstance(userService);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            boolean isLogged = (securityService.login(req) != null);
            if (isLogged) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" + LOGIN_SUCCESS_CMD_NAME);
            } else {
                req.setAttribute("loginFailed", "true");
                req.setAttribute(VIEW_NAME_REQ_PARAMETER, LOGIN_VIEW_CMD_NAME);
                req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
            }
        } catch (IOException | ServletException | UserServiceException e) {
            log.error("Failed to login user");
            throw new CommandException(e);
        }
    }
}