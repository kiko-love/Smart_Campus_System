package com.scs.dao;

import com.scs.pojo.StudentLeave;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StudentLeaveMapper {
    /**
     *学生请假
     */
    @Insert("insert into leaveForm (userId,startTime,endTime,leaveReason,rejectReason,status,counselorId) " +
            "values (#{userId},#{startTime},#{endTime},#{leaveReason},#{rejectReason},#{status},#{counselorId})")
    int insertInfo(StudentLeave studentLeave);

    /**
     * 老师批假
     */
    @Update("update leaveForm set rejectInfo=#{rejectInfo},status=#{status} where leaveId=#{leaveId}")
    int updateStatus(String rejectInfo,Integer status,Integer leaveId);

    /**
     * 获取请假申请
     */
    @Select("select * from leaveForm where status=#{arg0} and counselorId=#{arg1}")
    List<StudentLeave> getLeaveOfStu(Integer status,String counselorId);
}
