package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.resource;
import com.scs.service.resourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Resource")
public class resourceController {
    @Autowired
    private resourceService resourceService;
    @RequestMapping(value = "/uploadResource",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    //MultipartFile 后面的值 必须和表单的name属性一致
    @ResponseBody
    public String uploadResource(HttpServletRequest request, @RequestParam(value="resource") CommonsMultipartFile files[]) throws Exception {
        //获取要存放的位置,request.getSession().getServletContext()  获取项目路径
        String savePath = request.getSession().getServletContext().getRealPath("/resource/");
        File file = new File(savePath);
        //判断该文件夹是否存在
        if (!file.exists()){
            file.mkdirs();
        }
        JSONObject data = new JSONObject();
        List<String> filenameArray = new ArrayList<>();
        List<String> filepathArray = new ArrayList<>();
        int []count = new int[files.length];
        for(int i=0;i<files.length;i++){
            //获取要上传的文件名
            String filename = files[i].getOriginalFilename();
            String filepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/resource/"+filename;
            HttpSession session = request.getSession();
            String teacherId = (String) session.getAttribute("userInformation");
            resource resInfo = resourceService.getResInfo(filename);

            //该文件已经存在

            if(resInfo!=null){
                //覆盖原来的文件
                 count[i]= resourceService.updateRes(filepath, filename);
                files[i].transferTo(new File(savePath+filename));
            }
            else {
                //存文件的信息到数据库
                 count[i] = resourceService.saveRes(new resource(null, filename, teacherId, filepath));
                files[i].transferTo(new File(savePath + filename));
            }

            filenameArray.add(filename);
            filepathArray.add(filepath);
        }
        if (count.length== files.length){
            data.put("filenameArray",filenameArray);
            data.put("success",1);
            data.put("msg","文件上传成功");
            data.put("filepath",filepathArray);

        }
        else{
            data.put("filename",filenameArray);
            data.put("success",0);
            data.put("msg","文件上传失败");
            data.put("filepath","");
        }
        return data.toJSONString();
    }

}
