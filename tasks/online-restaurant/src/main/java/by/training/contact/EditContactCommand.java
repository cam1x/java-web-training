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
import java.util.Optional;

import static by.training.application.ApplicationConstants.EDIT_CONTACT_CMD_NAME;

@Log4j
@AllArgsConstructor
@Bean(name = EDIT_CONTACT_CMD_NAME)
public class EditContactCommand implements ServletCommand {
    private ContactService contactService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String contactIdString = req.getParameter("contactId");
        long contactId = Long.parseLong(contactIdString);
        HttpSession session = req.getSession(false);
        try {
            if (session == null) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                return;
            }
            UserDto userDto = (UserDto) session.getAttribute("user");
            List<ContactDto> userContacts = userDto.getContacts();
            Optional<ContactDto> optionalContact = userContacts.stream()
                    .filter(x -> x.getId() == contactId)
                    .findFirst();
            if (!optionalContact.isPresent()) {
                resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                        ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
                return;
            }
            ContactDto contactDto = optionalContact.get();
            String firstName = req.getParameter("contact.firstName");
            String lastName = req.getParameter("contact.lastName");
            String email = req.getParameter("contact.email");
            String phone = req.getParameter("contact.phone");
            if (!contactDto.getFirstName().equals(firstName) || !contactDto.getLastName().equals(lastName) ||
                    !contactDto.getEmail().equals(email) || !contactDto.getPhone().equals(phone)) {
                contactDto.setFirstName(firstName);
                contactDto.setLastName(lastName);
                contactDto.setEmail(email);
                contactDto.setPhone(phone);
                contactService.updateContact(contactDto);
            }
            resp.sendRedirect(req.getRequestURI() + "?commandName=" + ApplicationConstants.VIEW_USER_PROFILE_CMD_NAME);
        } catch (IOException | ContactServiceException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
