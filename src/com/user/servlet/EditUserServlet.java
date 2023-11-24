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

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name=request.getParameter("username");
        String type=request.getParameter("type");
        DBUtils dbUtils= null;
        try {
            dbUtils = new DBUtils();
            if(type.equals("delete")){
                if(dbUtils.DeleteUser(name)>0)
                    response.sendRedirect("admin.jsp");
                else
                    response.sendRedirect("fail.jsp");
            }
            else if(type.equals("query")){
                JSONObject jsonObject=new JSONObject();
                UserBean userBean=new UserBean();
                userBean=dbUtils.QueryUser(name);

                if(userBean.getName()!=null&&userBean.getPassword()!=null){
                    response.setContentType("application/json;charset=UTF-8");
                    jsonObject.put("name",userBean.getName());
                    jsonObject.put("password",userBean.getPassword());
                    response.getWriter().write(jsonObject.toString());
                }
                else{
                    response.getWriter().write("null");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String formType=request.getParameter("formType");
        String name=request.getParameter("username");
        String password=request.getParameter("password");
        if(formType!=null){
            try {
                DBUtils dbUtils=new DBUtils();
                if(formType.equals("add")){
                    if(dbUtils.InsertUser(name,password))
                        response.sendRedirect("admin.jsp");

                }
                else if(formType.equals("edit")){
                    String oldname=request.getParameter("oldname");
                    if(dbUtils.UpdateUser(oldname,name,password))
                        response.sendRedirect("admin.jsp");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}
