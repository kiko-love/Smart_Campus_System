package com.scs.dao;

import com.scs.pojo.course;

import java.util.List;

public interface CourseMapper {
    //查询信息
    List<course> getCourse();
    int updateCourse(course course);
    int insertCourse(course course);
    int deleteCourse(String courseId);
    int batchRemoveCourse(List<String> list);
    List<course> batchSelectCourse(List<String> list);
}
