package com.scs.controller;

import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.portrait;
import com.scs.service.portraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.UUID;

@Controller
@RequestMapping("/Portrait")
public class PortraitController {
    @Autowired
    private portraitService portraitService;


    //MultipartFile 后面的值 必须和表单的name属性一致
    @RequestMapping(value = "/uploadPortrait" ,method = RequestMethod.POST,consumes = "multipart/form-data",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String uploadPortrait(HttpServletRequest request, @RequestParam(value="portrait") MultipartFile portrait, HttpServletResponse response) throws Exception {
        //获取要存放的位置,request.getSession().getServletContext()  获取项目路径
        String path = request.getSession().getServletContext().getRealPath("");
        //C:\Users\帅\IdeaProjects\Smart_Campus_System\SCS\
        String savePath = path.substring(0, path.indexOf("target\\response\\"))+"src\\portrait\\";
        System.out.println(savePath);
        File file = new File(savePath);
        //判断该文件夹是否存在
        if (!file.exists()){
            file.mkdirs();
        }
        //获取要上传的文件名
        String filename = portrait.getOriginalFilename();
        //将文件名设置唯一值，以uuid_原名.jpg的方式存放
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        //设置最终的存放在服务器上的文件的名字
        String p_name=uuid+"_"+filename;
        System.out.println(p_name);
        //存放在服务器的地址为路径加文件名
        String p_path=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/portrait/"+p_name;
        //获取当前要上传头像的用户的用户名和角色
        String userid = (String) request.getSession().getAttribute("userInformation");
        System.out.println(userid);
        String role = (String) request.getSession().getAttribute("role");
        com.scs.pojo.portrait por = portraitService.getPortraitById(userid);
        JSONObject data = new JSONObject();
        //当已经存在该用户头像
        if (por!=null){
            if (por.getUserId().equals(userid)){
                //获取原来存在服务器的头像
                com.scs.pojo.portrait portraitById = portraitService.getPortraitById(userid);
                String p_name_origin = portraitById.getP_name();
                File fileExist = new File(savePath+p_name_origin);
                //删除原来的头像
                fileExist.delete();
                //更新数据库
                int count = portraitService.updatePortrait(p_name,p_path,userid);
                if(count==1){
                    //上传成功返回的信息
                    data.put("success",1);
                    data.put("msg","头像上传成功");
                    data.put("p_path",p_path);
                }
                else{
                    data.put("success",0);
                    data.put("msg","头像上传失败");
                    data.put("p_path","");
                }
                //上传头像到服务器储存
                System.out.println(1);
                portrait.transferTo(new File(savePath+p_name));
                System.out.println(2);
            }
        }
        //当还没有该头像信息
        else {
            //将信息存放到服务器
            int upNumber = portraitService.savePortrait(new portrait(userid, role, p_name, p_path));
            if (upNumber==1){
                //上传成功返回的信息
                data.put("success",1);
                data.put("msg","头像上传成功");
                data.put("p_path",p_path);
            }
            else {
                data.put("success",0);
                data.put("msg","头像上传失败");
                data.put("p_path","");
            }
            portrait.transferTo(new File(savePath+p_name));
        }
        return data.toJSONString();
    }
}
