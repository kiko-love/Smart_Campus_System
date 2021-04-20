// 模块和回调函数
// 一般直接写在一个js文件中
// layui.use(['layer', 'form'], 
// function(){
//     var layer = layui.layer
//     ,form = layui.form;

//     //layer.msg('Hello World');
//     layer.msg("动态弹出式消息框");
//   });
//
layui.use('form', function () {
  let form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
  form.render();
});



window.onload = function () {
  let register = document.getElementById("register");
  let Reback = document.getElementById("Reback");
  let regobj = document.getElementById("username");
  let psw1 = document.getElementById("password01");
  let psw2 = document.getElementById("password02");
  let pwstrobj1 = document.getElementById("pwstr1");
  let pwstrobj2 = document.getElementById("pwstr2");
  let selobj = document.getElementById("studyoptions");
  let checkNameobj = document.getElementById("checkName");
  let patt = /^\w{6,12}$/;
  let codeImgObj = document.getElementById("codeImg");
  codeImgObj.title = "看不清，换一张";
  codeImgObj.style.cursor = "pointer";
  codeImgObj.onclick = function () {
    codeImgObj.src = codeImgObj.src + "?" + Math.random();
  }
  //返回按钮事件
  Reback.onclick = function () {
    layer.alert('你点击了返回', {
      icon: 1,
      title: "提示信息",
      anim: 0
    });
  }
  //焦点获取事件集合
  regobj.onfocus = function () {
    pwstrobj1.style.display = 'none';
    pwstrobj2.style.display = 'none';
    if (!patt.test(regobj.value) && regobj.value != "") {
      checkNameobj.style.display = '';
    }
  }
  psw1.onfocus = function () {
    let tmpstr = psw1.value;
    pwstrobj2.style.display = 'none';
    checkNameobj.style.display = 'none';
    if (tmpstr != "" && tmpstr.length < 6) {
      pwstrobj1.style.display = '';
    }

  }
  psw2.onfocus = function () {
    let tmpstr = psw2.value;
    pwstrobj1.style.display = 'none';
    checkNameobj.style.display = 'none';
    if (tmpstr != "" && (tmpstr.length < 6 || psw1.value != psw2.value)) {
      pwstrobj2.style.display = '';
    }
  }
  //实时改变事件集合
  regobj.oninput = function () {
    if (regobj.value =='') {
      checkNameobj.style.display = 'none';
      checkNameobj.innerHTML = ""
      return;
    }
    if (!patt.test(regobj.value)) {
      checkNameobj.style.display = '';
      checkNameobj.innerHTML = "用户名由6-12位数字、字母和下划线组成"
    } else {
      checkNameobj.style.display = 'none';
      checkNameobj.innerHTML = ""
    }
  }
  psw1.oninput = function () {
    let tmpstr = psw1.value;
    if (tmpstr =='') {
      pwstrobj1.style.display = 'none';
      pwstrobj1.innerHTML = ""
      return;
    }
    if (tmpstr.length < 6) {
      pwstrobj1.style.display = '';
      pwstrobj1.innerHTML = "密码长度不能小于6位";
    }
    else if (tmpstr.length >= 6) {
      pwstrobj1.style.display = 'none';
    }
  }
  psw2.oninput = function () {
    let tmpstr = psw2.value;
    if (tmpstr =='') {
      pwstrobj2.style.display = 'none';
      pwstrobj2.innerHTML = ""
      return;
    }
    if (psw1.value != psw2.value) {
      pwstrobj2.style.display = '';
      pwstrobj2.innerHTML = "两次输入的密码不一致，请检查";
    }
    // else if(tmpstr.length<6) {
    //   pwstrobj2.style.display = '';
    //   pwstrobj2.innerHTML = "密码长度不能小于6位";
    // }
    else {
      pwstrobj2.style.display = 'none';
    }
  }

  //注册点击事件
  register.onclick = function () {
    let dataFormobj = document.getElementById("dataForm");
    let md5pswobj = document.getElementById("md5psw");
    let pstr1 = psw1.value;
    let pstr2 = psw2.value;
    let vericodeObj = document.getElementById("vericode");
    md5pswobj.value = md5(psw2.value);

    dataFormobj.method = "post";
    dataFormobj.action = "/response_war_exploded/new"
    if (regobj.value == "") {
      layer.msg('请输入用户名', {
        icon: 5
      });
      regobj.focus();
      return false;
    }
    else if (!patt.test(regobj.value)) {
      layer.msg('用户名不合法', {
        icon: 5
      });
      regobj.focus();
      return false;
    }
    if (psw1.value == "") {
      layer.msg('请输入密码', {
        icon: 5
      });
      psw1.focus();
      return false;
    } else if (pstr1.length < 6) {
      layer.msg('密码长度不能小于6位', {
        icon: 0
      });
      psw1.focus();
      return false;
    }
    if (psw2.value == "") {
      layer.msg('请再次输入以确认', {
        icon: 0
      });
      psw2.focus();
      return false;
    }
    if (psw1.value != psw2.value) {
      layer.msg('两次输入密码不一致，请重新输入', {
        icon: 2
      });
      return false;
    }

    if (selobj.selectedIndex == 0) {
      layer.msg('请选择你的学习方向', {
        icon: 0,
      });
      selobj.focus();
      return false;
    }
    if (vericodeObj.value == "") {
      layer.msg('请输入验证码', {
        icon: 5,
      });
      vericodeObj.focus();
      return false;
    }



    layer.msg('模拟注册过程完成', {
      icon: 6,
    });
  }
}

