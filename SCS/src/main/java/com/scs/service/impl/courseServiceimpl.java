package com.scs.service.impl;
import com.scs.dao.CourseMapper;
import com.scs.pojo.course;
import com.scs.service.courseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("courseService")
public class courseServiceimpl implements courseService {
    @Autowired
    private CourseMapper courseMapper;
    @Override
    //获取课程信息
    public List<course> getCourse() {
        return courseMapper.getCourse();
    }

    @Override
    public int updateCourse(course course) {
        return courseMapper.updateCourse(course);
    }

    @Override
    public int insertCourse(course course) {
        return courseMapper.insertCourse(course);
    }

    @Override
    public int deleteCourse(String courseId) {
        return courseMapper.deleteCourse(courseId);
    }

    @Override
    public int batchRemoveCourse(List<String> list) {
        return courseMapper.batchRemoveCourse(list);
    }

    @Override
    public List<course> batchSelectCourse(List<String> list) {
        return courseMapper.batchSelectCourse(list);
    }
}
