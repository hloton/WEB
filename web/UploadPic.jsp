<%--
  Created by IntelliJ IDEA.
  User: hloton
  Date: 2023/11/24
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.min.css" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@5.5.0/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
    <title>修改头像页</title>
</head>
<body>
    <div class="container" style="width: 500px;">
        <h2>上传头像</h2>
        <input id="file" name="file" type="file">
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@5.5.0/js/fileinput.min.js" type="application/javascript"></script>
    <script src="https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@5.5.0/js/locales/zh.js"></script>

    <script>
        $(document).ready(function() {
            $("#file").fileinput({
                language: "zh",  // 设置语言为中文
                // enctype: 'multipart/form-data',
                showUpload: true,  // 显示上传按钮
                showRemove: true,  // 移除按钮
                allowedFileExtensions: ["jpg", "png", "gif"],  // 允许的文件扩展名
                maxFileSize: 0,  // 最大文件大小，0即为无限制
                uploadUrl: "/ten/FileUploadServlet",  // 文件上传的URL
                msgZoomModalHeading:	"Detailed Preview",
                uploadAsync: false, // 同步上传

            }).on("filebatchuploadsuccess",function(event, data) {
                //回调函数，文件上传成功后出发（适用于同步上传）
                console.log(data);
                const response = data.response;
                if (response.redirectUrl) {
                    // 重定向到指定的 URL
                    //alert("成功！");
                    window.location.href = response.redirectUrl;}
                })

        });

    </script>
</body>
</html>
