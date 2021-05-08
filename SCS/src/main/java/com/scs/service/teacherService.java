package com.scs.service;

import com.scs.pojo.teacher;
import org.springframework.stereotype.Service;

import java.util.List;
public interface teacherService {
    //获取老师列表
    public List<teacher> getTeachers();
    //增加一个老师信息
    public int addTeacher(teacher tea);
    //删除一个老师信息
    public int removeTeacher(String teacherId);
    //查询指定老师信息
    public List<teacher> getTeacherById(String teacherId);
    //修改老师信息
    public int updateTeacher(teacher tea);
    //批量删除老师
    public int batchRemoveTea(List<String> List);
}
