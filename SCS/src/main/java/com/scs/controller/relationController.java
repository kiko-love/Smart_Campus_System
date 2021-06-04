package com.scs.controller;

import com.scs.pojo.course;
import com.scs.pojo.relation;
import com.scs.pojo.teacher;
import com.scs.service.courseService;
import com.scs.service.relationService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/relation")
public class relationController {
    @Autowired
    private relationService relationService;
    @Autowired
    private courseService courseService;
    @ResponseBody
    @RequestMapping(value = "/relationInsert",produces = "application/json;charset=utf-8")
    //关系表的插入操作
    public String relationInsert(HttpServletRequest request){
        JSONObject data = new JSONObject();
        String collegeId = request.getParameter("collegeId");
        String collegeName = request.getParameter("collegeName");
        String majorId = request.getParameter("majorId");
        String majorName = request.getParameter("majorName");
        String classId = request.getParameter("classId");
        String classes = request.getParameter("classes");
        String courseId = request.getParameter("courseId");
        String courseName = request.getParameter("courseName");
        String teacherId = request.getParameter("teacherId");
        String teacherName = request.getParameter("teacherName");
        relation item = new relation("0",collegeId,collegeName,majorId,majorName,classId,classes,courseId,courseName,teacherId,teacherName);
        int count = relationService.relationInsert(item);
        if(count!=1){
            data.put("success",0);
            data.put("msg","数据插入失败");
        }else{
            data.put("success",1);
            data.put("msg","数据插入成功");
;        }
        return data.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/getAllRelation",produces = "application/json;charset=utf-8")
    //获得所有关系数据
    public String getAllRelation(){
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "getData");
        List<relation> relationList = relationService.getAllRelation();
        data.put("data",relationList);
        data.put("count",relationList.size());
        return data.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/batchDeleteRelation",produces = "application/json;charset=utf-8")
    //批量删除关系
    public String batchDeleteRelation(HttpServletRequest request){
        JSONObject data = new JSONObject();
        List<String> List = JSONObject.parseArray(request.getParameter("relationId"),String.class);
        data.put("code", 0);
        data.put("msg", "batchDeleteRelation");
        int count = relationService.batchDeleteRelation(List);
        if (count > 0) {
            data.put("success", "1");
            data.put("msg", "删除选中关系成功");
        } else {
            data.put("success", "0");
            data.put("msg", "删除选中关系失败");
        }
        data.put("count", count);
        data.put("data", null);
        return data.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/selectCourseByTeacherId",produces = "application/json;charset=utf-8")
    //通过teacherId获得课程信息
    public String selectCourseByTeacherId(HttpServletRequest request, HttpSession session){
        JSONObject data = new JSONObject();
        //teacher teacher = (com.scs.pojo.teacher) session.getAttribute("teacher");
       // String teacherId = teacher.getTeacherId();
        String teacherId = request.getParameter("teacherId");
        List<relation> relationList = relationService.selectRelationByTeacherId(teacherId);
        if(relationList.size()==0){
            data.put("success",0);
            data.put("msg","未查询到该教师与课程的关联");
            return data.toJSONString();
        }
        List<String> courseIdList = new ArrayList<>();
        for(relation item : relationList){
            String courseId = item.getCourseId();
            courseIdList.add(courseId);
        }
        List<course> courseList = courseService.batchSelectCourse(courseIdList);
        if (courseList.size()!=0){
            data.put("success",1);
            data.put("msg","查询课程成功");
            //data.put("realName",teacher.getRealName());
            data.put("data", courseList);
            return data.toJSONString();
        }else
        {
            data.put("success",0);
            data.put("msg","查询课程失败");
            //data.put("realName",teacher.getRealName());
            data.put("count",0);
            return data.toJSONString();
        }

    }

}
