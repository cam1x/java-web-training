package by.training.user;

import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import by.training.util.ImageManager;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Arrays;

import static by.training.application.ApplicationConstants.*;

@Log4j
@AllArgsConstructor
@Bean(name = CHANGE_USER_AVATAR_CMD_NAME)
public class ChangeUserAvatarCommand implements ServletCommand {
    private UserService userService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String userIdString = req.getParameter("userId");
        long userId = Long.parseLong(userIdString);
        try {
            UserDto userDto = userService.getById(userId);
            byte[] photo = getPhoto(req);
            if (!Arrays.equals(photo, userDto.getAvatar())) {
                userDto.setAvatar(photo);
                userService.updateUser(userDto);
            }
            resp.sendRedirect(req.getRequestURI() + "?commandName=" + VIEW_USER_PROFILE_CMD_NAME);
        } catch (UserServiceException | IOException | ServletException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }

    private byte[] getPhoto(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart(AVATAR_ATTRIBUTE);
        return ImageManager.getImageBytes(filePart);
    }
}
