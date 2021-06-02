package com.scs.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.scs.pojo.CheckRecord;
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
        ArrayList<String> checkDates = new ArrayList<>();
        if(teacherId!=null){
            List<checkTeacher> checkTeachers = checkTeaService.selectById(teacherId);
            for(checkTeacher checkTeacher:checkTeachers){
                checkDates.add(checkTeacher.getCheckDate());
            }
            String str = JSON.toJSONStringWithDateFormat(checkDates, "yyyy-MM-dd",
                    SerializerFeature.WriteDateUseDateFormat);
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

    @ResponseBody
    @RequestMapping(value = "/getCheckRecords",produces = "application/json;charset=utf-8" )
    public String getCheckRecords(HttpServletRequest request){
        JSONObject data = new JSONObject();
        data.put("code", 0);
        data.put("msg", "getCheckRecords");
        List<CheckRecord> records = checkTeaService.checkRecords();
        String str = JSON.toJSONStringWithDateFormat(records, "yyyy-MM-dd",
                SerializerFeature.WriteDateUseDateFormat);
        JSONArray jsonArray = JSONArray.parseArray(str);
        data.put("count", records.size());
        data.put("data",jsonArray);
        return data.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/getRangeCheckRecords",produces = "application/json;charset=utf-8" )
    public String getRangeCheckRecords(HttpServletRequest request){
        String begin = request.getParameter("begin");
        String end = request.getParameter("end");
        JSONObject data = new JSONObject();
        if (begin.equals("") || end.equals("")){
            data.put("code", 110);
            data.put("msg", "日期参数获取失败");
            data.put("count", 0);
            data.put("data","");
        }else {
            data.put("code", 0);
            data.put("msg", "getRangeCheckRecords");
            List<CheckRecord> records = checkTeaService.RangeCheckRecords(begin,end);
            String str = JSON.toJSONStringWithDateFormat(records, "yyyy-MM-dd",
                    SerializerFeature.WriteDateUseDateFormat);
            JSONArray jsonArray = JSONArray.parseArray(str);
            data.put("count", records.size());
            data.put("data",jsonArray);
        }
        return data.toJSONString();
    }
}
