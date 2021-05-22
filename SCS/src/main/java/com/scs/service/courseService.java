package com.scs.service;

import com.scs.pojo.course;

import java.util.List;

public interface courseService {
    List<course> getCourse();
    int updateCourse(course course);
    int insertCourse(course course);
    int deleteCourse(String courseId);
    int batchRemoveCourse(List<String> list);
    List<course> batchSelectCourse(List<String> list);
}
