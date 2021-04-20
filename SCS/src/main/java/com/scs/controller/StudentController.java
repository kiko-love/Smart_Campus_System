package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.student;
import com.scs.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/stu")
public class StudentController {
    @Autowired
    private studentService studentService;

    @ResponseBody
    @RequestMapping(value="/getStudents",produces ="application/json;charset=utf-8")
    public String getStudent(HttpServletRequest request){
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "getData");
        List<student> list = studentService.getStudents();
        data.put("count", list.size());
        data.put("data", list);
        return data.toJSONString();
    }
}
