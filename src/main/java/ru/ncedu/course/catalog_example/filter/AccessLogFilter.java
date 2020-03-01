package ru.ncedu.course.catalog_example.filter;

import ru.ncedu.course.catalog_example.service.JourneyBean;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter(urlPatterns = {"*"})
public class AccessLogFilter implements Filter {
    @Inject
    private JourneyBean journeyBean;

    private final static Logger LOGGER = Logger.getLogger(AccessLogFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            String url = ((HttpServletRequest)request).getRequestURL().toString();
            String params = ((HttpServletRequest)request).getQueryString();
            LOGGER.info("Client " + request.getRemoteAddr() + " opened " + url);
            journeyBean.addUrl(url, params);
        }
        chain.doFilter(request, response);
    }
}
