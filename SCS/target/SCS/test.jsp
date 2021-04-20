<%--
  Created by IntelliJ IDEA.
  User: 帅
  Date: 2021/4/18
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <script  src="static/js/jquery-1.7.2.min.js"></script>
        <script>
            $(function (){
                $("#submit").click(function (){
                    $.ajax({
                        url:"User/Login",
                        contentType:"application/json;charset=utf-8",
                        dataType:"json",
                        type:"post",
                        data:'',
                        success:function (data){
                            console.log(data.status);
                        },
                        error:function (data){
                            alert(data)
                            alert("未找到此人")
                        }
                    })
                })
            })
        </script>

    </head>
    <body>
        <form action="User/Login" method="post">
            <input type="text" name="username">
            <input type="text" name="password">
            <input type="submit" value="提交" id="submit">
        </form>
    </body>
</html>
