package com.scs.service;

import com.scs.pojo.resource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface resourceService {
    //增加资源信息

    int saveRes(resource res);

    //查询资源信息

    resource getResInfo(String fileName);

    //删除信息

    int deleteRes(String fileName);

    int updateRes(String filepath,String filename);
}
