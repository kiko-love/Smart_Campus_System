package com.scs.controller;


import com.alibaba.fastjson.JSONObject;
import com.scs.aip.face.FaceDetect;
import com.scs.pojo.User;
import com.scs.pojo.student;
import com.scs.service.UserService;
import com.scs.service.studentService;
import com.scs.utils.JedisUtil;
import com.scs.utils.RandomCodeUtils;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/findPassword")
public class findPwdController {

    @Autowired
    private UserService userService;
    @Autowired
    private studentService studentService;


    @ResponseBody
    @RequestMapping(value = "/getUserVer", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String getUserForEmail(HttpServletRequest request) {
        //获取参数
        String username = request.getParameter("username");
        JSONObject data = new JSONObject();
        List<student> studentList = studentService.getStudentById(username);
        if (studentList.size() == 0) {
            data.put("success", 0);
            data.put("userEmail", "");
            data.put("msg", "学号不存在，无法重置密码");
        } else {
            List<User> userList = userService.FindByName(studentList.get(0).getUserId());
            System.out.println("studentList:" + studentList);
            //获取用户邮箱
            if (userList.size() == 0) {
                data.put("success", 0);
                data.put("userEmail", "");
                data.put("msg", "该学号尚未注册平台账号，请联系管理员咨询");
            } else {
                if (userList.get(0).getStatus().equals("110")) {
                    data.put("success", 0);
                    data.put("userEmail", "");
                    data.put("msg", "该账号当前处于冻结状态，无法重置密码，请联系管理员解封");
                } else {
                    String userEmail = studentList.get(0).getEmail();
                    if (userEmail == null || userEmail.equals("")) {
                        //封装成json
                        data.put("success", 0);
                        data.put("userEmail", userEmail);
                        data.put("msg", "该账号未在平台绑定邮箱，无法重置密码");
                    } else {
                        //封装成json
                        data.put("success", 1);
                        data.put("userEmail", userEmail);
                        data.put("msg", "学号查询成功");
                    }
                }
            }
        }
        return data.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/EmailToChangePwd", produces = "application/json;charset=utf-8")
    public String EmailToChangePwd(HttpServletRequest req, HttpServletResponse resp) {
        String newPassword = req.getParameter("newPassword");
        String username = req.getParameter("username");
        String token_Email = req.getParameter("token_Email");
        String key = "EMAIL_CODE_VER_" + username;
        JSONObject res = new JSONObject();
        if (RandomCodeUtils.verEmailToken(key, token_Email)) {
            int i = userService.updatePassword(username, newPassword);
            if (i > 0) {
                res.put("success", 1);
                res.put("msg", "密码重置成功");
            } else {
                res.put("success", 0);
                res.put("msg", "密码重置失败，请稍后尝试");
            }
        } else {
            res.put("success", 0);
            res.put("msg", "非法密码重置操作，请验证操作的合法性");
        }
        return res.toJSONString();
    }


    @ResponseBody
    @RequestMapping(value = "/verEmailCode", produces = "application/json;charset=utf-8")
    public String verEmailCode(HttpServletRequest req, HttpServletResponse resp) {
        String emailCode = req.getParameter("emailCode");
        String username = req.getParameter("username");
        JSONObject res = new JSONObject();
        UUID uuid = UUID.randomUUID();
        // 得到对象产生的ID
        String token = uuid.toString();
        // 转换为大写
        token = token.toUpperCase();
        // 替换 “-”变成空格
        token = token.replaceAll("-", "");
        System.out.println("生成的token=" + token);

        if (RandomCodeUtils.verEmailCode(emailCode) == 100) {
            //邮箱验证成功，为该用户生成一个临时token用于后续密码修改操作的验证
            RandomCodeUtils.setEmailToken(username, token, 60000);
            res.put("success", 1);
            res.put("token_change", token);
            res.put("msg", "邮箱验证成功");
            return res.toJSONString();
        } else if (RandomCodeUtils.verEmailCode(emailCode) == 110) {
            res.put("success", 0);
            res.put("token_change", "");
            res.put("msg", "邮箱验证码错误");
            return res.toJSONString();
        } else {
            res.put("success", 0);
            res.put("token_change", "");
            res.put("msg", "邮箱验证码已失效，请重新获取");
            return res.toJSONString();
        }
    }


    @ResponseBody
    @RequestMapping(value = "/GetEmailCode", produces = "application/json;charset=utf-8")
    public String GetEmailCode(HttpServletRequest req, HttpServletResponse resp) throws EmailException {

        List<String> list = new ArrayList<>();
        String email = req.getParameter("email");
        System.out.println(email);
        list.add(email);
        //获取send后的信息
        String emailRes = RandomCodeUtils.sendEmail(list);
        JSONObject res = new JSONObject();

        if (emailRes != null) {
            res.put("success", 1);
            res.put("msg", "邮箱验证码发送成功");
        } else {
            res.put("success", 0);
            res.put("msg", "邮箱验证码发送失败");
        }
        return res.toJSONString();
    }
}
