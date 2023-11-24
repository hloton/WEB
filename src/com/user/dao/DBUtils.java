package com.user.dao;

import com.user.bean.UserBean;
import java.sql.*;
import java.util.HashMap;

public class DBUtils {
    private HashMap<String, UserBean> users=new HashMap<String,UserBean>();
    private Connection con=null;
    private Statement st=null;
    private  PreparedStatement ps=null;
    private ResultSet rs=null;
    public DBUtils() throws SQLException, ClassNotFoundException {
        con=DBConnection.getConnection();
        String sql="SELECT * FROM user";
        st=con.createStatement();
        rs=st.executeQuery(sql);
        String name="";
        while(rs.next()){
            UserBean user=new UserBean();
            name=rs.getString("userName");
            user.setName(name);
            user.setPassword(rs.getString("passWord"));
            users.put(name,user);
        }
        DBConnection.release(rs,st,con);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//       HashMap<String,UserBean> aaa= new DBUtils().users;
//        for (UserBean value : aaa.values()) {
//            System.out.println(value.getName());
//            System.out.println(value.getPassword());
//            System.out.println();
//        }
        //if(new DBUtils().ContainUser("admin")) System.out.println("11111");
        //DBUtils dbUtils=new DBUtils();
        //dbUtils.DeleteUser("jack");
//        UserBean userBean=dbUtils.QueryUser("kiki");
//        System.out.println(userBean.getName());
//        System.out.println(userBean.getPassword());
    }
    public HashMap<String, UserBean> getUsers() {
        return users;
    }
    public boolean ContainUser(String name){
        return users.containsKey(name);
    }
    //---------------------增--------------------
    public boolean InsertUser(String name,String password) throws SQLException, ClassNotFoundException {
        if(ContainUser(name))return false;
        else{
            con=DBConnection.getConnection();
            UserBean user=new UserBean();
            user.setName(name);
            user.setPassword(password);
            users.put(name,user);
            String sql="INSERT INTO user(userName,passWord)"+"VALUES(?,?)";
            ps=con.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,password);
            ps.executeUpdate();
            DBConnection.release(rs,ps,con);
            return true;
        }
    }

    //---------------------删--------------------
    public int DeleteUser(String name) throws SQLException, ClassNotFoundException {
        con=DBConnection.getConnection();
        users.remove(name);
        String sql="delete from user where userName=?";
        ps=con.prepareStatement(sql);
        ps.setString(1,name);
        int row=ps.executeUpdate();
        DBConnection.release(rs,ps,con);
        return row;
    }
    //---------------------改-------------------
    public boolean UpdateUser(String oldname,String name,String password) throws SQLException, ClassNotFoundException {
        if(!oldname.equals(name)){
            //换新名字了
            if(ContainUser(name))
                return false;
            else{
                con=DBConnection.getConnection();
                String sql="UPDATE user set userName=?,passWord=? WHERE userName=?";
                ps=con.prepareStatement(sql);
                ps.setString(1,name);
                ps.setString(2,password);
                ps.setString(3,oldname);
                ps.executeUpdate();
                DBConnection.release(rs,ps,con);
                return true;
            }
        }
        else{//名字没改，只改了密码
            con=DBConnection.getConnection();
            String sql="UPDATE user set passWord=? WHERE userName=?";
            ps=con.prepareStatement(sql);
            ps.setString(1,password);
            ps.setString(2,name);
            ps.executeUpdate();
            DBConnection.release(rs,ps,con);
            return true;
        }
    }
    public void UpdatePic(String name,String uploadPath) throws SQLException, ClassNotFoundException {
        con=DBConnection.getConnection();
        String sql="UPDATE user set pic=? WHERE userName=?";
        ps=con.prepareStatement(sql);
        ps.setString(1,uploadPath);
        ps.setString(2,name);
        ps.executeUpdate();
        DBConnection.release(rs,ps,con);
    }
//---------------------查-------------------
public UserBean QueryUser(String name) throws SQLException, ClassNotFoundException {
    con=DBConnection.getConnection();
    String sql="SELECT * FROM user WHERE userName=?";
    ps=con.prepareStatement(sql);
    ps.setString(1,name);
    rs=ps.executeQuery();
    UserBean user=new UserBean();
    while (rs.next()){
        user.setName(rs.getString("userName"));
        user.setPassword(rs.getString("passWord"));
    }
    DBConnection.release(rs,ps,con);
    return user;
}

}

