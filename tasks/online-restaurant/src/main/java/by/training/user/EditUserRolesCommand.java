package by.training.user;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.role.RoleDto;
import by.training.role.RoleService;
import by.training.role.RoleServiceException;
import by.training.util.SelfOperationManager;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static by.training.application.ApplicationConstants.EDIT_USER_ROLES_CMD_NAME;

@Log4j
@Bean(name = EDIT_USER_ROLES_CMD_NAME)
@AllArgsConstructor
public class EditUserRolesCommand implements ServletCommand {
    private RoleService roleService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            boolean isSelfOperation = SelfOperationManager.isSelfOperation(req);
            if (isSelfOperation) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                return;
            }
            List<RoleDto> allRoles = roleService.findAllRoles();
            String userIdString = req.getParameter("userId");
            long userId = Long.parseLong(userIdString);
            List<RoleDto> userRoles = roleService.findUserRoles(userId);
            for (RoleDto role : allRoles) {
                boolean isSelected = req.getParameter(role.getRole()) != null;
                if (isSelected && !userRoles.contains(role)) {
                    roleService.assignRoles(userId, role.getId());
                }
                if (!isSelected && userRoles.contains(role)) {
                    roleService.deleteUserRole(userId, role.getId());
                }
            }
            resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                    ApplicationConstants.VIEW_ALL_USERS_CMD_NAME);
        } catch (IOException | RoleServiceException e) {
            log.error("Something went wrong...", e);
            throw new CommandException(e);
        }
    }
}
