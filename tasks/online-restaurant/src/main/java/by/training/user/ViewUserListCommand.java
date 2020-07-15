package by.training.user;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.role.RoleService;
import by.training.role.RoleServiceException;
import by.training.security.SecurityService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static by.training.application.ApplicationConstants.VIEW_ALL_USERS_CMD_NAME;
import static by.training.application.ApplicationConstants.VIEW_NAME_REQ_PARAMETER;

@Log4j
@AllArgsConstructor
@Bean(name = VIEW_ALL_USERS_CMD_NAME)
public class ViewUserListCommand implements ServletCommand {
    private UserService userService;
    private RoleService roleService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            HttpSession session = req.getSession(false);
            if (session == null) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                return;
            }
            final List<UserDto> allUsers = userService.getAllUsers();
            req.setAttribute("users", allUsers);
            req.setAttribute("roles", roleService.findAllRoles());
            req.setAttribute(VIEW_NAME_REQ_PARAMETER, VIEW_ALL_USERS_CMD_NAME);
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        } catch (ServletException | IOException | UserServiceException | RoleServiceException e) {
            log.error("Something went wrong...", e);
            throw new CommandException(e);
        }
    }
}