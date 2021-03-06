package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.*;
import com.scs.service.UserService;
import com.scs.service.portraitService;
import com.scs.service.studentService;
import com.scs.service.teacherService;
import com.scs.utils.IPUtils;
import com.scs.utils.InformToFront;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private portraitService portraitService;
    @Autowired
    private studentService studentService;
    @Autowired
    private teacherService teacherService;


    public static String GetBrowserName(HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent");
        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
        Browser browser = ua.getBrowser();
        return browser.getName() + ": " + browser.getVersion(userAgent);
    }

    public static String GetOsName(HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent");
        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
        OperatingSystem os = ua.getOperatingSystem();
        return os.getName();
    }



    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public @ResponseBody
    InformToFront Login(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = user.getUserName();
        String password = user.getMd5password();
        String user_exist = (String) request.getSession().getAttribute("userInformation");
        String browserName = GetBrowserName(request);
        String osName = GetOsName(request);
        String ipAddress = IPUtils.getRequestClientRealIP(request);
        String logStatus = "??????";
        Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String logTime = sim.format(date);

//        if (user_exist != null) {
//            InformToFront status_err =
//                    new InformToFront("Prohibit login to multiple accounts", "250", null, null, null);     //???????????????
//            return status_err;
//        }

        List<User> users = userService.FindByName(userName);
       if (users.size() == 0) {
            InformToFront status_err =
                    new InformToFront("Username does not exist", "-1", null, null, null);     //???????????????
            return status_err;
        }
        if (users.size() == 1 && users.get(0).getUserName().equals(userName)) {
            //??????????????????????????????????????????
            System.out.println(users);
            String role = users.get(0).getRole();
            userService.addLogRecord(new LogOB(null,browserName,osName
                    ,ipAddress,logTime,userName,role,logStatus));
            String md5password = users.get(0).getMd5password();
            String accountStatus = users.get(0).getStatus();
            if (md5password != null) {
                if (md5password.equals(password)) {        //????????????
                    InformToFront status_success =
                            new InformToFront("Success", "0", role, accountStatus, null);
                    if (accountStatus.equals("100")) {

                        if(role.equals("0")){
                            request.getSession().setAttribute("userData", "");
                        }
                        if(role.equals("2")){
                            List<student> student = studentService.getStudentById(userName);
                            request.getSession().setAttribute("userData", student);
                        }
                        if(role.equals("1")){
                            List<teacher> teacher = teacherService.getTeacherById(userName);
                            request.getSession().setAttribute("userData", teacher);
                        }
                        request.getSession().setAttribute("userInformation", userName);
                        request.getSession().setAttribute("role", role);
                    }
                    return status_success;
                } else {
                    InformToFront status_err =
                            new InformToFront("Incorrect password", "-2", accountStatus, null, null);     //???????????????
                    return status_err;
                }
            } else {
                InformToFront status_success =
                        new InformToFront("Request error", "110", null, null, null);     //???????????????
                return status_success;
            }
        }
        return null;
    }

    //????????????session
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


    //????????????session
    @ResponseBody
    @RequestMapping(value = "/getLogRecords", produces = "application/json;charset=utf-8")
    public String getLogRecords(HttpServletRequest request, HttpServletResponse response) {
        String role = (String) request.getSession().getAttribute("role");
        List<LogOB> logRecords = userService.getLogRecords();
        JSONObject data = new JSONObject();
        if (role.equals("0")){
            data.put("code", 0);
            data.put("msg", "????????????????????????");
            data.put("data", logRecords);
            data.put("count", logRecords.size());
        }else {
            data.put("code", 110);
            data.put("msg", "???????????????????????????");
            data.put("data", "");
            data.put("count", 0);
        }
        return data.toJSONString();
    }

    //????????????????????????
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
                data.put("msg", "????????????????????????");
                data.put("data", userList);
            } else {
                data.put("success", "1");
                data.put("msg", "????????????????????????");
                data.put("data", null);
            }
        } else {
            data.put("success", "1");
            data.put("data", null);
        }
        data.put("count", userList.size());
        return data.toJSONString();
    }

    //???????????????????????????
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
                data.put("msg", "????????????????????????????????????");
                data.put("data", userList);
            } else {
                data.put("success", "1");
                data.put("msg", "????????????????????????????????????");
                data.put("data", null);
            }
        } else {
            data.put("success", "1");
            data.put("data", null);
        }
        data.put("count", userList.size());
        return data.toJSONString();
    }


    //?????????????????????????????????????????????????????????
    @ResponseBody
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String getUserInfo(HttpServletRequest request) {
        //??????session???
        String username = (String) request.getSession().getAttribute("userInformation");
        List<User> User = userService.FindByName(username);
        JSONObject data = new JSONObject();
        String p_path = null;
        if (User.size() != 0) {
            //????????????id
            String realName = User.get(0).getUserName();
            portrait portrait = portraitService.getPortraitById(username);
            if (portrait != null) {
                p_path = portrait.getP_path();
            } else {
                p_path = "";
            }

            //?????????json
            data.put("userName", realName);
            data.put("p_path", p_path);
            data.put("success", 1);
            data.put("msg", "??????????????????");
            //???????????????
            return data.toJSONString();
        }
        data.put("userName", "");
        data.put("p_path", "");
        data.put("success", 0);
        data.put("msg", "??????????????????");
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
            jsonObject.put("msg", "????????????????????????????????????");
        } else {
            int i = userService.updatePassword(username, rePassword);
            if (i > 0) {
                jsonObject.put("success", 1);
                jsonObject.put("msg", "??????????????????");
            } else {
                jsonObject.put("success", 0);
                jsonObject.put("msg", "??????????????????");
            }
        }

        return jsonObject.toJSONString();
    }


    //??????????????????????????????
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
                data.put("msg", "????????????????????????");
                data.put("data", null);
            } else {
                int removeNumber = userService.removeAccount(userName);
                if (removeNumber > 0) {
                    data.put("success", "1");
                    data.put("msg", "??????????????????");
                    data.put("data", removeNumber);
                } else {
                    data.put("success", "0");
                    data.put("msg", "??????????????????");
                    data.put("data", removeNumber);
                }
            }
        } else {
            data.put("success", "0");
            data.put("msg", "????????????????????????????????????");
            data.put("data", null);
        }

        return data.toJSONString();
    }

    //????????????????????????????????????
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
            if (userList.get(0).getRole().equals("0") && !userList.get(0).getRole().equals(role)) {
                data.put("success", "0");
                data.put("msg", "??????????????????????????????");
                data.put("data", "");
                return data.toJSONString();
            }
            if (userList.get(0).getMd5password().equals(adminPwd)) {
                User user = new User(userName, password, role, null);
                int updateNumber = userService.updateAccount(user);
                if (updateNumber > 0) {
                    data.put("success", "1");
                    data.put("msg", "?????????????????????????????????????????????");
                    data.put("data", updateNumber);
                } else {
                    data.put("success", "0");
                    data.put("msg", "?????????????????????????????????????????????");
                    data.put("data", updateNumber);
                }

            } else {
                data.put("success", "0");
                data.put("msg", "?????????????????????");
                data.put("data", null);
            }
        } else {
            data.put("success", "0");
            data.put("msg", "???????????????????????????");
            data.put("data", null);
        }

        return data.toJSONString();
    }


    //??????????????????
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
            data.put("msg", "??????????????????????????????");
            data.put("data", null);
        } else {
            int i = userService.updateStatus(username, status);
            data.put("code", 0);
            data.put("msg", "updateStatus");
            if (i > 0) {
                data.put("success", "1");
                data.put("msg", "????????????????????????");
                data.put("data", i);
            } else {
                data.put("success", "0");
                data.put("msg", "????????????????????????");
                data.put("data", null);
            }
        }
        return data.toJSONString();
    }

    //??????????????????
    @ResponseBody
    @RequestMapping(value = "/addAccount", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String addAccount(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String md5password = request.getParameter("md5password");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String isDefault = request.getParameter("isDefault");
        User user = null;
        if (isDefault == null || !isDefault.equals("")) {
            user = new User(username, md5password, role, status);

        } else {
            user = new User(username, "e10adc3949ba59abbe56e057f20f883e", role, status);
        }
        JSONObject data = new JSONObject();
        int addNumber = userService.addAccount(user);
        if (addNumber > 0) {
            data.put("success", 1);
            data.put("msg", "??????????????????");
            data.put("data", null);
        } else {
            data.put("success", 0);
            data.put("msg", "??????????????????");
            data.put("data", null);
        }
        return data.toJSONString();
    }


    //????????????????????????
    @ResponseBody
    @RequestMapping(value = "/batchRemoveAccount", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String batchRemoveAccount(HttpServletRequest request, HttpServletResponse response) {
        List<String> List = JSONObject.parseArray(request.getParameter("userNames"), String.class);
        JSONObject data = new JSONObject();
        if (List.size() == 0) {
            data.put("success", 0);
            data.put("msg", "??????????????????");
            data.put("data", null);
        } else {
            int addNumber = userService.batchRemoveAccount(List);
            if (addNumber > 0) {
                data.put("success", 1);
                data.put("msg", "??????????????????????????????");
                data.put("data", null);
            } else {
                data.put("success", 0);
                data.put("msg", "??????????????????????????????");
                data.put("data", null);
            }
        }
        return data.toJSONString();
    }


    //??????????????????
    @ResponseBody
    @RequestMapping(value = "/searchAccount", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String searchAccount(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "searchStudent");
        List<User> list = userService.searchAccount(username);
        System.out.println(username);
        System.out.println(list);
        data.put("count", list.size());
        data.put("data", list);
        return data.toJSONString();
    }


}
