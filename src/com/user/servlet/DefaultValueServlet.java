package com.user.servlet;

import com.user.bean.UserBean;
import com.user.dao.DBUtils;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/DefaultValueServlet")
public class DefaultValueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name=request.getParameter("oldname");
        JSONObject responseData=new JSONObject();
        try {
            DBUtils dbUtils=new DBUtils();
            UserBean userBean=dbUtils.QueryUser(name);
            response.setContentType("application/json;charset=UTF-8");
            responseData.put("name",userBean.getName());
            responseData.put("password",userBean.getPassword());
            response.getWriter().write(responseData.toString());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
