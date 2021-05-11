package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.resource;
import com.scs.pojo.teacher;
import com.scs.service.resourceService;
import com.scs.service.teacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.scs.utils.charset.charset;

@Controller
@RequestMapping("/Resource")
public class resourceController {
    @Autowired
    private resourceService resourceService;
    @Autowired
    private teacherService teacherService;
    @RequestMapping(value = "/uploadResource",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    //MultipartFile 后面的值 必须和表单的name属性一致
    @ResponseBody
    public String uploadResource(HttpServletRequest request, @RequestParam(value="resource") CommonsMultipartFile files[] ,String profession) throws Exception {
        //获取要存放的位置,request.getSession().getServletContext()  获取项目路径,不同种类的学科的资料存放在相应的文件夹
        String savePath = request.getSession().getServletContext().getRealPath("/resource/"+profession+"/");
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
            //获取可访问该文件的地址
            String filepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/resource/"+profession+"/"+filename;
            HttpSession session = request.getSession();
            //获取上传该文件的老师
            String teacherId = (String) session.getAttribute("userInformation");
            //查看该文件是否已经存在
            resource resInfo = resourceService.getResInfoById(filename,profession);
            //如果该文件已经存在，覆盖原来的文件
            if(resInfo!=null){
                count[i]= resourceService.updateRes(filepath, filename,profession);
                files[i].transferTo(new File(savePath+filename));
            }
            //文件不存在，将资料存在服务器，信息存到数据库
            else {
                count[i] = resourceService.saveRes(new resource(null, filename, teacherId, filepath,profession));
                files[i].transferTo(new File(savePath + filename));
            }
            //将上传的文件的名字，地址，放到集合
            filenameArray.add(filename);
            filepathArray.add(filepath);
        }
        if (count.length== files.length){
            data.put("filenameArray",filenameArray);
            data.put("success",1);
            data.put("msg","文件上传成功");
            data.put("filepath",filepathArray);
            data.put("profession",profession);
        }
        else{
            data.put("filename",filenameArray);
            data.put("success",0);
            data.put("msg","文件上传失败");
            data.put("filepath","");
            data.put("profession",profession);
        }
        return data.toJSONString();
    }


    @RequestMapping(value = "/downloadResource",produces = "application/json;charset=utf-8")
    //MultipartFile 后面的值 必须和表单的name属性一致
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,  String filename,String profession) throws Exception {

        String fileName = resourceService.getResInfoById(filename,profession).getFileName();
        String filepath = request.getSession().getServletContext().getRealPath("/resource/"+profession+"/")+fileName;
        File file = new File(filepath);
        //获取输入流
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        //处理中文文件名乱码，charset为自定义方法
        String downloadFilename = charset(request,fileName);
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + downloadFilename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //获取输出流
        OutputStream out = response.getOutputStream();
        //设置缓冲区
        byte buffer[]=new byte[1024];
        int len=0;
        while((len=inputStream.read(buffer))!=-1){
            out.write(buffer,0,len);
        }
        out.close();
        inputStream.close();
    }
    @RequestMapping(value = "/getProfessionInfo",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getProfessionInfo(){
        JSONObject data = new JSONObject();
        //查询当前的资料的所有种类
        List<String> professions = resourceService.selectProfession();
        if(professions.size()>0){
            data.put("professions",professions);
            data.put("success",1);
        }
        else{
            data.put("professions","");
            data.put("success",0);
        }
        return data.toJSONString();
    }
    @RequestMapping(value = "/getTeacherInfo",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getTeacherInfo(String profession){
        JSONObject data = new JSONObject();
        ArrayList<String> teaName = new ArrayList<>();
        //查询当前的资料的所有老师
        List<String> teacherIds = resourceService.getTeacherId(profession);
        if(teacherIds.size()>0){
            for(int i=0;i<teacherIds.size();i++) {
                List<teacher> teacher = teacherService.getTeacherById(teacherIds.get(i));
                String realName = teacher.get(0).getRealName();
                teaName.add(realName);
            }
            data.put("teacherName",teaName);
            data.put("teacherId",teacherIds);
            data.put("success",1);
        }
        else {
            data.put("teacherName","");
            data.put("teacherId","");
            data.put("success",0);
        }
        return data.toJSONString();
    }
    @RequestMapping(value = "/getResources",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getResources(String profession,String teacherId){
        JSONObject data = new JSONObject();
        ArrayList<String> fileNameList = new ArrayList<>();
        ArrayList<String> filePathList = new ArrayList<>();
        //查询当前的资料信息
        List<resource> resInfo = resourceService.getResInfo(teacherId, profession);
        for (resource res: resInfo) {
            String fileName = res.getFileName();
            String filePath = res.getFilePath();
            fileNameList.add(fileName);
            filePathList.add(filePath);
        }
        if (resInfo.size()>0){
            data.put("filePath",filePathList);
            data.put("fileName",fileNameList);
            data.put("success",1);
        }
        else {
            data.put("filePath","");
            data.put("fileName","");
            data.put("success",0);
        }
        return data.toJSONString();
    }
}
