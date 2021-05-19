package com.scs.service;

import com.scs.pojo.resource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface resourceService {
    //增加资源信息

    int saveRes(resource res);

    //根据文件名和所属科目查询资源信息

    resource getResInfoById(String fileName,String course,String teacherId);

    //删除信息

    int deleteRes(String fileName,String course);


    int updateRes(String filepath ,String filename,String course);


    List<String> getTeacherId(String course);


    List<String> selectCourse();


    List<resource> getResInfo(String teacherId, String course);
}
