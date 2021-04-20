package com.scs.filter;

import javax.imageio.spi.ServiceRegistry;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Userfilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        String username = (String) session.getAttribute("userInformation");
        if (username!=null) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            res.sendRedirect("http://localhost:8080/SCS/index.jsp");
        }
    }
    @Override
    public void destroy() {
    }
}
