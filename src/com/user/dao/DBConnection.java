package com.user.dao;

import java.sql.*;

public class DBConnection {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        String JDBC_DRIVER="com.mysql.jdbc.Driver";
         String URL="jdbc:mysql://localhost:3306/DBJsp";
         String USER="root";
         String PASSWORD="123456";
         Class.forName(JDBC_DRIVER);
        Connection con=null;
        con=DriverManager.getConnection(URL,USER,PASSWORD);
        return con;
    }
    public static void closeConnection(Connection con){
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {e.printStackTrace();}
            con = null;
        }
    }
    public static  void release(ResultSet rs,Statement stmt,Connection con){
        if (rs!= null) {
            try {
                rs.close();
            } catch (SQLException e) {e.printStackTrace();}
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {e.printStackTrace();}
            stmt = null;
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {e.printStackTrace();}
            con = null;
        }
    }
    public static  void release(ResultSet rs, PreparedStatement preparedStatement,Connection con){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {e.printStackTrace();}
            rs = null;
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {e.printStackTrace();}
            preparedStatement = null;
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {e.printStackTrace();}
            con = null;
        }
    }

}
