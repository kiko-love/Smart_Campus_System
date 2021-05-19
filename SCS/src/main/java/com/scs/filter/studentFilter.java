package com.scs.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class studentFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        String character = null;
        //获取当前角色
        if(role.equals("2")){
             character="student";
        }
        if(role.equals("1")){
             character="teacher";
        }
        if(role.equals("0")){
             character="admin";
        }
        if(character.equals("student")){
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else {
            response.sendRedirect("/user/"+character+".html");
        }
    }

    @Override
    public void destroy() {

    }
}
