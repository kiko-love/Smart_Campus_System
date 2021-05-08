package com.scs.service.impl;

import com.scs.dao.teacherMapper;
import com.scs.pojo.teacher;
import com.scs.service.teacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teacherService")
public class TeacherServiceImpl implements teacherService {
    @Autowired
    private teacherMapper teaMapper;

    @Override
    public List<teacher> getTeachers() {
        return teaMapper.getTeachers();
    }

    @Override
    public int addTeacher(teacher tea) {
        return teaMapper.addTeacher(tea);
    }

    @Override
    public int removeTeacher(String teacherId) {
        return teaMapper.removeTeacher(teacherId);
    }

    @Override
    public List<teacher> getTeacherById(String teacherId) {
        return teaMapper.getTeacherById(teacherId);
    }

    @Override
    public int updateTeacher(teacher tea) {
        return teaMapper.updateTeacher(tea);
    }

    @Override
    public int batchRemoveTea(List<String> List) {
        return teaMapper.batchRemoveTea(List);
    }
}
