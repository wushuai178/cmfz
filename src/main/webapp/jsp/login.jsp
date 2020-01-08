<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="../boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="../boot/js/jquery-3.2.1.min.js"></script>
    <script>
       $(function () {
           $("#login").click(function () {
               var username = $("#username").val();
               var password = $("#password").val();
               var code =$("#code").val();
               $.post({
                   url: "${pageContext.request.contextPath}/admin/login",
                   data: "username="+username+"&password="+password+"&code="+code,
                   success:function (data) {
                       if (data=="success"){
                           location.href = "${pageContext.request.contextPath}/jsp/main.jsp";
                       } else {
                           $("#msg").text(data);
                       }
                   },
                   dataType:"json"
               });
           });
       });
    </script>
</head>
<body style=" background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input id="username" type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username">
            </div>
            <div class="form-group">
                <input id="password" type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
            </div>
            <div class="form-group">
                <input id="code" type="text" class="form-control" placeholder="验证码" autocomplete="off" name="code">
                <img src="${pageContext.request.contextPath}/admin/getCode" />
            </div>
            <span id="msg" style="color: red"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="login" >登录</button>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default form-control">注册</button>
            </div>

        </div>
    </div>
</div>
</body>
</html>
