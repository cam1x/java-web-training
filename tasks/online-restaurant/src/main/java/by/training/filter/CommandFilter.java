package by.training.filter;

import by.training.application.ApplicationConstants;
import by.training.security.SecurityContext;
import by.training.user.UserDto;
import lombok.extern.log4j.Log4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j
@WebFilter(servletNames = {"app"}, filterName = "cmd_filter")
public class CommandFilter implements Filter {
    private SecurityContext securityContext;

    public CommandFilter() {
        securityContext = SecurityContext.getInstance();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String commandName = request.getParameter("commandName");
        if (commandName == null || commandName.isEmpty()) {
            chain.doFilter(req, resp);
            return;
        }

        HttpSession session = req.getSession(false);
        boolean canExecute;
        if (session != null) {
            UserDto userDto = (UserDto) session.getAttribute("user");
            canExecute = securityContext.canExecute(commandName, userDto);
        } else {
            canExecute = securityContext.canExecute(commandName);
        }

        if (canExecute) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getRequestURI() + "?commandName=" +
                    ApplicationConstants.ACTION_PROHIBITED_CMD_NAME);
        }
    }

    @Override
    public void destroy() {

    }
}
