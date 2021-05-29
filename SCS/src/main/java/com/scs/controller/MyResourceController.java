package com.scs.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.TeacherResourceOB;
import com.scs.pojo.myResource;
import com.scs.pojo.resource;
import com.scs.pojo.teacher;
import com.scs.service.myResourceService;
import com.scs.service.resourceService;
import com.scs.service.teacherService;
import com.scs.utils.JsonStatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
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
    private teacherService teaService;
    @Autowired
    private resourceService resService;
    /**
     * 进行关注
     */
    @ResponseBody
    @RequestMapping(value = "/insertMyResource",produces = "application/json;charset=utf-8")
    public String insertMyResource(HttpServletRequest request){
        JSONObject data = new JSONObject();
        data.put("code",200);
        String courseName = request.getParameter("courseName");
        String teacherId = request.getParameter("teacherId");
        String userId = (String) request.getSession().getAttribute("userInformation");
        if(userId==null||userId.equals("")){
            data.put("msg","获取学生id失败");
            data.put("data","");
            return data.toJSONString();
        }
        if(courseName==null||courseName.equals("")||teacherId==null||teacherId.equals("")){
            data.put("msg","无法获取到要关注的课程或老师");
            data.put("data","");
            return data.toJSONString();
        }
        System.out.println(teacherId);
        List<myResource> existResource = myResService.selectOneResource(userId, courseName, teacherId);
        if (existResource.size()==1){
            data.put("msg","已关注请勿重复关注");
            data.put("data","");
            return data.toJSONString();
        }
        Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String focusTime = sim.format(date);
        int insertCount = myResService.insertMyResource(new myResource(null,userId, courseName, teacherId, focusTime));
        //关注成功
        if(insertCount==1){
            data.put("msg","关注成功");
            data.put("data","");
            return data.toJSONString();
        }
        data.put("msg","关注失败");
        data.put("data","");
        return data.toJSONString();
    }

    /**
     * 获取关注的课程
     */
    @ResponseBody
    @RequestMapping(value = "/selectFocusFIle",produces = "application/json;charset=utf-8")
    public String selectFocusFIle(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        String userId = (String) request.getSession().getAttribute("userInformation");
        ArrayList<TeacherResourceOB> jsonList = new ArrayList<>();
        JsonStatusUtils status;
        String courseName = request.getParameter("course");
        System.out.println(courseName);
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
                jsonList.add(new TeacherResourceOB(id, 0, course, null, "1", null, null, null, true, null, null,null));
            }
            status = new JsonStatusUtils("200", "获取第一层成功");
            data.put("status", status);
            data.put("data", jsonList);
            return data.toJSONString();
        }
        //获取第二层
        if (level.equals("1")) {
            List<String> teacherIds = myResService.selectTeacherOfCourse(userId, courseName);
            System.out.println(teacherIds);
            if (courseName == null || courseName.equals("")) {
                status = new JsonStatusUtils("110", "获取课程失败");
                data.put("status", status);
                data.put("data", "");
                return data.toJSONString();
            }
            if (teacherIds.size() == 0) {
                status = new JsonStatusUtils("110", "获取老师失败");
                data.put("status", status);
                data.put("data", "");
                return data.toJSONString();
            }
            if (teacherIds.size() > 0) {
                String authorityId = request.getParameter("authorityId");
                if(authorityId==null||authorityId.equals("")){
                    status = new JsonStatusUtils("110", "获取authorityId失败");
                    data.put("status", status);
                    data.put("data", "");
                    return data.toJSONString();
                }
                int parentId = Integer.parseInt(authorityId);
                int id = 1000;
                for (int i = 0; i < teacherIds.size(); i++) {
                    id++;
                    List<teacher> teacher = teaService.getTeacherById(teacherIds.get(i));
                    //获取到该条关注记录
                    List<myResource> thisResource = myResService.selectOneResource(userId, courseName, teacher.get(0).getTeacherId());
                    Integer focusId = thisResource.get(0).getFocusId();
                    jsonList.add(new TeacherResourceOB(id, parentId, null,
                            null, "2",
                            null, teacher.get(0).getRealName(), null,
                            false, null, teacherIds.get(i),focusId));
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
                if(authorityId==null||authorityId.equals("")){
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
                            false, null, null,null));
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
    @RequestMapping(value = "/deleteFocus",produces = "application/json;charset=utf-8")
    public String deleteFocus(HttpServletRequest request){
        JSONObject data = new JSONObject();
        List<Integer> focusIds = JSONArray.parseArray(request.getParameter("focusId"),Integer.class);
        if (focusIds == null || focusIds.size()==0) {
            data.put("code", 110);
            data.put("msg", "无focusId参数");
            data.put("data", "");
            data.put("success",0);
            return data.toJSONString();
        }
        //删除单个文件
        myResService.batchDeleteFocus(focusIds);
        data.put("code", 200);
        data.put("msg", "删除关注课程成功");
        data.put("data", "");
        data.put("success",1);
        return data.toJSONString();
    }
}
