package com.scs.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scs.aip.face.FaceDetect;
import com.scs.aip.face.FaceSearch;
import com.scs.pojo.FaceUser;
import com.scs.pojo.checkTeacher;
import com.scs.service.FaceUserService;
import com.scs.service.checkTeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/aipFace")
public class FaceUserController {

    @Autowired
    private FaceUserService faceUserService;

    @Autowired
    private checkTeaService checkTeaService;


    @ResponseBody
    @RequestMapping(value = "/detect",produces = "application/json;charset=utf-8")
    public String GetDetectInfo(HttpServletRequest req, HttpServletResponse resp){
        String img = req.getParameter("picBASE64");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        FaceDetect detect = new FaceDetect();
        String res = detect.faceDetect(img);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/search",produces = "application/json;charset=utf-8")
    public String GetSearchInfo(HttpServletRequest req,HttpServletResponse resp){
        String img = req.getParameter("picBASE64");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        FaceSearch search = new FaceSearch();
        String res = search.faceSearch(img);
        String faceId = null;
        String result_child = null;

        //将返回的json转换成java对象
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (jsonObject.getString("error_msg").equals("SUCCESS")) {
            //获取result的内容
            result_child = jsonObject.get("result").toString();
            JSONObject faceObject = JSONObject.parseObject(result_child);

            //获取result中user_list数组的第一项内容
            JSONArray tmp = faceObject.getJSONArray("user_list");
            JSONObject array = JSONObject.parseObject(tmp.getString(0));

            //获取人脸ID
            faceId = array.getString("user_id");
            System.out.println("键值：" + faceId);
            if(faceId!=null||faceId.equals("")){
                Date date = new Date();
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String nowTime = sim.format(date);
                checkTeaService.insertCheck(new checkTeacher(faceId,nowTime));
            }
        }
        List<String> list = new ArrayList<>();
        //原有的json数据，做备用
        list.add(res);
        //result的内容
        list.add(result_child);

        list.add(faceId);

        if (list.get(2)!=null){
            FaceUser faceUser = faceUserService.getUserById(faceId);
            String name = faceUser.getName();
            System.out.println(name);

            //将result_child转换成Java对象
            JSONObject result_child_data = JSONObject.parseObject(list.get(1));
            result_child_data.put("name", name);

            //将原有的json数据转换成Java对象
            // 并用result_child覆盖原来result对应的值
            JSONObject result_all = JSONObject.parseObject(list.get(0));
            result_all.put("result",result_child_data );

            return result_all.toString().replaceAll("\\\\","");
        }else {
            return list.get(0);
        }
    }
}
