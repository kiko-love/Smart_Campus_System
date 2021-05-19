package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.User;
import com.scs.pojo.portrait;
import com.scs.pojo.student;
import com.scs.service.UserService;
import com.scs.service.portraitService;
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
    private portraitService portraitService;


    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public @ResponseBody
    InformToFront Login(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = user.getUserName();
        String password = user.getMd5password();

        String user_exist = (String) request.getSession().getAttribute("userInformation");
//        if (user_exist != null) {
//            InformToFront status_err =
//                    new InformToFront("Prohibit login to multiple accounts", "250", null, null, null);     //没有该用户
//            return status_err;
//        }

        List<User> users = userService.FindByName(userName);

        if (users.size() == 0) {
            InformToFront status_err =
                    new InformToFront("Username does not exist", "-1", null, null, null);     //没有该用户
            return status_err;
        }
        if (users.size() == 1 && users.get(0).getUserName().equals(userName)) {
            //查到一条记录，并且用户名对应
            System.out.println(users);
            String role = users.get(0).getRole();
            String md5password = users.get(0).getMd5password();
            String accountStatus = users.get(0).getStatus();
            if (md5password != null) {
                if (md5password.equals(password)) {        //密码正确
                    InformToFront status_success =
                            new InformToFront("Success", "0", role, accountStatus, null);
                    if (accountStatus.equals("100")) {
                        request.getSession().setAttribute("userInformation", userName);
                        request.getSession().setAttribute("role", role);
                    }
                    return status_success;
                } else {
                    InformToFront status_err =
                            new InformToFront("Incorrect password", "-2", accountStatus, null, null);     //密码不正确
                    return status_err;
                }
            } else {
                InformToFront status_success =
                        new InformToFront("Request error", "110", null, null, null);     //密码不正确
                return status_success;
            }
        }
        return null;
    }

    //注销用户session
    @ResponseBody
    @RequestMapping(value = "/SignOut", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String SignOut(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("userInformation", null);
        String information = (String) request.getSession().getAttribute("userInformation");
        JSONObject data = new JSONObject();
        if (information == null) {
            data.put("success", "1");
        } else if (!information.equals("")) {
            data.put("success", "0");
        } else {
            data.put("success", "1");
        }
        return data.toJSONString();
    }

    //获取全部账号信息
    @ResponseBody
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String getAllUsers(HttpServletRequest request, HttpServletResponse response) {
        List<User> userList = userService.getAllUsers();
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "getAllUsers");
        if (userList != null) {
            if (userList.size() != 0) {
                data.put("success", "1");
                data.put("msg", "获取用户列表成功");
                data.put("data", userList);
            } else {
                data.put("success", "1");
                data.put("msg", "获取用户列表失败");
                data.put("data", null);
            }
        } else {
            data.put("success", "1");
            data.put("data", null);
        }
        data.put("count", userList.size());
        return data.toJSONString();
    }

    //获取不同的账号信息
    @ResponseBody
    @RequestMapping(value = "/getRoleAccounts", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String getRoleAccounts(HttpServletRequest request, HttpServletResponse response) {

        String role = request.getParameter("role");
        List<User> userList = userService.getRoleAccounts(role);
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "getRoleAccounts");
        if (userList != null) {
            if (userList.size() != 0) {
                data.put("success", "1");
                data.put("msg", "获取对应角色用户列表成功");
                data.put("data", userList);
            } else {
                data.put("success", "1");
                data.put("msg", "获取对应角色用户列表失败");
                data.put("data", null);
            }
        } else {
            data.put("success", "1");
            data.put("data", null);
        }
        data.put("count", userList.size());
        return data.toJSONString();
    }


    //获取该登录状态下的用户信息，头像处使用
    @ResponseBody
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String getUserInfo(HttpServletRequest request) {
        //获取session值
        String username = (String) request.getSession().getAttribute("userInformation");
        List<User> User = userService.FindByName(username);
        JSONObject data = new JSONObject();
        String p_path = null;
        if (User.size() != 0) {
            //获取用户id
            String realName = User.get(0).getUserName();
            portrait portrait = portraitService.getPortraitById(username);
            if (portrait != null) {
                p_path = portrait.getP_path();
            } else {
                p_path = "";
            }

            //封装成json
            data.put("userName", realName);
            data.put("p_path", p_path);
            data.put("success", 1);
            data.put("msg", "获取信息成功");
            //回传给前端
            return data.toJSONString();
        }
        data.put("userName", "");
        data.put("p_path", "");
        data.put("success", 0);
        data.put("msg", "获取信息失败");
        return data.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String updatePassword(HttpServletRequest request) {

        String oldPassword = request.getParameter("oldpassword");
        String rePassword = request.getParameter("repassword");
        String username = request.getParameter("userName");
        System.out.println(username);

        JSONObject jsonObject = new JSONObject();
        String password = userService.findPassword(username);
        if (oldPassword == null) {
            oldPassword = "";
        }

        if (!oldPassword.equals(password)) {
            jsonObject.put("success", 0);
            jsonObject.put("msg", "原始密码错误，请重新输入");
        } else {
            int i = userService.updatePassword(username, rePassword);
            if (i > 0) {
                jsonObject.put("success", 1);
                jsonObject.put("msg", "密码修改成功");
            } else {
                jsonObject.put("success", 0);
                jsonObject.put("msg", "修改密码失败");
            }
        }

        return jsonObject.toJSONString();
    }


    //管理员端删除账号接口
    @ResponseBody
    @RequestMapping(value = "/removeAccount", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String removeAccount(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("username");
        List<User> list = userService.FindByName(userName);
        JSONObject data = new JSONObject();
        if (list.size() != 0) {
            String role = list.get(0).getRole();
            if (role.equals("0")) {
                data.put("success", "0");
                data.put("msg", "您无权删除该账号");
                data.put("data", null);
            } else {
                int removeNumber = userService.removeAccount(userName);
                if (removeNumber > 0) {
                    data.put("success", "1");
                    data.put("msg", "删除账号成功");
                    data.put("data", removeNumber);
                } else {
                    data.put("success", "0");
                    data.put("msg", "删除账号失败");
                    data.put("data", removeNumber);
                }
            }
        } else {
            data.put("success", "0");
            data.put("msg", "账号信息不存在，删除失败");
            data.put("data", null);
        }

        return data.toJSONString();
    }

    //管理员端更改账号信息接口
    @ResponseBody
    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String updateAccount(HttpServletRequest request, HttpServletResponse response) {
        String adminPwd = request.getParameter("adminPwd");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        JSONObject data = new JSONObject();
        List<User> userList = userService.FindByName(userName);
        if (userList.size() != 0) {
            if (userList.get(0).getMd5password().equals(adminPwd)) {
                User user = new User(userName, password, role, null);
                int updateNumber = userService.updateAccount(user);
                if (updateNumber > 0) {
                    data.put("success", "1");
                    data.put("msg", "账号信息修改成功（管理员操作）");
                    data.put("data", updateNumber);
                } else {
                    data.put("success", "0");
                    data.put("msg", "账号信息修改失败（管理员操作）");
                    data.put("data", updateNumber);
                }

            } else {
                data.put("success", "0");
                data.put("msg", "管理员密码错误");
                data.put("data", null);
            }
        } else {
            data.put("success", "0");
            data.put("msg", "管理员身份识别错误");
            data.put("data", null);
        }

        return data.toJSONString();
    }


    //更改账号状态
    @ResponseBody
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String updateStatus(HttpServletRequest request, HttpServletResponse response) {

        String status = request.getParameter("status");
        String username = request.getParameter("username");
        String role = request.getParameter("role");
        JSONObject data = new JSONObject();
        List<User> userList = userService.FindByName(username);
        if (userList.get(0).getRole().equals("0") || role.equals("0")) {
            data.put("success", "0");
            data.put("msg", "您无权修改该账号状态");
            data.put("data", null);
        } else {
            int i = userService.updateStatus(username, status);
            data.put("code", 0);
            data.put("msg", "updateStatus");
            if (i > 0) {
                data.put("success", "1");
                data.put("msg", "账号权限修改成功");
                data.put("data", i);
            } else {
                data.put("success", "0");
                data.put("msg", "账号权限修改失败");
                data.put("data", null);
            }
        }
        return data.toJSONString();
    }

    //添加账号信息
    @ResponseBody
    @RequestMapping(value = "/addAccount", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String addAccount(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String md5password = request.getParameter("md5password");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        User user = new User(username, md5password, role, status);
        JSONObject data = new JSONObject();
        int addNumber = userService.addAccount(user);
        if (addNumber > 0) {
            data.put("success", 1);
            data.put("msg", "账号添加成功");
            data.put("data", null);
        }else {
            data.put("success", 0);
            data.put("msg", "账号添加失败");
            data.put("data", null);
        }
        return  data.toJSONString();
    }


    //批量删除账号信息
    @ResponseBody
    @RequestMapping(value = "/batchRemoveAccount", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String batchRemoveAccount(HttpServletRequest request, HttpServletResponse response) {
        List<String> List = JSONObject.parseArray(request.getParameter("userNames"),String.class);
        JSONObject data = new JSONObject();
        if (List.size()==0){
            data.put("success", 0);
            data.put("msg", "传递参数错误");
            data.put("data", null);
        }else {
            int addNumber = userService.batchRemoveAccount(List);
            if (addNumber > 0) {
                data.put("success", 1);
                data.put("msg", "批量删除账号添加成功");
                data.put("data", null);
            }else {
                data.put("success", 0);
                data.put("msg", "批量删除账号添加失败");
                data.put("data", null);
            }
        }
        return  data.toJSONString();
    }


    //查询账号信息
    @ResponseBody
    @RequestMapping(value = "/searchAccount", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String searchAccount(HttpServletRequest request, HttpServletResponse response) {
        String username= request.getParameter("username");
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "searchStudent");
        List<User> list = userService.searchAccount(username);
        System.out.println(username);
        System.out.println(list);
        data.put("count", list.size());
        data.put("data", list);
        return  data.toJSONString();
    }



}
