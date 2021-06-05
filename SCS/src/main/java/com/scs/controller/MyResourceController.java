package com.scs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.*;
import com.scs.service.courseService;
import com.scs.service.myResourceService;
import com.scs.service.resourceService;
import com.scs.service.teacherService;
import com.scs.utils.JsonStatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/MyResource")
public class MyResourceController {
    @Autowired
    private myResourceService myResService;

    @Autowired
    private resourceService resService;


    /**
     * 进行关注
     */
    @ResponseBody
    @RequestMapping(value = "/insertMyResource", produces = "application/json;charset=utf-8")
    public String insertMyResource(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        String courseName = request.getParameter("courseName");
        String teacherId = request.getParameter("teacherId");
        String userId = (String) request.getSession().getAttribute("userInformation");
        if (userId == null || userId.equals("")) {
            data.put("success", 0);
            data.put("msg", "获取学生id失败");
            data.put("data", "");

            return data.toJSONString();
        }
        if (courseName == null || courseName.equals("") || teacherId == null || teacherId.equals("")) {
            data.put("success", 0);
            data.put("msg", "无法获取到要关注的课程或老师");
            data.put("data", "");
            return data.toJSONString();
        }
        System.out.println(teacherId);
        List<myResource> existResource = myResService.selectOneResource(userId, courseName, teacherId);
        if (existResource.size() == 1) {
            data.put("success", 0);
            data.put("msg", "已关注请勿重复关注");
            data.put("data", "");
            return data.toJSONString();
        }
        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String focusTime = sim.format(date);
        System.out.println(focusTime);
        int insertCount = myResService.insertMyResource(new myResource(null, userId, courseName, teacherId, focusTime));
        //关注成功
        if (insertCount == 1) {
            data.put("success", 1);
            data.put("msg", "关注成功");
            data.put("data", "");
            return data.toJSONString();
        }
        data.put("success", 0);
        data.put("msg", "关注失败");
        data.put("data", "");
        return data.toJSONString();
    }

