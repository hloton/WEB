
<%--
  Created by IntelliJ IDEA.
  User: hloton
  Date: 2023/11/15
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>管理员页</title>
    <style>
        a:link {color: blue}
        a:visited {color: purple}
        a:hover {color: red}
        .search{
            margin: 20px auto;
            text-align: center;
        }
        .search input{
            width: 200px;
            height: 30px;
        }
        .search button{
            height: 30px;
        }
        h2 {
            text-align: center;
        }
        table {
            border-collapse: collapse;
            width: 600px;
            margin: 0 auto;
        }
        .exit,.back{
            margin: auto;
            text-align: center;
        }
        table tr,
        th,
        td {
            border: 2px ridge white;
            border-collapse: collapse;
            text-align: center;
        }
        .popup,.popup1 {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.6);
            display: none;
        }
        .popup-content {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
        }
        form{
            text-align: center;
        }
        form div{
            margin: 20px;
        }
        form div span{
            margin-right: 20px;
        }
        .error,.error1{
            display: none;
            color: red;
        }
    </style>
</head>


<body>
<%--添加表单--%>
<div class="popup">
    <div class="popup-content">
        <form action="EditUserServlet" method="post" class="add_form">
            <input type="hidden" name="formType" value="add">
            <div><span>用户名</span><input type="text" name="username" autocomplete="off" class="add_username"><span class="error">该用户名已存在</span></div>
            <div><span>密码</span><input type="text" name="password" autocomplete="off"></div>
            <input type="submit" class="add_btn">
            <button type="button" class="popup-close">取消</button>
        </form>
    </div>
</div>
<%--修改表单--%>
<div class="popup1">
    <div class="popup-content">
        <form action="EditUserServlet" method="post" class="edit_form">
            <%--            隐藏字段，表示表单类型（修改/增加）--%>
            <input type="hidden" name="formType" value="edit">
            <%--    隐藏字段，表示被修改学生信息的id--%>
            <input type="hidden" name="id" value="" class="edit_id">
                <div><span>原用户名</span><input type="text" name="oldname" class="edit_oldname" autocomplete="off"></div>
            <div><span>新用户名</span><input type="text" name="username" class="edit_name" autocomplete="off"><span class="error1">该用户名已存在</span></div>
            <div><span>密码</span><input type="text" name="password" class="edit_password" autocomplete="off"></div>
            <input type="submit" value="确认修改" class="edit_btn">
            <button type="button" class="popup1-close">取消</button>
        </form>
    </div>
</div>
<div class="search"><input type="text" class="queryUser" placeholder="请输入您要查询的用户名">
    <button type="button">搜索</button>
</div>
<table>
    <tr>
        <th>用户名</th>
        <th>密码</th>
        <th>操作</th>
    </tr>
    <jsp:useBean id="dbunits" scope="page" class="com.user.dao.DBUtils"/>
<c:forEach var="user" items="${dbunits.users}">
    <tr>
        <td>${user.value.name}</td>
        <td>${user.value.password}</td>
        <td><a href="#" class="edit" data-name="${user.value.name}">修改</a>
            |<a href="#" class="remove" data-name="${user.value.name}">删除</a></td>
    </tr>
</c:forEach>
    <tr>
        <td colspan="3" ><button class="add">添加</button></td>
    </tr>
</table>
<div class="back" style="display: none"><a href="admin.jsp">返回首页</a></div>
<div class="exit"><a href="login.jsp?exit=111">退出登录</a></div>
<script>

    const popup = document.querySelector('.popup');
    const popup1 = document.querySelector('.popup1');
    const closeButton = document.querySelector('.popup-close');
    const closeButton1 = document.querySelector('.popup1-close');

    const table=document.querySelector("table")

