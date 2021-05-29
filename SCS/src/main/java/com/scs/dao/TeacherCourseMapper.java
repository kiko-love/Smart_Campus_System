package com.scs.dao;

import com.scs.pojo.RelationTeacherCourse;

import java.util.List;

public interface TeacherCourseMapper {
    int InsertTeacherCourse(RelationTeacherCourse item);
    List<String> SelectTeacherCourse(String teacherid);
}