    /**
     * 获取关注的课程
     */
    @ResponseBody
    @RequestMapping(value = "/selectFocusFIle", produces = "application/json;charset=utf-8")
    public String selectFocusFIle(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        String userId = (String) request.getSession().getAttribute("userInformation");
        ArrayList<TeacherResourceOB> jsonList = new ArrayList<>();
        JsonStatusUtils status;
        String courseName = request.getParameter("courseName");
        if (userId == null || userId.equals("")) {
            status = new JsonStatusUtils("110", "获取学生id失败");
            data.put("status", status);
            data.put("data", "");
            return data.toJSONString();
        }
        //查询当前的资料的所有种类
        String level = request.getParameter("level");
        //获取第一层
        if (level == null || level.equals("")) {
            List<String> courseNames = myResService.selectMyFocusCourse(userId);
            //当没有资源
            if (courseNames.size() == 0) {
                status = new JsonStatusUtils("110", "无资源数据");
                data.put("status", status);
                data.put("data", "");
                return data.toJSONString();
            }
            int id = 0;
            for (String course : courseNames) {
                id++;
                jsonList.add(new TeacherResourceOB(id, 0, course, null, "1",
                        null, null, null, true, null, null, null, courseName));
            }
            status = new JsonStatusUtils("200", "获取第一层成功");
            data.put("status", status);
            data.put("data", jsonList);
            return data.toJSONString();
        }
        //获取第二层
        if (level.equals("1")) {
            List<TeacherOfCourseOB> myRes = myResService.selectTeacherOfCourse(userId, courseName);
            if (courseName == null || courseName.equals("")) {
                status = new JsonStatusUtils("110", "获取课程失败");
                data.put("status", status);
                data.put("data", "");
                return data.toJSONString();
            }
            if (myRes.size() == 0) {
                status = new JsonStatusUtils("110", "获取老师失败");
                data.put("status", status);
                data.put("data", "");
                return data.toJSONString();
            }
            if (myRes.size() > 0) {
                String authorityId = request.getParameter("authorityId");
                if (authorityId == null || authorityId.equals("")) {
                    status = new JsonStatusUtils("110", "获取authorityId失败");
                    data.put("status", status);
                    data.put("data", "");
                    return data.toJSONString();
                }
                int parentId = Integer.parseInt(authorityId);
                int id = 1000;

                for (int i = 0; i < myRes.size(); i++) {
                    id++;
                    Integer focusId = myRes.get(i).getFocusId();
                    String teacherId = myRes.get(i).getTeacherId();
                    String fileName = myRes.get(i).getTeacherName();
                    jsonList.add(new TeacherResourceOB(id, parentId, fileName,
                            null, "2",
                            null, fileName, null,
                            true, null, teacherId, focusId, courseName));
                }
                status = new JsonStatusUtils("200", "获取第二层成功");
                data.put("status", status);
                data.put("data", jsonList);
                return data.toJSONString();
            }
        }
        //获取第三层
        if (level.equals("2")) {
            String teacherId = request.getParameter("teacherId");
            if (courseName == null || courseName.equals("")) {
                data.put("msg", "获取课程失败");
                data.put("data", "");
                return data.toJSONString();
            }
            if (teacherId == null || teacherId.equals("")) {
                data.put("msg", "获取老师失败");
                data.put("data", "");
                return data.toJSONString();
            }
            List<resource> focusResources = resService.getResInfo(teacherId, courseName);
            if (focusResources.size() == 0) {
                status = new JsonStatusUtils("110", "没查找到资源");
                data.put("status", status);
                data.put("data", "");
                return data.toJSONString();
            }
            if (focusResources.size() > 0) {
                String authorityId = request.getParameter("authorityId");
                if (authorityId == null || authorityId.equals("")) {
                    status = new JsonStatusUtils("110", "获取authorityId失败");
                    data.put("status", status);
                    data.put("data", "");
                    return data.toJSONString();
                }
                int parentId = Integer.parseInt(authorityId);
                int id = 10000;
                for (int i = 0; i < focusResources.size(); i++) {
                    resource resource = focusResources.get(i);
                    jsonList.add(new TeacherResourceOB(id, parentId, resource.getFileName(),
                            resource.getFilesize(), "3",
                            resource.getCreateTime(), null, resource.getFileId(),
                            false, null, null, null, courseName));
                    id++;
                }
                status = new JsonStatusUtils("200", "获取文件成功");
                data.put("status", status);
                data.put("data", jsonList);
                return data.toJSONString();

            }
        }
        return null;
    }

    /**
     * 取消关注
     */
    @ResponseBody
    @RequestMapping(value = "/deleteFocus", produces = "application/json;charset=utf-8")
    public String deleteFocus(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        List<Integer> focusIds = new ArrayList<>();
        focusIds.add(Integer.parseInt(request.getParameter("focusId")));
        if (focusIds == null || focusIds.size() == 0) {
            data.put("code", 110);
            data.put("msg", "无focusId参数");
            data.put("data", "");
            data.put("success", 0);
            return data.toJSONString();
        }
        //删除单个文件
        myResService.batchDeleteFocus(focusIds);
        data.put("code", 200);
        data.put("msg", "删除关注课程成功");
        data.put("data", "");
        data.put("success", 1);
        return data.toJSONString();
    }


