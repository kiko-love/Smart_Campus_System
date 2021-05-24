package com.scs.service;

import com.scs.pojo.resource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface resourceService {
    /**
     * 增加资源信息
     * @param res
     * @return
     */
    int saveRes(resource res);

    /**
     * 根据文件名和所属科目查询资源信息
     * @param fileName
     * @param courseName
     * @param teacherId
     * @return
     */
    resource getResInfoById(String fileName,String courseName,String teacherId);

    /**
     * 删除某个资源信息
     * @param
     * @param
     * @return
     * */


    int deleteRes(Integer fileId);

    /**
     * 更新资料信息，用于覆盖某个资源
     * @param filepath
     * @param filename
     * @param courseName
     * @return
     */
    int updateRes(String filepath ,String filename,String courseName);

    /**
     * 查询某个科目下的所有老师
     * @param courseName
     * @return
     */
    List<String> getTeacherId(String courseName);

    /**
     * 查询表中的资源的所有课程
     * @return
     */
    List<String> selectcourseName();

    /**
     * 根据老师和学科查找对应的资源信息
     * @param teacherId
     * @param courseName
     * @return
     */
    List<resource> getResInfo(String teacherId, String courseName);

    /**
     *查询所有资料信息
     * @return
     */
    List<String> getCourseByTeacherId(String teacherId);

    /**
     * 根据课程名查资源
     * @param courseName
     * @return
     */
    List<resource> getResourceByCourse(String courseName);

    /**
     * 查找指定老师上传的资源
     * @param teacherId
     * @return
     */
    List<resource> getResourceByTeacherId(String teacherId);

    /**
     * 获取所有资源信息
     * @return
     */
    List<resource> getAllResource();

    /**
     * 批量删除
     * @param List
     * @return
     */
    int batchDeleteResource(List<Integer> List);

    /**
     * 根据fileId查找资料记录
     * @param fileId
     * @return
     */
    List<resource> selectResourceById(Integer fileId);
}
