layui.use(['carousel', 'form'], function () {
    var carousel = layui.carousel
        , form = layui.form;
    //监听提交
    $("#LoginGo").click(function () {
        $("#md5-password").val(md5($("#password").val()));
        $("#loginForm").attr({"action":"/response_war_exploded/newLogin","method":"post"});
    })
    //自定义验证规则
    form.verify({
        userName: function (value,username) {
            if (value.length < 3) {
                return '用户名必须大于3个字符';
            }
        }
        ,password:function (value) {
            if (value.length == 0) {
                return '密码不能为空';
            }
        }
        , pass: [/^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格']
        , vercodes: function (value) {
            //获取验证码
            var zylVerCode = $(".zylVerCode").html();
            if (value.toLowerCase() != zylVerCode.toLowerCase()) {
                return '验证码错误';
            }
        }
    });


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

    //监听轮播--案例暂未使用
    carousel.on('change(zylogin)', function (obj) {
        var loginCarousel = obj.index;
    });
});

$(document).ready(function(){
    $(".veriCode").click(function (){
        zyVerCode();
    });
    $("#logtoreg").click(function () {
        let reg = $("#register_main");
        let log = $("#Login_main");

        log.animate({
            width:'toggle',
            opacity:'toggle'
        },'1500');
        reg.animate({
            width:'toggle',
            opacity:'toggle'
        },'1500')

    });
    $("#regtolog").click(function () {
        let reg = $("#register_main");
        let log = $("#Login_main");
        reg.animate({
            width:'toggle',
            opacity:'toggle'
        },'1500');
        log.animate({
            width:'toggle',
            opacity:'toggle'
        },'1500')
    });
});



$(function(){
    zyVerCode();//初始化生成随机数
});

//生成随机数
function zyVerCode(len){
    len = len || 4;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';//默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
    var maxPos = $chars.length;
    var zyCode = '';
    for (let i = 0; i < len; i++) {
        zyCode += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    $(".zyVerCode").html(zyCode);
}