    /**
     * 搜索关注课程列表信息
     */
    @ResponseBody
    @RequestMapping(value = "/getCourseRes", produces = "application/json;charset=utf-8")
    public String getCourseRes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject data = new JSONObject();
        String key = request.getParameter("key");
        if (key == null || key.equals("")) {
            String userId = (String) request.getSession().getAttribute("userInformation");
            List<courseTeaOfFocusOB> allResource = resService.getResInfoToFocus();
            if (allResource.size() == 0) {
                data.put("msg", "暂无资源可添加");
                data.put("data", "");
                data.put("code", "110");
                data.put("count", 0);
                return data.toJSONString();
            }
            ArrayList<FocusResInfoOB> jsonList = new ArrayList<>();
            List<myResource> myResources = myResService.selectMyResById(userId);
            for (int i = 0; i < allResource.size(); i++) {
                String courseName = allResource.get(i).getCourseName();
                String teacherId = allResource.get(i).getTeacherId();
                String courseId = allResource.get(i).getCourseId();
                String teacherName = allResource.get(i).getTeacherName();
                int flag = 0;
                for (int j = 0; j < myResources.size(); j++) {
                    myResource myRes = myResources.get(j);
                    if (courseName.equals(myRes.getCourseName()) && teacherId.equals(myRes.getTeacherId())) {
                        //该课程已关注
                        flag = 1;
                    }
                }
                if (flag != 0) {
                    jsonList.add(new FocusResInfoOB(courseId, courseName, teacherName, teacherId, true));
                }
                if (flag == 0) {
                    jsonList.add(new FocusResInfoOB(courseId, courseName, teacherName, teacherId, false));
                }
            }
            data.put("msg", "可关注资源获取成功");
            data.put("data", jsonList);
            data.put("code", "0");
            data.put("count", jsonList.size());
        } else {
            List<courseTeaOfFocusOB> resourceList = resService.selectCourseRes(key);
            ArrayList<FocusResInfoOB> jsonList = new ArrayList<>();
            String userId = (String) request.getSession().getAttribute("userInformation");
            List<myResource> myResources = myResService.selectMyResById(userId);
            for (int i = 0; i < resourceList.size(); i++) {
                String courseName = resourceList.get(i).getCourseName();
                String teacherId = resourceList.get(i).getTeacherId();
                String courseId = resourceList.get(i).getCourseId();
                String teacherName = resourceList.get(i).getTeacherName();
                int flag = 0;
                for (int j = 0; j < myResources.size(); j++) {
                    myResource myRes = myResources.get(j);
                    if (courseName.equals(myRes.getCourseName()) && teacherId.equals(myRes.getTeacherId())) {
                        //该课程已关注
                        flag = 1;
                    }
                }
                if (flag != 0) {
                    jsonList.add(new FocusResInfoOB(courseId, courseName, teacherName, teacherId, true));
                }
                if (flag == 0) {
                    jsonList.add(new FocusResInfoOB(courseId, courseName, teacherName, teacherId, false));
                }
            }
            data.put("code", "0");
            data.put("msg", "搜索资源完毕");
            data.put("data", jsonList);
            data.put("count", jsonList.size());
        }
        return data.toJSONString();

    }

    /**
     * 获取关注列表
     */
    @ResponseBody
    @RequestMapping(value = "/getFocusRes", produces = "application/json;charset=utf-8")
    public String getFocusRes(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        String userId = (String) request.getSession().getAttribute("userInformation");
        List<courseTeaOfFocusOB> allResource = resService.getResInfoToFocus();
        if (allResource.size() == 0) {
            data.put("msg", "暂无资源可添加");
            data.put("data", "");
            data.put("code", "110");
            data.put("count", 0);
            return data.toJSONString();
        }
        ArrayList<FocusResInfoOB> jsonList = new ArrayList<>();
        List<myResource> myResources = myResService.selectMyResById(userId);
        for (int i = 0; i < allResource.size(); i++) {
            String courseName = allResource.get(i).getCourseName();
            String teacherId = allResource.get(i).getTeacherId();
            String courseId = allResource.get(i).getCourseId();
            String teacherName = allResource.get(i).getTeacherName();
            int flag = 0;
            for (int j = 0; j < myResources.size(); j++) {
                myResource myRes = myResources.get(j);
                if (courseName.equals(myRes.getCourseName()) && teacherId.equals(myRes.getTeacherId())) {
                    //该课程已关注
                    flag = 1;
                }
            }
            if (flag != 0) {
                jsonList.add(new FocusResInfoOB(courseId, courseName, teacherName, teacherId, true));
            }
            if (flag == 0) {
                jsonList.add(new FocusResInfoOB(courseId, courseName, teacherName, teacherId, false));
            }
        }
        data.put("msg", "可关注资源获取成功");
        data.put("data", jsonList);
        data.put("code", "0");
        data.put("count", jsonList.size());
        return data.toJSONString();
    }


}
