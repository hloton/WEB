<%--
  Created by IntelliJ IDEA.
  User: Hloton
  Date: 2023/11/7
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%pageContext.setAttribute("APP_PATH",request.getContextPath());%>
    <title>登录页</title>
    <link rel="stylesheet" href="./bootstrap-3.4.1/css/bootstrap.css">
<%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">--%>
    <style>
        .box{
            width: 480px;
            padding: 0 20px;
            margin: 100px auto 0 auto;
            border: 1px solid #dddddd;
        }
        .card {
            margin: 100px auto 0 auto;
            width: 480px;
            border: 1px solid #dddddd;
        }

        .card-header {
            background-color: #f5f5f5;
            padding: 10px;
            text-align: center;
        }

        .card-header h2 {
            margin: 0;
        }

        .card-body {
            padding: 20px;
        }


        .error{
            color: red;
            text-align: center;
        }
        .test{
            margin: auto;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h2>用户登录</h2>
        </div>
   <div class="card-body">


<form action="LoginServlet" method="post" class="form-horizontal" role="form">
    <%
        HttpSession session1=request.getSession();
        session1.removeAttribute("userName");
        session1.removeAttribute("passWord");
        String errorMessage = (String) session1.getAttribute("errorMessage");
        String exit=request.getParameter("exit");
        if (errorMessage != null&&exit==null) { %>
    <div class="error"><%= errorMessage %></div>
    <% }%>
    <div class="form-group">
        <label class="control-label col-md-3">用户名</label>
        <div class="col-md-6">
            <input type="text" name="userName" class="form-control" placeholder="请输入用户名" autocomplete="off"/>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-3">密码</label>
        <div class="col-md-6">
            <input type="password" name="passWord"  class="form-control" placeholder="请输入密码" autocomplete="off"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">验证码</label>
        <div class="col-md-4">
            <input type="text" name="inputCode" class="form-control" placeholder="请输入验证码" autocomplete="off">
        </div>
        <div class="row"><img src="CaptchaServlet" class="auth " /></div>

    </div>
    <div class="form-group">
        <div class="col-md-12" style="text-align: center">
            <input type="submit" value="登录" class="btn btn-primary" style="margin-right:20px"/><input type="reset" value="重置" class="btn btn-primary"/>
        </div>
    </div>
</form>
    </div>
</div>
</div>
<div class="test">用户管理页：<a href="admin.jsp">admin</a></div>
<script>
    const autoCode=document.querySelector(".auth");
    autoCode.addEventListener("click",function (){
        autoCode.src= "CaptchaServlet?" + new Date().getTime(); // 添加时间戳以确保浏览器重新加载图片
    })
</script>
</body>
</html>
