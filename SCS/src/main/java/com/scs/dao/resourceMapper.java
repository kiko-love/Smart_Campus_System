package com.scs.dao;

import com.scs.pojo.resource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface resourceMapper {
    /**
     * 增加资源信息
     * @param res
     * @return
     */
    @Insert("insert into resource(fileId,fileName,teacherId,filePath,courseName,createTime,filesize) values(#{fileId},#{fileName},#{teacherId},#{filePath},#{courseName},#{createTime},#{filesize})")
    int saveRes(resource res);

    /**
     * 根据文件名和所属科目查询资源信息
     * @param fileName
     * @param courseName
     * @param teacherId
     * @return
     */
    @Select("select * from resource where fileName=#{arg0} and courseName=#{arg1} and teacherId=#{arg2}")
    resource getResInfoById(String fileName,String courseName,String teacherId);

    /**
     * 删除某个资源信息
     * @param fileName
     * @param courseName
     * @return
     */
    @Delete("delete from resource where fileName=#{arg0} and courseName=#{arg1}")
    int deleteRes(String fileName,String courseName);

    /**
     * 更新资料信息，用于覆盖某个资源
     * @param filepath
     * @param filename
     * @param courseName
     * @return
     */
    @Update("update resource set filepath=#{arg0} where filename=#{arg1} and courseName=#{arg2}")
    int updateRes(String filepath ,String filename,String courseName);

    /**
     * 查询某个科目下的所有老师
     * @param courseName
     * @return
     */
    @Select("select teacherId from resource where courseName=#{courseName} group by teacherId")
    List<String> getTeacherId(String courseName);

    /**
     * 查询表中的资源的所有课程
     * @return
     */
    @Select("SELECT courseName FROM resource GROUP BY courseName")
    List<String> selectcourseName();

    /**
     * 根据老师和学科查找对应的资源信息
     * @param teacherId
     * @param courseName
     * @return
     */
    @Select("select * from resource where TeacherId=#{arg0} and courseName=#{arg1}")
    List<resource> getResInfo(String teacherId, String courseName);

    /**
     *查询所有资料信息
     * @return
     */
    @Select("select courseName from resource where teacherId=#{teacherId} group by courseName;")
    List<String> getCourseByTeacherId(String teacherId);

    /**
     * 根据课程名查资源
     * @param courseName
     * @return
     */
    @Select("select * from resource where courseName=#{courseName}")
    List<resource> getResourceByCourse(String courseName);
}
