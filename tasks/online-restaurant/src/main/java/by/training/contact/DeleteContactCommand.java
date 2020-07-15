package by.training.contact;

import by.training.application.ApplicationConstants;
import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static by.training.application.ApplicationConstants.DELETE_CONTACT_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = DELETE_CONTACT_CMD_NAME)
public class DeleteContactCommand implements ServletCommand {
    private ContactService contactService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            HttpSession session = req.getSession(false);
            String contactIdString = req.getParameter("contactId");
            long contactId = Long.parseLong(contactIdString);
            if (session == null) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                return;
            }
            UserDto userDto = (UserDto) session.getAttribute("user");
            List<ContactDto> userContacts = userDto.getContacts();
            if (userContacts.size() >= 2) {
                contactService.deleteContact(contactId);
                userDto.setContacts(userContacts.stream()
                        .filter(x -> x.getId() != contactId)
                        .collect(Collectors.toList()));
            }
            resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                    ApplicationConstants.VIEW_USER_PROFILE_CMD_NAME);
        } catch (ContactServiceException | IOException e) {
            log.error("Failed to delete contact");
            throw new CommandException(e);
        }
    }
}
