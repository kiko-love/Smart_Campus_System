package com.scs.service;

import com.scs.pojo.student;

import java.util.List;

public interface studentService {
    //获取学生列表
    public List<student> getStudents();
    //增加一个学生信息
    public int addStudent(student stu);
    //删除一个学生信息
    public int removeStudent(String userId);
    //查询指定学生信息
    public List<student> getStudentById(String userId);
    //修改学生信息
    public int updateStudent(student stu);
    //批量删除学生
    public int batchRemove(List<String> List);
}
