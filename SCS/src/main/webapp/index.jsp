<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>登陆</title>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all" charset="utf-8">
    <link rel="stylesheet" href="static/css/login.css" media="all" charset="utf-8">
    <link rel="shortcut icon" href="static/images/favicon.ico" type="image/x-icon">
    <script src="static/layui/layui.js" charset="UTF-8"></script>
    <script src="static/js/md5.js" charset="utf-8"></script>
    <script src="static/js/jQuery.js" charset="UTF-8"></script>
    <script src="static/js/login.js" charset="UTF-8"></script>

    <style>
        /* 覆盖原框架样式 */
        .layui-elem-quote {
            background-color: inherit !important;
        }

        .layui-input,
        .layui-select,
        .layui-textarea {
            background-color: inherit;
            padding-left: 30px;
        }
    </style>
    <input type="hidden" id="_w_elixir">
</head>

<body style="zoom: 1;">
<!-- Head -->
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-sm12 layui-col-md12 zy_mar_01">
            <blockquote class="layui-elem-quote"><h1>欢迎登录你的管理系统</h1></blockquote>
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
                    <div class="zy_login_cont"><canvas width="506" height="574"
                                                        style="display: block; background: rgba(0, 0, 0, 0);"></canvas></div>
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
            </div><button class="layui-icon layui-carousel-arrow" lay-type="sub"></button><button
                class="layui-icon layui-carousel-arrow" lay-type="add"></button>
        </div>
    </div>
</div>
<!-- Carousel End -->

<!-- Footer -->
<div class="layui-row">
    <div class="layui-col-sm12 layui-col-md12 zy_center zy_mar_01">
        ©2021-2022  工程实践Ⅱ-测试项目
    </div>
</div>
<!-- Footer End -->
<!-- LoginForm -->
<div class="zy_lofo_main" id="Login_main">
    <fieldset class="layui-elem-field layui-field-title zy_mar_02">
        <legend>
            <img src="../../../../../JavaWebDemo/response/src/main/webapp/image/plane.png" style="height: 50px;">
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
                    <input type="hidden" id="md5-password" name="md5-password">
                    <i class="layui-icon layui-icon-password zy_lofo_icon"></i>
                </div>
            </div>
            <div class="layui-col-sm12 layui-col-md12">
                <div class="layui-row">
                    <div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
                        <div class="layui-form-item">
                            <input type="text" name="vercode" id="vercode" lay-verify="required|vercodes"
                                   autocomplete="off" placeholder="验证码" lay-reqText="请填写验证码" class="layui-input" maxlength="4">
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
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="formLogin"
                        id="LoginGo">立即登录</button>
                <br>
                <br>
                <span class="zy_span_bottom" >还没有账号？<strong id="logtoreg">注册</strong></span>
            </div>
        </form>
    </div>
</div>
<div class="zy_lofo_main" id="register_main">
    <fieldset class="layui-elem-field layui-field-title zy_mar_02">
        <legend><h3>欢迎注册</h3></legend>
    </fieldset>
    <div class="layui-row layui-col-space15">
        <form class="layui-form zy_pad_01" id="registerForm">
            <div class="layui-col-sm12 layui-col-md12">
                <div class="layui-form-item">
                    <input type="text" id="username-register" name="userName" lay-verify="required|userName" autocomplete="off"
                           placeholder="账号" lay-reqText="用户名不能为空" class="layui-input layui-form-danger">
                    <i class="layui-icon layui-icon-username zy_lofo_icon"></i>
                </div>
            </div>
            <div class="layui-col-sm12 layui-col-md12">
                <div class="layui-form-item">
                    <input type="password" id="password-register" lay-verify="required|pass" autocomplete="off"
                           placeholder="密码" lay-reqText="密码不能为空" class="layui-input">
                    <i class="layui-icon layui-icon-password zy_lofo_icon"></i>
                </div>
            </div>
            <div class="layui-col-sm12 layui-col-md12">
                <div class="layui-form-item">
                    <input type="password" id="password-register2" lay-verify="required|pass" autocomplete="off"
                           placeholder="确认密码" lay-reqText="请确认你的密码" class="layui-input">
                    <input type="hidden" id="md5-password-register" name="md5-password">
                    <i class="layui-icon layui-icon-password zy_lofo_icon"></i>
                </div>
            </div>
            <div class="layui-col-sm12 layui-col-md12">
                <div class="layui-row">
                    <div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
                        <div class="layui-form-item">
                            <input type="text" name="vercode" id="vercode-Register" lay-verify="required|vercodes"
                                   autocomplete="off" placeholder="验证码" lay-reqText="请填写验证码" class="layui-input" maxlength="4">
                            <i class="layui-icon layui-icon-vercode zy_lofo_icon"></i>
                        </div>
                    </div>
                    <div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
                        <div class="zy_lofo_vercode zyVerCode veriCode" ></div>
                    </div>
                </div>
            </div>
            <div class="layui-col-sm12 layui-col-md12">
                <br>
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="formLogin" id="RegisterGo">立即注册</button>
                <br>
                <br>
                <span class="zy_span_bottom" >已有账号？<strong id="regtolog">登录</strong></span>
            </div>
        </form>
    </div>
</div>
<!-- LoginForm End -->
<div class="layui-layer-move"></div>
</body>
</html>