const back=document.querySelector(".back")
    const add_username=document.querySelector(".add_username")
    const edit_username=document.querySelector(".edit_name")
    const add_btn=document.querySelector(".add_btn")
    const edit_btn=document.querySelector(".edit_btn")
    const error_span=document.querySelector(".error");
    const error_span1=document.querySelector(".error1");
    const search=document.querySelector(".search")
    closeButton.addEventListener('click', () => {//表单关闭按钮
        popup.style.display = 'none';
    });
    closeButton1.addEventListener('click', () => {
        popup1.style.display='none';
    });
    function containName(name,errorSpan,btn){
        const xhr=new XMLHttpRequest();
        xhr.onreadystatechange=function (){
            if(xhr.readyState===4&&xhr.status===200){
                if(xhr.responseText==="error"){
                    errorSpan.style.display="block";
                    btn.disabled=true;
                }

                else{
                    errorSpan.style.display="none";
                    btn.disabled=false;
                }
            }
        }
        xhr.open("GET","CheckUserNameServlet?username="+name,true)
        xhr.send();
    }
    add_username.addEventListener('blur',function (){
        containName(this.value, error_span,add_btn);
    })
edit_username.addEventListener('blur',function (){
    containName(this.value,error_span1,edit_btn);
})
    function defaultFetch(name){
        const oldname=document.querySelector('.edit_oldname')
        const password=document.querySelector('.edit_password')
        if(name){
            let xhr=new XMLHttpRequest();//创建了一个新的XMLHttpRequest对象，用于发送AJAX请求，与服务器进行异步通信
            xhr.onreadystatechange=function(){//回调函数，当请求的状态改变时函数会被调用
                if(xhr.readyState===4&&xhr.status===200){//请求已经完成且成功
                    let response=JSON.parse(xhr.responseText)//获取服务器返回的json格式的字符串，并将字符串转换为js对象
                    oldname.value=response.name.toString()
                    password.value=response.password.toString()
                    popup1.style.display='block';
                }
            }
            xhr.open("GET","DefaultValueServlet?oldname="+name,true)//配置AJAX请求，true表示异步请求
            xhr.send()//发送AJAX请求到服务器，触发和服务器的通信，发送后js就可以继续执行而不用等待服务器响应
        }

    }
    const queryUser=document.querySelector(".queryUser")

queryUser.addEventListener('input',function (e){

        // const queryUser=document.querySelector(".queryUser")
        let xhr=new XMLHttpRequest();


        xhr.onreadystatechange=function (){
            if(xhr.readyState===4&&xhr.status===200){
                if(xhr.responseText==="null"){
                    table.innerHTML=`
                        <tr>
                        <th>用户名</th>
                    <th>密码</th>
                    <th>操作</th>
                </tr>

                    <tr>
                        <td colspan="3" >未查询到任何用户</td>
                    </tr>
                        `
                    back.style.display="block";
                }
                let response=JSON.parse(xhr.responseText)
                let name=response.name.toString()
                let password=response.password.toString()
                console.log(name, password);
                table.innerHTML=`
                  <tr>
        <th>用户名</th>
        <th>密码</th>
        <th>操作</th>
    </tr>

    <tr>
        <td>\${name}</td>
        <td>\${password}</td>
        <td><a href="#" class="edit" data-name="\${name}">修改</a>
            |<a href="#" class="remove" data-name="\${name}">删除</a></td>
    </tr>

    <tr>
        <td colspan="3" ><button class="add">添加</button></td>
    </tr>`
                back.style.display="block";
            }
        }
        xhr.open("GET","EditUserServlet?username="+queryUser.value+"&type=query")
        xhr.send()



})
    table.addEventListener('click',function (e){//事件委托
        if(e.target.tagName==='A'){
            if(e.target.className==='remove'){
                console.log("删除的名字"+e.target.dataset.name);
                if(confirm("确定删除该条信息吗？")){
                    location.href="EditUserServlet?username="+e.target.dataset.name+"&type=delete"//location.href=url是GET请求
                }
            }
            else if(e.target.className==='edit'){
                defaultFetch(e.target.dataset.name)

            }

        }
        else if(e.target.tagName==='BUTTON'){
            if(e.target.className==='add')
                popup.style.display = 'block';

        }

    })
</script>
</body>
</html>
