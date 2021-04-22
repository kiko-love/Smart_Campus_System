window.onload=function () {
    let NowHour = GetNow();
    let msg ="";
    if (NowHour > 0 && NowHour <= 6) {
        msg = "凌晨好！";
    }else if (NowHour > 6 && NowHour <= 11) {
        msg = "早上好！";
    }else if (NowHour > 11 && NowHour <= 12) {
        msg = "中午好！";
    }else if (NowHour > 12 && NowHour <= 19) {
        msg = "下午好！";
    }else if (NowHour > 19 && NowHour <= 24) {
        msg = "晚上好！";
    }
    layer.alert('欢迎登录管理系统', {
        title:msg,
        skin: 'layui-layer-molv' //样式类名
        , closeBtn: 0
    });
}



function GetNow() {
    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth()+1;
    let week = date.getDate();
    let day = date.getDay();
    let hour = date.getHours();
    let minutes = date.getMinutes();
    let second = date.getSeconds();

    return hour;

}