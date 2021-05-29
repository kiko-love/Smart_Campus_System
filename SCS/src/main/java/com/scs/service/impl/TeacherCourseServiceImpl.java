package com.scs.service.impl;

import com.scs.dao.TeacherCourseMapper;
import com.scs.pojo.RelationTeacherCourse;
import com.scs.service.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "TeacherCourseService")
public class TeacherCourseServiceImpl implements TeacherCourseService {
    @Autowired
    private TeacherCourseMapper teacherCourseMapper;
    @Override
    public int InsertTeacherCourse(RelationTeacherCourse item) {
        return teacherCourseMapper.InsertTeacherCourse(item);
    }

    @Override
    public List<String> SeclectTeacherCourse(String teacherid) {
        return teacherCourseMapper.SelectTeacherCourse(teacherid);
    }
}
