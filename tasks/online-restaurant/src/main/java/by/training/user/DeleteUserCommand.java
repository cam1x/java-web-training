package by.training.user;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.security.SecurityService;
import by.training.util.SelfOperationManager;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.training.application.ApplicationConstants.DELETE_CMD_NAME;

@Log4j
@Bean(name = DELETE_CMD_NAME)
public class DeleteUserCommand implements ServletCommand {
    private UserService userService;
    private SecurityService securityService;

    public DeleteUserCommand(UserService userService) {
        this.userService = userService;
        this.securityService = SecurityService.getInstance(userService);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            boolean isSelfOperation = SelfOperationManager.isSelfOperation(req);
            if (isSelfOperation) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                return;
            }
            String userIdString = req.getParameter("userId");
            long userId = Long.parseLong(userIdString);
            UserDto userDto = userService.getById(userId);
            securityService.logout(userDto);
            userService.delete(userDto.getUserId());
            resp.sendRedirect(req.getRequestURI() + "?commandName=" + ApplicationConstants.VIEW_ALL_USERS_CMD_NAME);
        } catch (UserServiceException | IOException e) {
            log.error("Failed to delete user");
            throw new CommandException(e);
        }
    }
}
