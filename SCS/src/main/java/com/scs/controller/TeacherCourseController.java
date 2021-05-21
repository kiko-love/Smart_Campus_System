package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.course;
import com.scs.service.TeacherCourseService;
import com.scs.service.courseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.scs.pojo.RelationTeacherCourse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/TeacherCourseController")
public class TeacherCourseController {
    @Autowired
    private courseService courseService;
    @Autowired
    private TeacherCourseService teacherCourseService;

    @ResponseBody
    @RequestMapping(value = "/SelectCourseByTeacherId",produces = "application/json;charset=utf-8")
    public String SelectCourseByTeacherId(HttpServletRequest request){
        JSONObject data = new JSONObject();
        String teacherid = request.getParameter("teacherid");
        List<String> list = teacherCourseService.SeclectTeacherCourse(teacherid);//查询courseid by teacherId
        System.out.println(list);
        List<course> courselist = courseService.batchSelectCourse(list);
        if(courselist.size()==0){
            data.put("success",0);
            data.put("msg","未查找到该教师对应教学科目");
            data.put("count",0);
            return data.toJSONString();
        }
        data.put("success",1);
        data.put("msg","查找到该教师对应教学科目");
        data.put("data",courselist);
        return data.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/InsertTeacherCourse")
    public String InsertTeacherCourse(HttpServletRequest request){
        JSONObject data = new JSONObject();
        String courseid = request.getParameter("courseid");
        String teacherid = request.getParameter("teacherid");
        RelationTeacherCourse item = new RelationTeacherCourse(teacherid,courseid);
        int count = teacherCourseService.InsertTeacherCourse(item);
        if(count>0){
            data.put("success","1");
            data.put("msg","数据插入成功");
        }else{
            data.put("success","0");
            data.put("msg","数据插入失败");
        }
        return data.toJSONString();
    }


}
