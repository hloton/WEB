package com.user.servlet;
import com.user.dao.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet("/FileUploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName=(String) request.getSession().getAttribute("userName");
        Part file=request.getPart("file");
        String fileName = generateUniqueFileName(file);

        String uploadPath=request.getServletContext().getRealPath("/pics");
        File uploadDir=new File(uploadPath);
        if(!uploadDir.exists())
            uploadDir.mkdir();
        String filePath = uploadPath + File.separator + fileName;
        saveFile(file,filePath);
        try {
            saveDB(userName,fileName);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        session.setAttribute("profileImage", fileName);
        //response.sendRedirect("user.jsp");
        String jsonResponse = "{\"redirectUrl\": \"user.jsp\"}";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
    private String generateUniqueFileName(Part part) {
        String submittedFileName = getFileName(part);
        if (submittedFileName != null && !submittedFileName.isEmpty()) {
            // 生成唯一的文件名，比如使用 UUID
            return UUID.randomUUID().toString() + "_" + submittedFileName;
        }
        return null;
    }private String getFileName(final Part part){
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    private void saveFile(Part part, String filePath) throws IOException {
        // 保存文件到服务器
        try (InputStream inputStream = part.getInputStream();
             OutputStream outputStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
    private void saveDB(String name,String path) throws SQLException, ClassNotFoundException {
        // 保存文件到数据库
        DBUtils dbUtils=new DBUtils();
        dbUtils.UpdatePic(name,path);
    }

}
