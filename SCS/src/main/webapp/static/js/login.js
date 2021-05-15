layui.use(['carousel', 'form', 'jquery'], function () {
    var carousel = layui.carousel
        , form = layui.form
        , $ = layui.jquery;
    //Login轮播主体
    carousel.render({
        elem: '#login'//指向容器选择器
        , indicator: 'none' //指示器位置||外部：outside||内部：inside||不显示：none
        , width: '100%' //设置容器宽度
        , height: 'zy_car_height'
        , arrow: 'none' //不显示箭头
        , anim: 'fade' //切换动画方式
        , autoplay: false //是否自动切换false true
        , interval: '5000' //自动切换时间:单位：ms（毫秒）
    });
    //监听提交
    form.on('submit(formLogin)', function (data) {
        //console.log(data.field); // 得到checkbox原始DOM对象
        var index = layer.load(2, {
            shade: [0.2, '#323232'] //0.1透明度的白色背景
        });
        $.ajax({
            type: "post",
            url: "/User/Login",
            data: data.field,
            dataType: 'json',
            success: function (result) {
                console.log(result)
                layer.close(index);
                if (result.code == '-1') {
                    layer.open({
                        title: '提示'
                        , icon: 2
                        , content: '用户名不存在'
                    });
                    // $('#username').val('');
                    // $('#password').val('');
                    // $('#vercode').val('');
                    zyVerCode();
                } else if (result.code == '-2') {
                    layer.open({
                        title: '提示'
                        , icon: 2
                        , content: '密码错误'
                    });
                    // $('#username').val('');
                    // $('#password').val('');
                    // $('#vercode').val('');
                    zyVerCode();
                } else if (result.code == '110') {
                    layer.open({
                        title: '提示'
                        , icon: 2
                        , content: '请求数据异常，请重新尝试'
                    });
                    // $('#username').val('');
                    // $('#password').val('');
                    // $('#vercode').val('');
                    zyVerCode();
                } else if (result.code == '0') {
                   //console.log(result.status);
                    if (result.status == '110') {
                        layer.open({
                            title: '帐号状态异常'
                            , icon: 5
                            , content: '该账号处于停用状态，请联系管理员进行解封'
                        });
                        return false;
                    }
                    sessionStorage.setItem("username", data.field.userName);
                    //获取完整路径
                    let curWwwPath = window.document.location.href;
                    //获取主机地址之后的目录，如： index.jsp
                    let pathName = window.document.location.pathname;
                    let pos = curWwwPath.indexOf(pathName);
                    //获取主机地址，如： http://localhost:8080
                    let localhostPaht = curWwwPath.substring(0, pos);
                    console.log(result.role);
                    if (result.role == '0') {
                        window.location.replace(localhostPaht + "/user/admin.jsp");
                    } else if (result.role == '1') {
                        window.location.replace(localhostPaht + "/user/teacher.jsp");
                    } else {
                        window.location.replace(localhostPaht + "/user/student.jsp");
                    }
                }
            },
            error: function () {
                layer.close(index);
                layer.open({
                    title: '提示'
                    , icon: 2
                    , content: '请求服务器出错，请稍后再试'
                });
            }
        });
        //这个return非常重要，防止ajax跳转，去掉的话就不经过ajax了
        return false;
    });

    //自定义验证规则
    form.verify({
        userName: function (value, username) {
            if (value.length < 3) {
                return '用户名必须大于3个字符';
            }
        }
        , password: function (value) {
            if (value.length == 0) {
                return '密码不能为空';
            }
        }
        , pass: [/^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格']
        , vercodes: function (value) {
            //获取验证码
            var zyVerCode = $(".zyVerCode").html();
            console.log(zyVerCode);
            if (value.toLowerCase() != zyVerCode.toLowerCase()) {
                return '验证码错误';
            }
        }
    });


    //监听轮播--案例暂未使用
    carousel.on('change(zylogin)', function (obj) {
        var loginCarousel = obj.index;
    });
    //初始化验证码
    $(".veriCode").click(function () {
        zyVerCode();
    });

    //生成随机数
    function zyVerCode(len) {
        len = len || 4;
        var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';//默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
        var maxPos = $chars.length;
        var zyCode = '';
        for (let i = 0; i < len; i++) {
            zyCode += $chars.charAt(Math.floor(Math.random() * maxPos));
        }
        $(".zyVerCode").html(zyCode);
    }

    zyVerCode();//初始化生成随机数
    $("#LoginGo").click(function () {
        $("#md5password").val(md5($("#password").val()));
        //$("#loginForm").attr({"action":"User/Login","method":"post"});
    });
    // $("#logtoreg").click(function () {
    //     let reg = $("#register_main");
    //     let log = $("#Login_main");
    //     log.animate({
    //         width: 'toggle',
    //         opacity: 'toggle'
    //     }, '1500');
    //     reg.animate({
    //         width: 'toggle',
    //         opacity: 'toggle'
    //     }, '1500')
    // });
    // $("#regtolog").click(function () {
    //     let reg = $("#register_main");
    //     let log = $("#Login_main");
    //     reg.animate({
    //         width: 'toggle',
    //         opacity: 'toggle'
    //     }, '1500');
    //     log.animate({
    //         width: 'toggle',
    //         opacity: 'toggle'
    //     }, '1500')
    // });
});





