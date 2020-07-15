package by.training.user;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.security.SecurityService;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.training.application.ApplicationConstants.LOGOUT_CND_NAME;

@Log4j
@Bean(name = LOGOUT_CND_NAME)
public class LogoutUserCommand implements ServletCommand {
    private UserService userService;
    private SecurityService securityService;

    public LogoutUserCommand(UserService userService) {
        this.userService = userService;
        securityService = SecurityService.getInstance(userService);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        HttpSession session = req.getSession(false);
        UserDto userDto = (UserDto) session.getAttribute("user");
        boolean isLoggedOut = securityService.logout(userDto);
        try {
            if (isLoggedOut) {
                resp.sendRedirect(req.getRequestURI());
            } else {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
            }
        } catch (IOException e) {
            log.error("Failed to log out user");
            throw new CommandException(e);
        }
    }
}