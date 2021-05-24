package com.scs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;

import com.scs.pojo.student;
import com.scs.service.UserService;
import com.scs.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/stu")
public class StudentController {
    @Autowired
    private studentService studentService;
    //批量删除学生信息
    @ResponseBody
    @RequestMapping(value = "/batchRemove", produces = "application/json;charset=utf-8")
    public String batchRemoveStudent(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        List<String> List = JSONObject.parseArray(request.getParameter("userIds"),String.class);
        System.out.println(List);
;       data.put("code", 0);
        data.put("msg", "batchRemoveStudent");
        int removeNumber = studentService.batchRemove(List);
        if (removeNumber > 0) {
            data.put("success", "1");
            data.put("msg", "删除选中用户成功");
        } else {
            data.put("success", "0");
            data.put("msg", "删除选中用户失败");
        }
        data.put("count", removeNumber);
        data.put("data", null);
        return data.toJSONString();
    }

    //获取全部学生信息
    @ResponseBody
    @RequestMapping(value = "/getStudents", produces = "application/json;charset=utf-8")
    public String getStudent(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "getData");
        List<student> list = studentService.getStudents();
        data.put("count", list.size());
        data.put("data", list);
        return data.toJSONString();
    }
    //查询学生信息
    @ResponseBody
    @RequestMapping(value = "/search", produces = "application/json;charset=utf-8")
    public String searchStudent(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "searchStudent");
        String userId = request.getParameter("userId");
        List<student> list = studentService.getStudentById(userId);
        data.put("count", list.size());
        data.put("data", list);
        return data.toJSONString();
    }
    //添加学生信息
    @ResponseBody
    @RequestMapping(value = "/addStudent", produces = "application/json;charset=utf-8")
    public String addStudent(HttpServletRequest req) {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "addStudent");
        String userId = req.getParameter("userId");
        String realName = req.getParameter("realName");
        String major = req.getParameter("major");
        String sexStr = req.getParameter("sex");
        String phone = req.getParameter("phone");
        String levels = req.getParameter("levels");
        String classes = req.getParameter("classes");
        String addr = req.getParameter("addr");
        String email = req.getParameter("addr");
        student stu = new student(userId, major, realName, sexStr, phone, levels,classes,addr,email);
        int addNumber = studentService.addStudent(stu);
        if (addNumber > 0) {
            data.put("success", "1");
            data.put("msg", "添加成功");
        } else {
            data.put("success", "0");
            data.put("msg", "添加失败");
        }
        data.put("code", 0);
        data.put("count", addNumber);
        data.put("data", null);
        return data.toJSONString();
    }
    //删除学生信息
    @ResponseBody
    @RequestMapping(value = "/removeStudent", produces = "application/json;charset=utf-8")
    public String removeStudent(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        String userId = request.getParameter("userId");
        data.put("code", 0);
        data.put("msg", "removeStudent");
        int i = studentService.removeStudent(userId);
        if (i > 0) {
            data.put("success", "1");
            data.put("msg", "删除成功");
            //studentList = mapper.getStudents();
        } else {
            data.put("success", "0");
            data.put("msg", "删除失败");
            //studentList = mapper.getStudents();
        }
        data.put("code", 0);
        data.put("count", null);
        data.put("data", null);
        return data.toJSONString();
    }
    //修改学生信息
    @ResponseBody
    @RequestMapping(value = "/updateStudent", produces = "application/json;charset=utf-8")
    public String updateStudent(HttpServletRequest req){
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "updateStudent");
        String userId = req.getParameter("userId");
        String realName = req.getParameter("realName");
        String major = req.getParameter("major");
        String sexStr = req.getParameter("sex");
        String phone = req.getParameter("phone");
        String levels = req.getParameter("levels");
        String classes = req.getParameter("classes");
        String addr = req.getParameter("addr");
        String email = req.getParameter("email");
        student stu = new student(userId, major, realName, sexStr, phone, levels,classes,addr,email);
        int addNumber = studentService.updateStudent(stu);
        if (addNumber > 0) {
            data.put("success", "1");
            data.put("msg", "修改成功");
        } else {
            data.put("success", "0");
            data.put("msg", "修改失败");
        }
        data.put("code", 0);
        data.put("count", addNumber);
        data.put("data", null);
        return data.toJSONString();
    }

}
