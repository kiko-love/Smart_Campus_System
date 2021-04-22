package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.student;
import com.scs.service.UserService;
import com.scs.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/stu")
public class StudentController {
    @Autowired
    private studentService studentService;

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

    @ResponseBody
    @RequestMapping(value = "/addStudent", produces = "application/json;charset=utf-8")
    public String addStudent(HttpServletRequest req) {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "addStudent");
        String userId = req.getParameter("userId");
        String realName = req.getParameter("realName");
        String department = req.getParameter("department");
        String sexStr = req.getParameter("sex");
        String phone = req.getParameter("phone");
        String levels = req.getParameter("levels");
        student stu = new student(userId, department, realName, sexStr, phone, levels);
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
}
