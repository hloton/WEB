package com.user.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns= {"/user.jsp","/admin.jsp"})
public class LoginCheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;
        String username=(String)httpServletRequest.getSession().getAttribute("userName");
        if(username!=null){
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else{
            httpServletResponse.sendRedirect("login.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
