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
    @Insert("insert into resource(fileId,fileName,teacherId,filePath,course) values(#{fileId},#{fileName},#{teacherId},#{filePath},#{course})")
    int saveRes(resource res);

    //根据文件名和所属科目查询资源信息
    @Select("select * from resource where fileName=#{arg0} and course=#{arg1} and teacherId=#{arg2}")
    resource getResInfoById(String fileName,String course,String teacherId);

    //删除信息
    @Delete("delete from resource where fileName=#{arg0} and course=#{arg1}")
    int deleteRes(String fileName,String course);

    @Update("update resource set filepath=#{arg0} where filename=#{arg1} and course=#{arg2}")
    int updateRes(String filepath ,String filename,String course);

    @Select("select teacherId from resource where course=#{course} group by teacherId")
    List<String> getTeacherId(String course);

    @Select("SELECT course FROM resource GROUP BY course")
    List<String> selectCourse();

    @Select("select * from resource where TeacherId=#{arg0} and course=#{arg1}")
    List<resource> getResInfo(String teacherId, String course);
}
