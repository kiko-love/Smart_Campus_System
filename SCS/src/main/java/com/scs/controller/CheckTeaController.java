package com.scs.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.scs.pojo.checkTeacher;
import com.scs.service.checkTeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/CheckTea")
public class CheckTeaController {
    @Autowired
    private checkTeaService checkTeaService;
    @ResponseBody
    @RequestMapping(value = "/getCheckInfoById",produces = "application/json;charset=utf-8" )
    public String getCheckInfoById(HttpServletRequest request){
        String teacherId = (String) request.getSession().getAttribute("userInformation");
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "getCheckInfoById");
        ArrayList<Date> checkDates = new ArrayList<>();
        if(teacherId!=null){
            List<checkTeacher> checkTeachers = checkTeaService.selectById(teacherId);
            for(checkTeacher checkTeacher:checkTeachers){
                checkDates.add(checkTeacher.getCheckDate());
            }
            String str = JSON.toJSONStringWithDateFormat(checkDates, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
            JSONArray jsonArray = JSONArray.parseArray(str);
            data.put("success",1);
            data.put("teacherId",teacherId);
            data.put("data",jsonArray);
            return data.toJSONString();
        }
        else {
            data.put("success",0);
            data.put("data","");
            return data.toJSONString();
        }
    }
}
