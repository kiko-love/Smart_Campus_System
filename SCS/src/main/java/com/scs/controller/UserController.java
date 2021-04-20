package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.User;
import com.scs.service.UserService;
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

    @RequestMapping(value = "/Login",method = RequestMethod.POST)
    public @ResponseBody InformToFront Login(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = user.getUserName();
        String password = user.getMd5password();
        System.out.println(userName);
        List<User> users = userService.FindByName(userName);
        System.out.println(users);
        System.out.println(users.size());
        if (users.size()==0){
            InformToFront status1 =new InformToFront("用户名不存在", "-1", null);     //没有该用户
            return status1;
        }
        if(users.size()==1&&users.get(0).getUserName().equals(userName)){       //查到一条记录，并且用户名对应
            if(users.get(0).getMd5password().equals(password)){        //密码正确
                request.getSession().setAttribute("userInformation",userName);
                request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request,response);
            }
            else {
               InformToFront status2= new InformToFront("密码错误", "-2", null);     //密码不正确
                return status2;
            }
        }
        return null;
    }
}
