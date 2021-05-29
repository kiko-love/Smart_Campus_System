package com.scs.service;

import com.scs.pojo.resource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface resourceService {
    int saveRes(resource res);

    resource getResInfoById(String fileName,String courseName,String teacherId);

    int deleteRes(Integer fileId);

    int updateRes(String filepath ,String createTime,String fileSize,String filename,String courseName);

    List<String> getTeacherId(String courseName);

    List<String> selectcourseName();

    List<resource> getResInfo(String teacherId, String courseName);

    List<String> getCourseByTeacherId(String teacherId);

    List<resource> getResourceByCourse(String courseName);

    List<resource> getResourceByTeacherId(String teacherId);

    List<resource> getAllResource();

    int batchDeleteResource(List<Integer> List);

    List<resource> selectResourceById(Integer fileId);

    List<resource> selectByFileName(String teacherId,String fileName);
}
