package com.user.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/CheckSessionServlet")
public class CheckSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);//如果不存在则返回null
        if (session==null||session.getAttribute("userName")==null ) {
            // 设置响应内容为 "invalid"
            response.setContentType("text/plain"); // 设置响应内容类型为纯文本
            response.setCharacterEncoding("UTF-8"); // 设置字符编码为UTF-8
            response.getWriter().write("invalid"); // 写入 "invalid" 到响应中
        }
    }
}
