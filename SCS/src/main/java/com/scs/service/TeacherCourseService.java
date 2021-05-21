package com.scs.service;

import com.scs.pojo.RelationTeacherCourse;

import java.util.List;

public interface TeacherCourseService {
    int InsertTeacherCourse(RelationTeacherCourse item);

    List<String> SeclectTeacherCourse(String teacherid);
}
