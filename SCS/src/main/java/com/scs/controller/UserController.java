package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.User;
import com.scs.pojo.student;
import com.scs.service.UserService;
import com.scs.service.studentService;
import com.scs.utils.InformToFront;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private studentService studentService;

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public @ResponseBody
    InformToFront Login(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = user.getUserName();
        String password = user.getMd5password();
        String role = user.getRole();
        System.out.println(password);
        List<User> users = userService.FindByName(userName);
        System.out.println(users);
        System.out.println(users.size());
        if (users.size() == 0) {
            InformToFront status_err = new InformToFront("Username does not exist", "-1", null, null);     //没有该用户
            return status_err;
        }
        if (users.size() == 1 && users.get(0).getUserName().equals(userName)) {
            //查到一条记录，并且用户名对应
            String md5password = users.get(0).getMd5password();
            if (md5password != null) {
                if (md5password.equals(password)) {        //密码正确
                    InformToFront status_success = new InformToFront("Success", "0", role, null);
                    request.getSession().setAttribute("userInformation", userName);
                    // request.getRequestDispatcher("/admin.jsp").forward(request,response);
                    return status_success;
                } else {
                    InformToFront status_err = new InformToFront("Incorrect password", "-2", null, null);     //密码不正确
                    return status_err;
                }
            } else {
                InformToFront status_success = new InformToFront("Request error", "110", null, null);     //密码不正确
                return status_success;
            }
        }
        return null;
    }


    //注销用户session
    @ResponseBody
    @RequestMapping(value = "/SignOut", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String SignOut(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("userInformation", null);
        String information = (String) request.getSession().getAttribute("userInformation");
        JSONObject data =new JSONObject();
        if (information==null){
            data.put("success","1");
        }else if (!information.equals("")) {
            data.put("success","0");
        }else {
            data.put("success","1");
        }
        return data.toJSONString();
    }

    //获取该登录状态下的用户信息，头像处使用
    @ResponseBody
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String getUserInfo(HttpServletRequest request) {
        //获取session值
        String userId = (String) request.getSession().getAttribute("userInformation");
        List<student> student = studentService.getStudentById(userId);
        if (student.size() != 0) {
            //获取班级
            String classes = student.get(0).getClasses();
            //获取姓名
            String realName = student.get(0).getRealName();
            //封装成json
            JSONObject data = new JSONObject();
            data.put("classes", classes);
            data.put("realName", realName);
            //回传给前端
            return data.toJSONString();
        }
        return null;
    }
}
