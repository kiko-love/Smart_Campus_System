package com.scs.dao;

import com.scs.pojo.resource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface resourceMapper {
    //增加资源信息
    @Insert("insert into resource(fileId,fileName,teacherId,filePath) values(#{fileId},#{fileName},#{teacherId},#{filePath})")
    int saveRes(resource res);

    //查询资源信息
    @Select("select * from resource where fileName=#{fileName}")
    resource getResInfoById(String fileName);

    //删除信息
    @Delete("delete from resource where fileName=#{fileName}")
    int deleteRes(String fileName);

    @Update("update resource set filepath=#{arg0} where filename=#{arg1}")
    int updateRes(String filepath,String filename);
}
