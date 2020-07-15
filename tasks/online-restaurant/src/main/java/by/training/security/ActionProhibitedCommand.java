package by.training.security;

import by.training.command.CommandException;
import by.training.command.ServletCommand;
import by.training.core.Bean;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.application.ApplicationConstants.ACTION_PROHIBITED_CMD_NAME;
import static by.training.application.ApplicationConstants.VIEW_NAME_REQ_PARAMETER;

@Log4j
@AllArgsConstructor
@Bean(name = ACTION_PROHIBITED_CMD_NAME)
public class ActionProhibitedCommand implements ServletCommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            req.setAttribute(VIEW_NAME_REQ_PARAMETER, ACTION_PROHIBITED_CMD_NAME);
            req.getRequestDispatcher("/jsp/layout.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error(e.getMessage(), e);
            throw new CommandException(e);
        }
    }
}
