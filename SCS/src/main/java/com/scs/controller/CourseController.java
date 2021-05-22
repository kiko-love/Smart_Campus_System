package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.course;
import com.scs.service.courseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController{
    @Autowired
    private courseService courseService;
    @ResponseBody
    @RequestMapping(value="/getCourse", produces = "application/json;charset=utf-8")
    //管理员端查询课程
    public String getCourse(){
        JSONObject data = new JSONObject();
        List<course> courseList = courseService.getCourse();
        System.out.println(courseList.get(0));
        data.put("data",courseList);
        System.out.println(data);
        return data.toJSONString();
    }
    //修改课程
    @ResponseBody
    @RequestMapping(value = "updateCourse",produces = "application/json;charset=utf-8")
    public String updateCourse(HttpServletRequest request){
        JSONObject data = new JSONObject();
        String courseid = request.getParameter("courseid");
        String coursename = request.getParameter("coursename");
        String coursescore = request.getParameter("coursescore");
        String coursetime = request.getParameter("coursetime");
        String coursemajor = request.getParameter("coursemajor");
        course course = new course(courseid,coursename,coursescore,coursetime,coursemajor);
        int count = courseService.updateCourse(course);
        if(count>0){
            data.put("success","1");
            data.put("msg","修改成功");
        }else{
            data.put("success","0");
            data.put("msg","修改失败");
        }
        data.put("count",count);
        data.put("data",null);
        return data.toJSONString();
    }
    //插入课程
    @ResponseBody
    @RequestMapping(value = "/insertCourse",produces = "application/json;charset=utf-8")
    public String insertCourse(HttpServletRequest request){
        JSONObject data = new JSONObject();
        String courseid = request.getParameter("courseid");
        String coursename = request.getParameter("coursename");
        String coursescore = request.getParameter("coursescore");
        String coursetime = request.getParameter("coursetime");
        String coursemajor = request.getParameter("coursemajor");
        course course = new course(courseid,coursename,coursescore,coursetime,coursemajor);
        int count = courseService.insertCourse(course);
        if(count>0){
            data.put("success","1");
            data.put("msg","添加成功");
        }else{
            data.put("success","0");
            data.put("msg","添加失败");
        }
        data.put("count",count);
        data.put("data",null);
        return data.toJSONString();
    }
    @ResponseBody
    @RequestMapping(value = "/deleteCourse",produces = "application/json;charset=utf-8")
    public String deleteCourse(HttpServletRequest request){
        JSONObject data = new JSONObject();
        String courseId = request.getParameter("courseid");
        int count = courseService.deleteCourse(courseId);
        if(count>0){
            data.put("success","1");
            data.put("msg","删除成功");
        }else{
            data.put("success","0");
            data.put("msg","删除失败");
        }
        data.put("count",count);
        data.put("data",null);
        return data.toJSONString();
    }
    @ResponseBody
    @RequestMapping(value = "batchRemoveStudent",produces = "application/json;charset=utf-8")
    public String batchRemoveStudent(HttpServletRequest request){
        JSONObject data = new JSONObject();
       // List<String> List = JSONObject.parseArray(request.getParameter("userIds"),String.class);
        List<String> List = new ArrayList<>();
        List.add("aaa");
        List.add("bbb");
        int count = courseService.batchRemoveCourse(List);
        if(count>0){
            data.put("success","1");
            data.put("msg","删除成功");
        }else{
            data.put("success","0");
            data.put("msg","删除失败");
        }
        data.put("count",count);
        data.put("data",null);
        return data.toJSONString();
    }
}
