package com.scs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.scs.pojo.TeacherResourceOB;
import com.scs.pojo.resource;
import com.scs.pojo.teacher;
import com.scs.service.resourceService;
import com.scs.service.teacherService;
import com.scs.utils.*;
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
    @CrossOrigin
    public String uploadResource(HttpServletRequest request, @RequestParam(value = "file") MultipartFile files[]) throws Exception {
        //获取要存放的位置,request.getSession().getServletContext()  获取项目路径,不同种类的学科的资料存放在相应的文件夹
        HttpSession session = request.getSession();
        String course = request.getParameter("course");
        //获取上传该文件的老师
        String teacherId = (String) session.getAttribute("userInformation");
        JSONObject data = new JSONObject();
        if (course.equals("") || teacherId.equals("")) {
            data.put("status", 200);
            data.put("success", 0);
            data.put("msg", "请求参数错误，请重新上传");
            data.put("data", null);
            return data.toJSONString();
        }

        String path = request.getSession().getServletContext().getRealPath("");
        String savePath = path.substring(0, path.indexOf("target\\response\\")) + "src\\resource\\" + course + "\\" + teacherId + "\\";
        File file = new File(savePath);
        //判断该文件夹是否存在
        if (!file.exists()) {
            file.mkdirs();
        }

        int[] count = new int[files.length];
        Map<String, String> fileData = new HashMap<String, String>();
        int length = 0;
        //判断实际的文件上传个数
        for (int i = 0; i < files.length; i++) {
            if (!files[i].getOriginalFilename().equals("")) {
                length++;
            }
        }
        Date createTime = new Date();
        for (int i = 0; i < length; i++) {
            //获取要上传的文件名scs
            String filename = files[i].getOriginalFilename();
            //获取可访问该文件的地址
            String filepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/resource/" + course + "/" + teacherId + "/" + filename;

            //获取文件大小
            String fileSize = DetermineFileSizeUtils.getSize(files[i]);

            //查看该文件是否已经存在
            resource resInfo = resourceService.getResInfoById(filename, course, teacherId);
            //如果该文件已经存在，覆盖原来的文件
            if (resInfo != null) {
                //删除已经存在服务器的同名文件
                File fileExist = new File(savePath + filename);
                fileExist.delete();
                count[i] = resourceService.updateRes(filepath, filename, course);
                files[i].transferTo(new File(savePath + filename));
            } else {
                count[i] = resourceService.saveRes(new resource(null, filename, teacherId, filepath, course, createTime, fileSize));
                files[i].transferTo(new File(savePath + filename));
            }
            //文件不存在，将资料存在服务器，信息存到数据库
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
    @ResponseBody
    public void downloadFiles(HttpServletRequest request, HttpServletResponse response) {
        String course = request.getParameter("course");
        String teacherId = request.getParameter("teacherId");
        List<String> filenames = JSONObject.parseArray(request.getParameter("contexts"), String.class);
        //响应头的设置
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        //设置压缩包的名字
        //解决不同浏览器压缩包名字含有中文时乱码的问题
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String downloadName = uuid + ".zip";
        String agent = request.getHeader("USER-AGENT");
        try {
            if (agent.contains("MSIE") || agent.contains("Trident")) {
                downloadName = java.net.URLEncoder.encode(downloadName, "UTF-8");
            } else {
                downloadName = new String(downloadName.getBytes("UTF-8"), "ISO-8859-1");
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
        for (int i = 0; i < filenames.size(); i++) {
            String fileName = resourceService.getResInfoById(filenames.get(i), course, teacherId).getFileName();
            String path = request.getSession().getServletContext().getRealPath("");
            String filepath = path.substring(0, path.indexOf("target\\response\\")) + "src\\resource\\" + course + "\\" + teacherId + "\\" + fileName;

            File file = new File(filepath);
            try {
                //添加ZipEntry，并ZipEntry中写入文件流
                zipos.putNextEntry(new ZipEntry(fileName));
                os = new DataOutputStream(zipos);
                InputStream is = new FileInputStream(file);
                byte[] b = new byte[1024];
                int length = 0;
                while ((length = is.read(b)) != -1) {
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
        ArrayList<JsonDataUtils> data = new ArrayList<>();
        JsonStatusUtils status = null;
        String level = request.getParameter("level");

        if (level == null) {
            //查询当前的资料的所有种类
            List<String> courses = resourceService.selectcourseName();
            if (courses.size() > 0) {
                status = new JsonStatusUtils("200", "获取第一层成功");
                jsonData.put("status", status);
                for (int i = 0; i < courses.size(); i++) {
                    String id = String.valueOf(i + 1);
                    data.add(new JsonDataUtils(id, courses.get(i), false, "2014",
                            null, null, new basicDataUtils()));
                }
                jsonData.put("data", data);
            } else {
                status = new JsonStatusUtils("110", "无资源数据");
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
            System.out.println(teacherIds);
            if (teacherIds.size() > 0) {
                status = new JsonStatusUtils("200", "获取第二层成功");
                jsonData.put("status", status);
                for (int i = 0; i < teacherIds.size(); i++) {
                    String id = String.valueOf((1 * 100 + i));
                    List<teacher> teacherName = teacherService.getTeacherById(teacherIds.get(i));
                    if (teacherName.size() > 0) {
                        data.add(new JsonDataUtils(id, teacherName.get(0).getRealName(),
                                false, nodeId, null
                                , null, new basicDataUtils(teacherIds.get(i), course, null, null)));
                    } else {
                        data.add(new JsonDataUtils(id, "[匿名教师]",
                                false, nodeId, new checkArrUtils("0", "0")
                                , null, new basicDataUtils(teacherIds.get(i), course, null, null)));
                    }

                }
                jsonData.put("data", data);
            } else {
                status = new JsonStatusUtils("110", "获取第二层失败");
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
            JSONArray jsonArray = null;
            if (resInfo.size() > 0) {
                status = new JsonStatusUtils("200", "获取第三层成功");
                jsonData.put("status", status);

                for (int i = 0; i < resInfo.size(); i++) {
                    //获取上传时间
                    Date createTime = resInfo.get(i).getCreateTime();
                    //获取上传文件大小
                    String filesize = resInfo.get(i).getFilesize();

                    String id = String.valueOf(1 * 1000 + i);
                    data.add(new JsonDataUtils(id, resInfo.get(i).getFileName(), true, nodeId,
                            new checkArrUtils("0", "0"), null, new basicDataUtils(null, null, createTime, filesize)));
                    String format = JSON.toJSONStringWithDateFormat(data, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
                    jsonArray = JSONArray.parseArray(format);
                }
                jsonData.put("data", jsonArray);

            } else {
                status = new JsonStatusUtils("110", "获取第三层失败");
                jsonData.put("status", status);
                jsonData.put("data", "");
            }
            return jsonData.toJSONString();
        }

        return null;
    }

    /**
     * 通过学科名查找该学科的资源
     *
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/selectByCourseName", produces = "application/json;charset=utf-8")
    @CrossOrigin
    public String selectByCourseName(HttpServletRequest request) {
        JsonStatusUtils status;
        JSONObject jsonData = new JSONObject();
        ArrayList<JsonDataUtils> data = new ArrayList<>();
        String course = request.getParameter("courseName");
        //当未输入课程时
        if (course == null || course.equals("")) {
            List<String> courses = resourceService.selectcourseName();
            if (courses.size() > 0) {
                status = new JsonStatusUtils("200", "获取第一层成功");
                jsonData.put("status", status);
                for (int i = 0; i < courses.size(); i++) {
                    String id = String.valueOf(i + 1);
                    data.add(new JsonDataUtils(id, courses.get(i), false, "2014",
                            null, null, new basicDataUtils()));
                }
                jsonData.put("data", data);
            } else {
                status = new JsonStatusUtils("110", "无资源数据");
                jsonData.put("status", status);
                jsonData.put("data", "");
            }
            return jsonData.toJSONString();
        }
        //查询当前的资料的所有种类
        List<resource> resInfo = resourceService.getResourceByCourse(course);
        if (resInfo.size() > 0) {
            status = new JsonStatusUtils("200", "查询成功");
            jsonData.put("status", status);
            data.add(new JsonDataUtils("1", resInfo.get(0).getCourseName(), false, "2014",
                    null, null, new basicDataUtils()));
            jsonData.put("data", data);
        } else {
            status = new JsonStatusUtils("110", "查询失败");
            jsonData.put("status", status);
            jsonData.put("data", "");
        }
        return jsonData.toJSONString();
    }

    /**
     * 获取老师本人上传的资源
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getInfoByTeacherId", produces = "application/json;charset=utf-8")
    @ResponseBody
    @CrossOrigin
    public String getInfoByTeacherId(HttpServletRequest request) {
        JSONObject jsonData = new JSONObject();
        JsonStatusUtils status;
        ArrayList jsonList = new ArrayList();
        String teacherId = (String) request.getSession().getAttribute("userInformation");
        String courseName = request.getParameter("course");
        //查询当前的资料的所有种类
        String level = request.getParameter("level");
        jsonData.put("code", 200);
        if (level == null) {
            jsonData.put("msg", "获取第一层成功");
            List<String> courses = resourceService.getCourseByTeacherId(teacherId);
            //当没有资源
            if (courses.size() == 0) {
                jsonData.put("msg", "无资源数据");
                jsonData.put("data", "");
                return jsonData.toJSONString();
            }
            int id = 0;
            for (String course : courses) {
                id++;
                jsonList.add(new TeacherResourceOB(id, null, null, null, null, true, course, null));
            }
            jsonData.put("data", jsonList);
            return jsonData.toJSONString();
        }
        List<resource> resources = resourceService.getResInfo(teacherId, courseName);
        if (resources.size() > 0) {
            int id = 1000;
            for (int i = 0; i < resources.size(); i++) {

                resource resource = resources.get(i);
                jsonList.add(new TeacherResourceOB(id, resource.getFileName(), resource.getFilesize(),
                        resource.getCreateTime(), resource.getFileId() * 10000, false, null, null));
                id++;
            }
            String format = JSON.toJSONStringWithDateFormat(jsonList, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
            JSONArray jsonArray = JSONArray.parseArray(format);
            jsonData.put("data", jsonArray);
            return jsonData.toJSONString();
        } else {
            status = new JsonStatusUtils("110", "无资源数据");
            jsonData.put("status", status);
            jsonData.put("data", "");
            return jsonData.toJSONString();
        }
    }

    /**
     * 单个资源的删除
     *
     * @param request
     * @return
     */
    public String deleteResource(HttpServletRequest request) {
        Integer fileId = Integer.valueOf(request.getParameter("fileId"));
        JSONObject data = new JSONObject();
        data.put("code", 0);
        if (fileId == null && fileId.equals("")) {
            data.put("msg", "没有找到该条记录");
            data.put("data", "");
            return data.toJSONString();
        }
        int deleteCount = resourceService.deleteRes(fileId / 1000);
        if (deleteCount == 0) {
            data.put("msg", "删除失败");
            data.put("data", "");
            return data.toJSONString();
        }
        data.put("msg", "删除成功");
        data.put("data", "");
        return data.toJSONString();
    }

    /**
     * 批量资源的删除
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/batchRemoveResource", produces = "application/json;charset=utf-8")
    public String batchRemoveResource(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        List<Integer> List = JSONObject.parseArray(request.getParameter("fileIds"), Integer.class);
        data.put("code", 0);
        //执行删除
        int removeNumber = resourceService.batchDeleteResource(List);
        if (removeNumber > 0) {
            data.put("success", "1");
            data.put("msg", "删除选中资源成功");
        } else {
            data.put("success", "0");
            data.put("msg", "删除选中资源失败");
        }
        data.put("count", removeNumber);
        data.put("data", null);
        return data.toJSONString();
    }
}
