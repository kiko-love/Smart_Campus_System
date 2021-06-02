package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.classInfo;
import com.scs.service.ClassInfoService;
import com.scs.service.impl.ClassInfoServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ClassInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller()
@RequestMapping(value = "ClassInfo")
public class ClassInfoController {
    @Autowired
    private ClassInfoService classInfoService;
    @ResponseBody
    @RequestMapping(value = "getClassInfo",produces = "application/json;charset=utf-8")
    //管理员查询已有班级
    public String getClassInfo(){
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "getData");
        List<ClassInfo> list= classInfoService.getClassInfo();
        data.put("count", list.size());
        data.put("data", list);
        return data.toJSONString();
    }
    @ResponseBody
    @RequestMapping(value = "insertClassInfo",produces = "application/json;charset=utf-8")
    //插入班级表
    public String insertClassInfo(HttpServletRequest request){
        JSONObject data = new JSONObject();
        String classes = request.getParameter("classes");
        String classId = request.getParameter("classId");
        String counselor = request.getParameter("counselor");
        String majorName = request.getParameter("majorName");
        String counselorId = request.getParameter("counselorId");
        classInfo classInfo = new classInfo(classes,classId,majorName,counselor,counselorId);
        int count = classInfoService.insertClassInfo(classInfo);
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
    @RequestMapping(value = "updateClassInfo",produces = "application/json;charset=utf-8")
    //修改班级表
    public String updateClassInfo(HttpServletRequest request){
        JSONObject data = new JSONObject();
        String classes = request.getParameter("classes");
        String classId = request.getParameter("classId");
        String counselor = request.getParameter("counselor");
        String majorName = request.getParameter("majorName");
        String counselorId = request.getParameter("counselorId");
        classInfo classInfo = new classInfo(classes,classId,majorName,counselor,counselorId);
        int count = classInfoService.updateClassInfo(classInfo);
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
    @ResponseBody
    @RequestMapping(value = "deleteClassInfo",produces = "application/json;charset=utf-8")
    //批量删除
    public String deleteClassInfo(HttpServletRequest request){
        JSONObject data = new JSONObject();
        String classId = request.getParameter("classId");
        int count = classInfoService.deleteClassInfo(classId);
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
    @RequestMapping(value = "/batchDeleteMajor", produces = "application/json;charset=utf-8")
    public String batchDeleteMajor(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        List<String> List = JSONObject.parseArray(request.getParameter("classId"),String.class);
        System.out.println(List);
        ;       data.put("code", 0);
        data.put("msg", "batchRemoveStudent");
        int removeNumber = classInfoService.batchDeleteClassInfo(List);
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


}
