package by.training.user;

import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.application.ApplicationConstants.*;

@Log4j
@Bean(name = LOGIN_SUCCESS_CMD_NAME)
public class LoginSuccessCommand implements ServletCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            req.setAttribute(VIEW_NAME_REQ_PARAMETER, LOGIN_SUCCESS_CMD_NAME);
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        } catch (IOException | ServletException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
