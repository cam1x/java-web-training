package by.training.user;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.util.SelfOperationManager;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.application.ApplicationConstants.UNLOCK_USER_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = UNLOCK_USER_CMD_NAME)
public class UnlockUserCommand implements ServletCommand {
    private UserService userService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String userIdString = req.getParameter("userId");
        long userId = Long.parseLong(userIdString);
        try {
            boolean isSelfOperation = SelfOperationManager.isSelfOperation(req);
            if (isSelfOperation) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                return;
            }
            UserDto userDto = userService.getById(userId);
            if (userDto.isLocked()) {
                userDto.setLocked(false);
                userService.updateUser(userDto);
            }
            resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                    ApplicationConstants.VIEW_ALL_USERS_CMD_NAME);
        } catch (UserServiceException | IOException e) {
            log.error("Failed to unlock user", e);
            throw new CommandException(e);
        }
    }
}
