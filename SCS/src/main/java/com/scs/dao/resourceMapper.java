package com.scs.dao;

import com.scs.pojo.resource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface resourceMapper {
    //增加资源信息
    @Insert("insert into resource(fileId,fileName,teacherId,filePath,profession) values(#{fileId},#{fileName},#{teacherId},#{filePath},#{profession})")
    int saveRes(resource res);

    //根据文件名和所属科目查询资源信息
    @Select("select * from resource where fileName=#{arg0} and profession=#{arg1} and teacherId=#{arg2}")
    resource getResInfoById(String fileName,String profession,String teacherId);

    //删除信息
    @Delete("delete from resource where fileName=#{arg0} and profession=#{arg1}")
    int deleteRes(String fileName,String profession);

    @Update("update resource set filepath=#{arg0} where filename=#{arg1} and profession=#{arg2}")
    int updateRes(String filepath ,String filename,String profession);

    @Select("select teacherId from resource where profession=#{profession} group by teacherId")
    List<String> getTeacherId(String profession);

    @Select("SELECT profession FROM resource GROUP BY profession")
    List<String> selectProfession();

    @Select("select * from resource where TeacherId=#{arg0} and profession=#{arg1}")
    List<resource> getResInfo(String teacherId, String profession);
}
