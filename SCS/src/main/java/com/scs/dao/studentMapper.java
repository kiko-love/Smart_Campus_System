package com.scs.dao;

import com.scs.pojo.student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface studentMapper {
    //获取学生列表
    public List<student> getStudents();
    //增加一个学生信息
    public int addStudent(student stu);
    //删除一个学生信息
    public int removeStudent(String userId);
    //查询指定学生信息
    public List<student> getStudentById(@Param("userId")String userId);
    //修改学生信息
    public int updateStudent(student stu);
}
