package com.user.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.user.dao.*;
import com.user.listener.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName=request.getParameter("userName");
        String passWord=request.getParameter("passWord");
        String inputCode=request.getParameter("inputCode");
        String code=(String)request.getSession().getAttribute("authCode");
        if(userName==null||userName.trim().length()==0|| passWord==null||passWord.trim().length()==0||
                inputCode==null||inputCode.trim().length()==0 ||code==null||code.trim().length()==0){//表单信息输入完整否？
            request.getSession().setAttribute("errorMessage","请输入完整信息！");
            response.sendRedirect("login.jsp");

        }else if(!code.equals(inputCode)){//验证码正确否？
            request.getSession().setAttribute("errorMessage","验证码输入有误，请重新输入！");
            response.sendRedirect("login.jsp");
        }
        else{
            try {
                Connection con=DBConnection.getConnection();
                PreparedStatement ps=null;
                ResultSet rs=null;
                String sql="select * from user WHERE userName =? and passWord=?";
                ps=con.prepareStatement(sql);
                ps.setString(1,userName);
                ps.setString(2,passWord);
                rs=ps.executeQuery();
                if(rs.next()){
                   if(UserSessionListener.isUsernameInActiveSessions(userName)){
                       HttpSession oldSession=UserSessionListener.getActiveSessions().get(userName);
                       oldSession.invalidate();
                   }
                   HttpSession session=request.getSession();

                   session.setAttribute("userName",userName);
                    session.setAttribute("passWord",passWord);
                    session.setAttribute("gender",rs.getString("gender"));
                    session.setAttribute("email",rs.getString("email"));
                    session.setAttribute("profileImage",rs.getString("pic"));
                    UserSessionListener.addActiveMap(userName,session);//先删旧才能加新的，保证同一个用户的会话只能存在一个
                    if(userName.equals("admin"))
                        response.sendRedirect("admin.jsp");
                    else
                        response.sendRedirect("user.jsp");
                }
                else {
                    request.getSession().setAttribute("errorMessage","账户信息输入有误，请重新输入！");
                    response.sendRedirect("login.jsp");
                }
                DBConnection.release(rs,ps,con);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }



        }
    }
}
