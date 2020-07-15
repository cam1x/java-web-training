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

import static by.training.application.ApplicationConstants.SAVE_CONTACT_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = SAVE_CONTACT_CMD_NAME)
public class SaveContactCommand implements ServletCommand {
    private ContactService contactService;

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
            List<ContactDto> userContacts = userDto.getContacts();
            String firstName = req.getParameter("contact.firstName");
            String lastName = req.getParameter("contact.lastName");
            String email = req.getParameter("contact.email");
            String phone = req.getParameter("contact.phone");
            ContactDto contactDto = ContactDto.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .phone(phone)
                    .userId(userDto.getUserId())
                    .build();
            contactService.saveContact(contactDto);
            userContacts.add(contactDto);
            resp.sendRedirect(req.getRequestURI() + "?commandName=" + ApplicationConstants.VIEW_USER_PROFILE_CMD_NAME);
        } catch (IOException | ContactServiceException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
