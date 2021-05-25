package com.scs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.scs.pojo.major;
import com.scs.pojo.teacher;
import com.scs.service.majorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Major")
public class majorController {
    @Autowired
    private majorService majorService;

    /**
     * 添加专业信息
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMajor", produces = "application/json;charset=utf-8")
    public String addMajor(HttpServletRequest req) {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        String majorName = req.getParameter("majorName");
        String majorType = req.getParameter("majorType");
        String majorYear = req.getParameter("majorYear");
        String academy = req.getParameter("academy");
        List<major> oneMajor = majorService.selectByMajorName(majorName);
        //当添加的专业已经存在
        if (oneMajor.size() > 0) {
            data.put("success", "0");
            data.put("msg", "该用户已存在");
            data.put("data", "");
            return data.toJSONString();
        }
        Integer majorId;
        List<major> allMajor = majorService.selectAllMajor();
        //为首条记录id赋值为10000
        if (allMajor.size() == 0) {
            majorId = 10000;
        } else {
            //major为最后一条课程id+1
            majorId = allMajor.get(allMajor.size() - 1).getMajorId() + 1;
        }
        major major = new major(majorId, majorName, majorType, majorYear, academy);
        int count = majorService.addMajor(major);
        //添加成功
        if (count == 1) {
            data.put("success", "1");
            data.put("msg", "添加成功");
            data.put("data", null);
            return data.toJSONString();
        }
        data.put("success", "2");
        data.put("msg", "添加失败");
        data.put("data", "");
        return data.toJSONString();
    }

    /**
     * 获取全部专业信息
     *
     * @param request
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value = "/getMajors", produces = "application/json;charset=utf-8")
    public String getMajors(HttpServletRequest request) throws ParseException {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        List<major> majors = majorService.selectAllMajor();
        if (majors.size() == 0) {
            data.put("success", 0);
            data.put("msg", "无专业信息");
            data.put("data", "");
            data.put("count", 0);
            return data.toJSONString();
        }
        data.put("success", 1);
        data.put("msg", "获取专业信息成功");
        data.put("data", majors);
        data.put("count", majors.size());
        return data.toJSONString();
    }

    /**
     * 通过MajorName获取major信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMajorByName", produces = "application/json;charset=utf-8")
    public String getMajorByName(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        String majorName = request.getParameter("majorName");
        if (majorName==null || majorName.equals("")){
            List<major> majors = majorService.selectAllMajor();
            data.put("count", majors.size());
            data.put("msg", "查询成功");
            data.put("success", 1);
            data.put("data", majors);
            return data.toJSONString();
        }
        List<major> oneMajor = majorService.selectByMajorName(majorName);
        if (oneMajor.size() > 0) {
            data.put("count", oneMajor.size());
            data.put("msg", "查询成功");
            data.put("success", 1);
            data.put("data", oneMajor);
            return data.toJSONString();
        }
        data.put("count", oneMajor.size());
        data.put("msg", "查询失败");
        data.put("success", 0);
        data.put("data", "");
        return data.toJSONString();
    }

    /**
     * 删除某条专业信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteMajor", produces = "application/json;charset=utf-8")
    public String deleteMajor(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        String majorName = request.getParameter("majorName");
        int count = majorService.deleteMajor(majorName);
        if (count == 1) {
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

    /**
     * 更新专业信息
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateMajor", produces = "application/json;charset=utf-8")
    public String updateMajor(HttpServletRequest req) {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        String majorName = req.getParameter("majorName");
        String majorType = req.getParameter("majorType");
        String majorYear = req.getParameter("majorYear");
        String academy = req.getParameter("academy");
        String majorIdString = req.getParameter("majorId");
        Integer majorId = Integer.valueOf(majorIdString);
        major major = new major(majorId, majorName, majorType, majorYear, academy);
        int count = majorService.updateMajor(major);
        //添加成功
        if (count == 1) {
            data.put("success", "1");
            data.put("msg", "修改成功");
            data.put("data", null);
            return data.toJSONString();
        }
        data.put("success", "0");
        data.put("msg", "修改失败");
        data.put("data", "");
        return data.toJSONString();
    }

    /**
     * 批量删除专业信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/batchRemoveMajor", produces = "application/json;charset=utf-8")
    public String batchRemoveMajor(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        //获取勾选的所有需要删除的老师的id
        List<String> List = JSONObject.parseArray(request.getParameter("majorIds"), String.class);
        data.put("code", 0);
        data.put("msg", "batchRemoveMajor");
        //执行删除
        int removeNumber = majorService.batchRemoveMajors(List);
        if (removeNumber > 0) {
            data.put("success", "1");
            data.put("msg", "删除选中专业成功");
        } else {
            data.put("success", "0");
            data.put("msg", "删除选中专业失败");
        }
        data.put("count", removeNumber);
        data.put("data", null);
        return data.toJSONString();
    }
}
