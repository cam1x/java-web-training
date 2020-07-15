package by.training.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface ServletCommand {
    void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException;
}
