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
     * @param
     * @param
     * @return
     */
    @Delete("delete from resource where fileId=#{fileId}")
    int deleteRes(Integer fileId);
    /**
     * 更新资料信息，用于覆盖某个资源
     * @param filepath
     * @param filename
     * @param courseName
     * @return
     */
    @Update("update resource set filepath=#{arg0},courseName=#{arg1},fileSize=#{arg2} where filename=#{arg3} and courseName=#{arg4} ")
    int updateRes(String filepath ,String createTime,String fileSize,String filename,String courseName);
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
    /**
     * 查找指定老师上传的资源
     * @param teacherId
     * @return
     */
    @Select("select * from resource where teacherId=#{teacherId}")
    List<resource> getResourceByTeacherId(String teacherId);
    /**
     * 获取所有资源信息
     * @return
     */
    @Select("select * from resource ")
    List<resource> getAllResource();
    /**
     * 批量删除
     * @param List
     * @return
     */
    @Delete("<script>delete from resource where fileId in " +
            "<foreach collection='list' item='id' open='(' separator=',' close=')'>#{id}</foreach></script>")
    int batchDeleteResource(List<Integer> List);
    /**
     * 根据fileId查找资料记录
     * @param fileId
     * @return
     */
    @Select("select * from resource where fileId=#{fileId}")
    List<resource> selectResourceById(Integer fileId);
    /**
     * 老师查找文件实现模糊查询
     * @param teacherId
     * @param fileName
     * @return
     */
    @Select("select * from resource where teacherId=#{arg0} and fileName like '%${arg1}' or fileName like '${arg1}%' or fileName like '%${arg1}%'")
    List<resource> selectByFileName(String teacherId,String fileName);
}
