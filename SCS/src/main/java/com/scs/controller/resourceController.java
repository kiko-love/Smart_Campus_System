package com.scs.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.resource;
import com.scs.pojo.teacher;
import com.scs.service.resourceService;
import com.scs.service.teacherService;
import com.scs.utils.basicDataUtils;
import com.scs.utils.loadFile1Utils;
import com.scs.utils.loadFile2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.scs.utils.charset.charset;

@Controller
@RequestMapping("/Resource")
public class resourceController {
    @Autowired
    private resourceService resourceService;
    @Autowired
    private teacherService teacherService;

    /**
     * 上传文件
     *
     * @param request
     * @param files
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadResource", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    //MultipartFile 后面的值 必须和表单的name属性一致
    @ResponseBody
    public String uploadResource(HttpServletRequest request, @RequestParam(value = "resource") MultipartFile files[]) throws Exception {
        //获取要存放的位置,request.getSession().getServletContext()  获取项目路径,不同种类的学科的资料存放在相应的文件夹
        HttpSession session = request.getSession();
        String course = request.getParameter("course");
        //获取上传该文件的老师
        String teacherId = (String) session.getAttribute("userInformation");
        String path = request.getSession().getServletContext().getRealPath("");
        String savePath = path.substring(0, path.indexOf("target\\response\\")) + "src\\resource\\" + course + "\\" + teacherId + "\\";
        File file = new File(savePath);
        //判断该文件夹是否存在
        if (!file.exists()) {
            file.mkdirs();
        }
        JSONObject data = new JSONObject();
        int[] count = new int[files.length];
        Map<String, String> fileData = new HashMap<String, String>();
        int length = 0;
        //判断实际的文件上传个数
        for (int i = 0; i < files.length; i++) {
            if (!files[i].getOriginalFilename().equals("")) {
                length++;
            }
        }
        for (int i = 0; i < length; i++) {
            //获取要上传的文件名
            String filename = files[i].getOriginalFilename();
            //获取可访问该文件的地址
            String filepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/resource/" + course + "/" + teacherId + "/" + filename;
            //查看该文件是否已经存在
            resource resInfo = resourceService.getResInfoById(filename, course, teacherId);
            //如果该文件已经存在，覆盖原来的文件
            if (resInfo != null) {
                //删除已经存在服务器的同名文件
                File fileExist = new File(savePath + filename);
                fileExist.delete();
                count[i] = resourceService.updateRes(filepath, filename, course);
                files[i].transferTo(new File(savePath + filename));
            }
            //文件不存在，将资料存在服务器，信息存到数据库
            else {
                count[i] = resourceService.saveRes(new resource(null, filename, teacherId, filepath, course));
                files[i].transferTo(new File(savePath + filename));
            }
            fileData.put(filename, filepath);

        }

        if (count.length == files.length) {
            data.put("status", 200);
            data.put("success", 1);
            data.put("msg", "文件上传成功");
            data.put("data", fileData);
        } else {
            data.put("status", 400);
            data.put("success", 0);
            data.put("msg", "文件上传失败");
            data.put("data", "");
        }
        return data.toJSONString();
    }

    /**
     * 下载文件
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/downloadResource", produces = "application/json;charset=utf-8")
    //MultipartFile 后面的值 必须和表单的name属性一致
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //通过
        String filename = request.getParameter("filename");
        String course = request.getParameter("course");
        String teacherId = request.getParameter("teacherId");
        String fileName = resourceService.getResInfoById(filename, course, teacherId).getFileName();
        String path = request.getSession().getServletContext().getRealPath("");
        String filepath = path.substring(0, path.indexOf("target\\response\\")) + "src\\resource\\" + course + "\\" + teacherId + "\\" + fileName;

        File file = new File(filepath);
        //获取输入流
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        //处理中文文件名乱码，charset为自定义方法
        String downloadFilename = charset(request, fileName);
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + downloadFilename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //获取输出流
        OutputStream out = response.getOutputStream();
        //设置缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.close();
        inputStream.close();
    }

    @RequestMapping(value = "/getInfo", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getInfo(HttpServletRequest request) {
        JSONObject jsonData = new JSONObject();
        ArrayList<loadFile2Utils> data = new ArrayList<>();
        loadFile1Utils status = null;
        String level = request.getParameter("level");
        if (level.equals("1")) {
            //获取点击的科目
            String course = request.getParameter("context");
            String nodeId = request.getParameter("nodeId");
            request.getSession().setAttribute("course", course);
            //查询当前的科目资料的所有老师
            List<String> teacherIds = resourceService.getTeacherId(course);
            if (teacherIds.size() > 0) {
                status = new loadFile1Utils("200", "获取第二层成功");
                jsonData.put("status", status);
                for (int i = 0; i < teacherIds.size(); i++) {
                    String id = String.valueOf((1 * 100 + i));
                    List<teacher> teacherName = teacherService.getTeacherById(teacherIds.get(i));
                    data.add(new loadFile2Utils(id, teacherName.get(0).getRealName(), false, nodeId, null, new basicDataUtils(teacherIds.get(i))));
                }
                jsonData.put("data", data);

            } else {
                status = new loadFile1Utils("110", "获取第二层失败");
                jsonData.put("status", status);
                jsonData.put("data", "");
            }
            return jsonData.toJSONString();
        }

        if (level.equals("2")) {
            //获取点击的科目
            String teacherId = request.getParameter("basicData");
            String course = (String) request.getSession().getAttribute("course");
            String nodeId = request.getParameter("nodeId");
            List<resource> resInfo = resourceService.getResInfo(teacherId, course);
            if (resInfo.size() > 0) {
                status = new loadFile1Utils("200", "获取第三层成功");
                jsonData.put("status", status);

                for (int i = 0; i < resInfo.size(); i++) {
                    String id = String.valueOf(1 * 1000 + i);
                    data.add(new loadFile2Utils(id, resInfo.get(i).getFileName(), true, nodeId, null, null));
                }
                jsonData.put("data", data);

            } else {
                status = new loadFile1Utils("110", "获取第三层失败");
                jsonData.put("status", status);
                jsonData.put("data", "");
            }
            return jsonData.toJSONString();
        }

        if (level == null) {
            //查询当前的资料的所有种类
            List<String> courses = resourceService.selectCourse();
            if (courses.size() > 0) {
                status = new loadFile1Utils("200", "获取第一层成功");
                jsonData.put("status", status);
                for (int i = 0; i < courses.size(); i++) {
                    String id = String.valueOf(i + 1);
                    data.add(new loadFile2Utils(id, courses.get(i), false, "2014", null, null));
                }
                jsonData.put("data", data);
            } else {
                status = new loadFile1Utils("110", "获取第一层失败");
                jsonData.put("status", status);
                jsonData.put("data", "");
            }
            return jsonData.toJSONString();
        }
        return null;
    }

}
