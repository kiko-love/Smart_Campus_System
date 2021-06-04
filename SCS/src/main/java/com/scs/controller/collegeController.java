package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.college;
import com.scs.pojo.major;
import com.scs.service.collegeService;
import com.scs.service.majorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/College")
public class collegeController {
    @Autowired
    private collegeService collegeService;

    /**
     * 添加学院信息
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addCollege", produces = "application/json;charset=utf-8")
    public String addCollege(HttpServletRequest req) {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        String collegeId = req.getParameter("collegeId");
        String collegeName = req.getParameter("collegeName");
        String collegeIntroduce = req.getParameter("collegeIntroduce");
        if (collegeId==null||collegeId.equals("")||collegeName==null||collegeName.equals("")){
            data.put("success", "0");
            data.put("msg", "参数传递错误，请重新输入");
            data.put("data", "");
            return data.toJSONString();
        }
        int addNum = collegeService.addCollege(new college(collegeId,collegeName,collegeIntroduce));
        if (addNum == 0) {
            data.put("success", "0");
            data.put("msg", "学院信息添加失败");
            data.put("data", "");
        } else {
            data.put("success", "1");
            data.put("msg", "学院信息添加成功");
            data.put("data", "");
        }
        return data.toJSONString();

    }

    /**
     * 获取学院列表
     *
     */
    @ResponseBody
    @RequestMapping(value = "/getColleges", produces = "application/json;charset=utf-8")
    public String getColleges() throws ParseException {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        List<college> colleges = collegeService.selectAllCollege();
        if (colleges.size() == 0) {
            data.put("success", 0);
            data.put("msg", "暂无学院信息");
            data.put("data", "");
            data.put("count", 0);
            return data.toJSONString();
        }
        data.put("success", 1);
        data.put("msg", "获取学院信息成功");
        data.put("data", colleges);
        data.put("count", colleges.size());
        return data.toJSONString();
    }

    /**
     * 根据Id获取学院信息
     *
     */
    @ResponseBody
    @RequestMapping(value = "/getCollegeById", produces = "application/json;charset=utf-8")
    public String getCollegeById(HttpServletRequest request)  {
        JSONObject data = new JSONObject();
        String collegeId = request.getParameter("collegeId");
        data.put("code", 0);
        List<college> colleges = collegeService.selectCollegeById(collegeId);
        if (colleges.size() == 0) {
            data.put("success", 0);
            data.put("msg", "未找到指定学院Id");
            data.put("data", "");
            data.put("count", 0);
            return data.toJSONString();
        }
        data.put("success", 1);
        data.put("msg", "获取学院信息成功");
        data.put("data", colleges);
        data.put("count", colleges.size());
        return data.toJSONString();
    }



    /**
     * 通过key获取学院信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCollege", produces = "application/json;charset=utf-8")
    public String getCollege(HttpServletRequest request) {
        String key = request.getParameter("key");
        JSONObject data = new JSONObject();
        data.put("code", 0);
        if (key == null || key.equals("")) {
            List<college> colleges = collegeService.selectAllCollege();
            data.put("count", colleges.size());
            data.put("msg", "查询成功");
            data.put("success", 1);
            data.put("data", colleges);
            return data.toJSONString();
        }
        List<college> keyColleges = collegeService.selectCollege(key);
        if (keyColleges.size() > 0) {
            data.put("count", keyColleges.size());
            data.put("msg", "查询成功");
            data.put("success", 1);
            data.put("data", keyColleges);
            return data.toJSONString();
        }
        data.put("count", keyColleges.size());
        data.put("msg", "查询失败");
        data.put("success", 0);
        data.put("data", "");
        return data.toJSONString();
    }

    /**
     * 删除某条学院信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteCollege", produces = "application/json;charset=utf-8")
    public String deleteMajor(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        String collegeName = request.getParameter("collegeName");
        int count = collegeService.deleteCollege(collegeName);
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
     * 更新学院信息
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateCollege", produces = "application/json;charset=utf-8")
    public String updateCollege(HttpServletRequest req) {
        JSONObject data = new JSONObject();
        data.put("code", 0);
        String collegeId = req.getParameter("collegeId");
        String collegeName = req.getParameter("collegeName");
        String collegeIntroduce = req.getParameter("collegeIntroduce");
        int upNum = collegeService.updateCollege(new college(collegeId,collegeName,collegeIntroduce));
        if (upNum == 0) {
            data.put("success", "0");
            data.put("msg", "更新学院信息失败");
        } else {
            data.put("success", "1");
            data.put("msg", "更新学院信息成功");
        }
        data.put("data", "");
        return data.toJSONString();
    }

    /**
     * 批量删除学院信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/batchRemoveCollege", produces = "application/json;charset=utf-8")
    public String batchRemoveCollege(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        //获取勾选的所有需要删除的老师的id
        List<String> collegeIds = JSONObject.parseArray(request.getParameter("collegeIds"), String.class);
        data.put("code", 0);
        data.put("msg", "batchRemoveMajor");
        //执行删除
        int removeNumber = collegeService.batchRemoveCollege(collegeIds);
        if (removeNumber > 0) {
            data.put("success", "1");
            data.put("msg", "删除选中学院成功");
        } else {
            data.put("success", "0");
            data.put("msg", "删除选中学院失败");
        }
        data.put("count", removeNumber);
        data.put("data", null);
        return data.toJSONString();
    }
}
