package by.training.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@WebFilter(servletNames = {"app"}, filterName = "lang_filter")
public class LangFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            String lang = request.getParameter("lang");
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            if (("en".equalsIgnoreCase(lang) || "ru".equalsIgnoreCase(lang))) {
                Cookie langCookie = new Cookie("lang", lang);
                langCookie.setPath(httpRequest.getContextPath());
                httpRequest.setAttribute("lang", lang);
                ((HttpServletResponse) response).addCookie(langCookie);
            } else {
                Optional<Cookie[]> cookies = Optional.ofNullable(httpRequest.getCookies());
                Cookie langCookie = cookies.map(Stream::of).orElse(Stream.empty())
                        .filter(cookie -> cookie.getName().equalsIgnoreCase("lang")).findFirst()
                        .orElse(new Cookie("lang", "en"));
                langCookie.setPath(httpRequest.getContextPath());
                httpRequest.setAttribute("lang", lang);
                ((HttpServletResponse) response).addCookie(langCookie);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
