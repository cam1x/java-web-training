package by.training.application;

import by.training.command.CommandException;
import by.training.command.ServletCommand;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
@MultipartConfig
@WebServlet(urlPatterns = "/", loadOnStartup = 1, name = "app")
public class ApplicationServlet extends HttpServlet {
    private static final long serialVersionUID = 3014413763338600865L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter("commandName");
        if (commandName == null) {
            req.getRequestDispatcher("jsp/layout.jsp").forward(req, resp);
            return;
        }

        try {
            ServletCommand command = ApplicationContext.getInstance().getBean(commandName);
            command.execute(req, resp);
        } catch (CommandException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}