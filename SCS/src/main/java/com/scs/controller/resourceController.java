package com.scs.controller;
import com.alibaba.fastjson.JSONObject;
import com.scs.pojo.resource;
import com.scs.pojo.teacher;
import com.scs.service.resourceService;
import com.scs.service.teacherService;
import com.scs.utils.basicDataUtils;
import com.scs.utils.checkArrUtils;
import com.scs.utils.loadFile1Utils;
import com.scs.utils.loadFile2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/downloadResource", produces = "application/json;charset=utf-8")
    //MultipartFile 后面的值 必须和表单的name属性一致
    @ResponseBody
    public void downloadFiles(HttpServletRequest request, HttpServletResponse response){
        String course = request.getParameter("course");
        String teacherId = request.getParameter("teacherId");
        List<String> filenames = Arrays.asList(request.getParameter("filenames"));
        //响应头的设置
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        //设置压缩包的名字
        //解决不同浏览器压缩包名字含有中文时乱码的问题
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String downloadName = uuid+".zip";
        String agent = request.getHeader("USER-AGENT");
        try {
            if (agent.contains("MSIE")||agent.contains("Trident")) {
                downloadName = java.net.URLEncoder.encode(downloadName, "UTF-8");
            } else {
                downloadName = new String(downloadName.getBytes("UTF-8"),"ISO-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;fileName=\"" + downloadName + "\"");
        //设置压缩流：直接写入response，实现边压缩边下载
        ZipOutputStream zipos = null;
        try {
            zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            zipos.setMethod(ZipOutputStream.DEFLATED); //设置压缩方法
        } catch (Exception e) {
            e.printStackTrace();
        }
        //循环将文件写入压缩流
        DataOutputStream os = null;
        for(int i = 0; i < filenames.size(); i++ ){
            String fileName = resourceService.getResInfoById(filenames.get(i), course, teacherId).getFileName();
            String path = request.getSession().getServletContext().getRealPath("");
            String filepath = path.substring(0, path.indexOf("target\\response\\")) + "src\\resource\\" + course + "\\" + teacherId + "\\" + fileName;

            File file = new File(filepath);
            try {
                //添加ZipEntry，并ZipEntry中写入文件流
                //这里，加上i是防止要下载的文件有重名的导致下载失败
                zipos.putNextEntry(new ZipEntry(i + fileName));
                os = new DataOutputStream(zipos);
                InputStream is = new FileInputStream(file);
                byte[] b = new byte[1024];
                int length = 0;
                while((length = is.read(b))!= -1){
                    os.write(b, 0, length);
                }
                is.close();
                zipos.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //关闭流
        try {
            os.flush();
            os.close();
            zipos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/getInfo", produces = "application/json;charset=utf-8")
    @ResponseBody
    @CrossOrigin
    public String getInfo(HttpServletRequest request) {
        JSONObject jsonData = new JSONObject();
        ArrayList<loadFile2Utils> data = new ArrayList<>();
        loadFile1Utils status = null;
        String level = request.getParameter("level");

        if (level == null) {
            //查询当前的资料的所有种类
            List<String> courses = resourceService.selectCourse();
            if (courses.size() > 0) {
                status = new loadFile1Utils("200", "获取第一层成功");
                jsonData.put("status", status);
                for (int i = 0; i < courses.size(); i++) {
                    String id = String.valueOf(i + 1);
                    data.add(new loadFile2Utils(id, courses.get(i), false, "2014",
                            new checkArrUtils("0","0"), null,new basicDataUtils()));
                }
                jsonData.put("data", data);
            } else {
                status = new loadFile1Utils("110", "获取第一层失败");
                jsonData.put("status", status);
                jsonData.put("data", "");
            }
            return jsonData.toJSONString();
        }

        if (level.equals("1")) {
            //获取点击的科目
            String course = request.getParameter("context");
            String nodeId = request.getParameter("nodeId");
            //查询当前的科目资料的所有老师
            List<String> teacherIds = resourceService.getTeacherId(course);
            if (teacherIds.size() > 0) {
                status = new loadFile1Utils("200", "获取第二层成功");
                jsonData.put("status", status);
                for (int i = 0; i < teacherIds.size(); i++) {
                    String id = String.valueOf((1 * 100 + i));
                    List<teacher> teacherName = teacherService.getTeacherById(teacherIds.get(i));
                    data.add(new loadFile2Utils(id, teacherName.get(0).getRealName(),
                            false, nodeId,new checkArrUtils("0","0")
                            , null, new basicDataUtils(teacherIds.get(i),course)));
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
            String teacherId = request.getParameter("basicData[teacherId]");
            String course = request.getParameter("basicData[course]");
            String nodeId = request.getParameter("nodeId");
            System.out.println(teacherId);
            System.out.println(course);
            System.out.println(nodeId);
            List<resource> resInfo = resourceService.getResInfo(teacherId, course);
            System.out.println(resInfo);
            if (resInfo.size() > 0) {
                status = new loadFile1Utils("200", "获取第三层成功");
                jsonData.put("status", status);

                for (int i = 0; i < resInfo.size(); i++) {
                    String id = String.valueOf(1 * 1000 + i);
                    data.add(new loadFile2Utils(id, resInfo.get(i).getFileName(), true, nodeId,
                            new checkArrUtils("0","0"),null, null));
                }
                jsonData.put("data", data);

            } else {
                status = new loadFile1Utils("110", "获取第三层失败");
                jsonData.put("status", status);
                jsonData.put("data", "");
            }
            return jsonData.toJSONString();
        }

        return null;
    }

}
