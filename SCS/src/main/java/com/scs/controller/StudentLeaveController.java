package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.StudentLeave;
import com.scs.pojo.classInfo;
import com.scs.pojo.student;
import com.scs.service.ClassInfoService;
import com.scs.service.StudentLeaveService;
import com.scs.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("StudentLeave")
public class StudentLeaveController {
    @Autowired
    private StudentLeaveService studentLeaveService;
    @Autowired
    private studentService studentService;
    @Autowired
    private ClassInfoService classInfoService;


    /**
     * 学生请假接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadResource", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String insertInfo(HttpServletRequest request){
        //获取请假人
        String userId = (String) request.getSession().getAttribute("userInformation");
        //请假开始时间
        String startTime = request.getParameter("startTime");
        //请假结束时间
        String endTime = request.getParameter("endTime");
        //请假时有
        String leaveReason = request.getParameter("leaveReason");

        JSONObject data = new JSONObject();
        if(userId==null||userId.equals("")){
            data.put("msg","学生信息获取失败");
            data.put("data","");
            data.put("success",0);
            data.put("code",110);
            return data.toJSONString();
        }
        if(startTime==null||endTime==null||leaveReason==null||startTime.equals("")||endTime.equals("")||leaveReason.equals("")){
            data.put("msg","请假信息填充不充分");
            data.put("data","");
            data.put("success",0);
            data.put("code",110);
            return data.toJSONString();
        }
        //获取这个学生的辅导员
        List<student> student = studentService.getStudentById(userId);
        String classes = student.get(0).getClasses();
        String major = student.get(0).getMajor();
        classInfo classInfo = classInfoService.getInfo(classes, major);
        String counselor = classInfo.getCounselor();
        StudentLeave studentLeave = new StudentLeave(null, userId, startTime, endTime, leaveReason, null, 1,counselor);
        int insertNumber = studentLeaveService.insertInfo(studentLeave);
        if (insertNumber==0){
            data.put("msg","请假操作失败");
            data.put("data","");
            data.put("success",0);
            data.put("code",110);
            return data.toJSONString();
        }
        else{
            data.put("msg","请假操作成功");
            data.put("data","");
            data.put("success",1);
            data.put("code",200);
            return data.toJSONString();
        }
    }


}
