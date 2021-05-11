package com.scs.service;

import com.scs.pojo.resource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface resourceService {
    //增加资源信息

    int saveRes(resource res);

    //查询资源信息
    resource getResInfoById(String fileName,String profession);

    //删除信息
    int deleteRes(String fileName,String profession);

    int updateRes(String filepath,String filename,String profession);

    List<String> getTeacherId(String profession);

    List<String> selectProfession();

    List<resource> getResInfo(String teacherId, String profession);
}
