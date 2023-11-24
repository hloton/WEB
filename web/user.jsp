<%--
  Created by IntelliJ IDEA.
  User: hloton
  Date: 2023/11/11
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>用户详情页</title>
<%--    <link rel="stylesheet" href="./bootstrap-3.4.1/css/bootstrap.css">--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">--%>
    <link href="https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@5.5.2/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />


    <style>
        .profile-image-container {
            position: relative;
            margin:10px auto 20px auto;
            width: 150px;
            height: 150px;
        }


        .profile-image-overlay::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            opacity: 0;
            transition: opacity 0.3s ease;
            cursor: pointer;
            border-radius: 50%;
            text-align: center;
            /*background-image: url("images/02.jpg");*/
        }
        .profile-image-text{
            position: absolute;
            left: 30%;
            top: 40%;
            color: white; /* 设置文字颜色 */
            font-size: 16px; /* 设置文字大小 */
            font-weight: bold; /* 设置文字加粗 */
            opacity: 0; /* 默认隐藏 */
            transition: opacity 0.3s ease;
            text-decoration: none;

        }
        .profile-image-overlay:hover::before {
            opacity: 1;
        }
        .profile-image-overlay:hover .profile-image-text {
            opacity: 1; /* 在遮罩出现时显示 */
        }
    </style>
</head>

<body>
<div class="container mt-5" style="max-width: 400px;">
    <div class="card">
        <div class="card-header">
            <h5 class="card-title">个人资料</h5>
        </div>
        <div class="card-body">
            <div class="profile-image-container">
                <div class="profile-image-overlay" data-bs-toggle="modal" data-bs-target="#updateImageModal">
                    <a class="profile-image-text" href="UploadPic.jsp?userName=${sessionScope.userName}">修改头像</a>
                    <input type="file" style="display: none">
                </div>
                <c:choose>
                    <c:when test="${not empty sessionScope.profileImage}">
                        <img src="pics/${sessionScope.profileImage}" alt="" class="rounded-circle" width="150" height="150">
                    </c:when>
                    <c:otherwise>
                        <%-- 如果 profileImage 为空，显示默认头像 --%>
                        <img src="images/00.jpg" alt="" class="rounded-circle" width="150" height="150">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="text-center">
            <div class="mb-3">
                <label class="fw-bold">用户名</label>
                <span class="form-control-plaintext" >${sessionScope.userName}</span>
            </div>
            <div class="mb-3">
                <label  class="fw-bold">密码</label>
                <span class="form-control-plaintext" >${sessionScope.passWord}</span>
            </div>
            <div class="mb-3">
                <label  class="fw-bold">性别</label>
                <span class="form-control-plaintext">${sessionScope.gender}</span>
            </div>
            <div class="mb-3">
                <label class="fw-bold">性别</label>
                <span class="form-control-plaintext">${sessionScope.email}</span>
            </div>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#updateModal">
                编辑资料
            </button>
                <a href="login.jsp?exit=111" class="btn btn-primary">退出登录</a>
        </div>
        </div>
    </div>
</div>



<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script >//
// 设置定时器，每隔一段时间获取当前会话状态
    let id=setInterval(function (){
        //console.log("111\n");
        $.get("CheckSessionServlet", function(response) {
            if (response === "invalid") {
                // 会话无效，删除定时器，弹出提示框
                clearInterval(id)
                alert("您的账号已在其他设备登录，请重新登录。");
                // 可以执行其他操作，例如重定向到登录页
                window.location.href = "login.jsp";
            }
        });
    }, 2000);
</script>
</body>
</html>
