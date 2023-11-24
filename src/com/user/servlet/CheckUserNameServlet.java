package com.user.servlet;

import com.user.dao.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CheckUserNameServlet")
public class CheckUserNameServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name=request.getParameter("username");
        DBUtils dbUtils= null;
        try {
            dbUtils = new DBUtils();
           if(dbUtils.ContainUser(name))
                response.getWriter().write("error");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
