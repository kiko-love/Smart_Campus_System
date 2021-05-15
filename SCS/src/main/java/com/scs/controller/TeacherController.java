package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.student;
import com.scs.pojo.teacher;
import com.scs.service.teacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/tea")
public class TeacherController {
    @Autowired
    private teacherService teaService;
    //批量删除老师信息
    @ResponseBody
    @RequestMapping(value = "/batchRemoveTea", produces = "application/json;charset=utf-8")
    public String batchRemoveTea(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        //获取勾选的所有需要删除的老师的id
        List<String> List = JSONObject.parseArray(request.getParameter("teacherIds"),String.class);
        data.put("code", 0);
        data.put("msg", "batchRemoveTea");
        //执行删除
        int removeNumber = teaService.batchRemoveTea(List);
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

    //获取全部老师信息
    @ResponseBody
    @RequestMapping(value = "/getTeachers", produces = "application/json;charset=utf-8")
    public String getTeachers(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "getData");
        List<teacher> list = teaService.getTeachers();
        data.put("count", list.size());
        data.put("data", list);
        return data.toJSONString();
    }
    //根据teacherId查询老师信息
    @ResponseBody
    @RequestMapping(value = "/searchTea", produces = "application/json;charset=utf-8")
    public String searchStudent(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "searchTeacher");
        String TeacherId = request.getParameter("teacherId");
        List<teacher> list = teaService.getTeacherById(TeacherId);
        data.put("count", list.size());
        data.put("data", list);
        return data.toJSONString();
    }
    //添加老师信息
    @ResponseBody
    @RequestMapping(value = "/addTea", produces = "application/json;charset=utf-8")
    public String addTea(HttpServletRequest req) {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "addTeacher");
        String teacherId = req.getParameter("teacherId");
        String realName = req.getParameter("realName");
        String phone = req.getParameter("phone");
        String sex = req.getParameter("sex");
        String collegeId = req.getParameter("collegeId");
        String addr = req.getParameter("addr");
        String birth = req.getParameter("birthday");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
             birthday = fmt.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        teacher tea = new teacher(teacherId, realName, phone, sex, collegeId, addr,birthday);
        int addNumber = teaService.addTeacher(tea);
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
    //根据teacherId删除老师信息
    @ResponseBody
    @RequestMapping(value = "/removeTeacher", produces = "application/json;charset=utf-8")
    public String removeTeacher(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        String teacherId = request.getParameter("teacherId");
        data.put("code", 0);
        data.put("msg", "removeTeacher");
        int i = teaService.removeTeacher(teacherId);
        if (i > 0) {
            data.put("success", "1");
            data.put("msg", "删除成功");
        } else {
            data.put("success", "0");
            data.put("msg", "删除失败");
        }
        data.put("code", 0);
        data.put("count", null);
        data.put("data", null);
        return data.toJSONString();
    }
    //修改学生信息
    @ResponseBody
    @RequestMapping(value = "/updateTeacher", produces = "application/json;charset=utf-8")
    public String updateTeacher(HttpServletRequest req){
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "updateStudent");
        String teacherId = req.getParameter("teacherId");
        String realName = req.getParameter("realName");
        String phone = req.getParameter("phone");
        String sex = req.getParameter("sex");
        String collegeId = req.getParameter("collegeId");
        String addr = req.getParameter("addr");
        String birth = req.getParameter("birthday");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
            birthday = fmt.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        teacher tea = new teacher(teacherId, realName, phone, sex, collegeId, addr,birthday);
        int addNumber = teaService.updateTeacher(tea);
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
