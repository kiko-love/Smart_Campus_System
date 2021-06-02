package com.scs.dao;

import com.scs.pojo.myResource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface myResourceMapper {
    /**
     * 添加关注的课程和老师
     * @param myResource
     * @return
     */
    @Insert("insert into myResource (userId,courseName,teacherId,focusTime) values(#{userId},#{courseName},#{teacherId},#{focusTime})")
    int insertMyResource(myResource myResource);
    /**
     * 查找该同学关注的课程
     * @param userId
     * @return
     */
    @Select("select courseName from myResource where userId=#{userId} group by courseName")
    List<String> selectMyFocusCourse(String userId);

    /**
     * 查找某个课程下的学生关注的资源
     * @param userId
     * @param courseName
     * @return
     */
    @Select("select teacherId from myResource where userId=#{arg0} and courseName=#{arg1}")
    List<String> selectTeacherOfCourse(String userId,String courseName);

    /**
     * 查找是否关注了老师的资源
     * @param userId
     * @param courseName
     * @param teacherId
     * @return
     */
    @Select("select * from myResource where userId=#{arg0} and courseName=#{arg1} and teacherId=#{arg2}")
    List<myResource> selectOneResource(String userId,String courseName,String teacherId);

    /**
     * 取消关注
     */
    @Delete("<script>delete from myResource where focusId in " +
            "<foreach collection='list' item='id' open='(' separator=',' close=')'>#{id}</foreach></script>")
    int batchDeleteFocus(List<Integer> List);

    /**
     * 获取我已经关注的资源
     * @param userId
     * @return
     */
    @Select("select * from myResource where userId=#{userId}")
    List<myResource> selectMyResById(String userId);




}
