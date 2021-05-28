<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>登陆</title>
    <link rel="stylesheet" href="/static/layui/css/layui.css" media="all" charset="utf-8">
    <link rel="stylesheet" href="static/css/login.css" media="all" charset="utf-8">
    <link rel="shortcut icon" href="static/images/favicon.ico" type="image/x-icon">
    <script src="/static/layui/layui.js" charset="UTF-8"></script>
    <script src="static/js/md5.js" charset="utf-8"></script>
    <script src="static/js/login.js" charset="UTF-8"></script>

    <style>
        .layui-elem-quote {
            position: relative;
            top: 0px;
            left: -10px;
            width: 99%;
            background-color: #ffffff!important;
            border-bottom: 1px solid rgba(0, 0, 0, 0.1);
        }

        .spanTitle {
            position: relative;
            top: 5px;
            font-size: 18px;
        }

        /* 覆盖原框架样式 */
        /*.layui-elem-quote {*/
        /*    background-color: inherit !important;*/
        /*}*/

        .layui-input,
        .layui-select,
        .layui-textarea {
            background-color: inherit;
            padding-left: 30px;
        }

        a:hover {
            color: #03a2d2;
            text-decoration:underline;
        }
    </style>
    <input type="hidden" id="_w_elixir">
</head>

<body style="zoom: 1;">
<!-- Head -->
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-sm12 layui-col-md12 zy_mar_01">
            <blockquote class="layui-elem-quote">
                <a href="">
                    <img src="./static/images/school.png" alt="LOGO">
                </a>
                <span class="spanTitle">欢迎登录智慧校园系统</span>
            </blockquote>
        </div>
    </div>
</div>
<!-- Head End -->

<!-- Carousel -->
<div class="layui-row">
    <div class="layui-col-sm12 layui-col-md12">
        <div class="layui-carousel zy_login_height carousel-background" id="login" lay-filter="login" lay-anim="fade">
            <div carousel-item="">
                <div class="">
                    <div class="zy_login_cont">
                        <canvas width="506" height="574"
                                style="display: block; background: rgba(0, 0, 0, 0);"></canvas>
                    </div>
                </div>
                <div class="layui-this">
                    <div class="background">
                        <span></span><span></span><span></span>
                        <span></span><span></span><span></span>
                        <span></span><span></span><span></span>
                        <span></span><span></span><span></span>
                    </div>
                </div>
            </div>
            <div class="layui-carousel-ind">
                <ul>
                    <li class=""></li>
                    <li class=""></li>
                    <li class="layui-this"></li>
                    <li class=""></li>
                </ul>
            </div>
            <button class="layui-icon layui-carousel-arrow" lay-type="sub"></button>
            <button
                    class="layui-icon layui-carousel-arrow" lay-type="add">
            </button>
        </div>
    </div>
</div>
<!-- Carousel End -->

<!-- Footer -->
<div class="layui-row">
    <div class="layui-col-sm12 layui-col-md12 zy_center zy_mar_01">
        ©2021-2022 工程实践Ⅱ-测试项目
    </div>
</div>
<!-- Footer End -->
<!-- LoginForm -->
<div class="zy_lofo_main" id="Login_main">
    <fieldset class="layui-elem-field layui-field-title zy_mar_02">
        <legend>
            <img src="static/images/plane.png" style="height: 50px;">
            <h3>欢迎登陆</h3>
        </legend>
    </fieldset>
    <div class="layui-row layui-col-space15">
        <form class="layui-form zy_pad_01" id="loginForm">
            <div class="layui-col-sm12 layui-col-md12">
                <div class="layui-form-item">
                    <input type="text" id="username" name="userName" lay-verify="required|userName" autocomplete="off"
                           placeholder="账号" lay-reqText="用户名不能为空" class="layui-input">
                    <i class="layui-icon layui-icon-username zy_lofo_icon"></i>
                </div>
            </div>
            <div class="layui-col-sm12 layui-col-md12">
                <div class="layui-form-item">
                    <input type="password" id="password" lay-verify="required|pass" autocomplete="off"
                           placeholder="密码" lay-reqText="密码不能为空" class="layui-input">
                    <input type="hidden" id="md5password" name="md5password">
                    <i class="layui-icon layui-icon-password zy_lofo_icon"></i>
                </div>
            </div>
            <div class="layui-col-sm12 layui-col-md12">
                <div class="layui-row">
                    <div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
                        <div class="layui-form-item">
                            <input type="text" id="vercode" lay-verify="required|vercodes"
                                   autocomplete="off" placeholder="验证码" lay-reqText="请填写验证码" class="layui-input"
                                   maxlength="4">
                            <i class="layui-icon layui-icon-vercode zy_lofo_icon"></i>
                        </div>
                    </div>
                    <div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
                        <div class="zy_lofo_vercode zyVerCode veriCode"></div>
                    </div>
                </div>
            </div>
            <div class="layui-col-sm12 layui-col-md12">
                <br>
                <a href="/static/html/findPwd.html"
                   style="color: #027b70;right: 2%;position: absolute;">
                    忘记密码</a>
                <br>
                <br>
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="formLogin"
                        id="LoginGo">立即登录
                </button>
            </div>
        </form>
    </div>
</div>
<!-- LoginForm End -->
<div class="layui-layer-move"></div>
</body>
</html>