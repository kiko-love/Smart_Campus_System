package com.scs.service.impl;

import com.scs.dao.myResourceMapper;
import com.scs.pojo.TeacherOfCourseOB;
import com.scs.pojo.myResource;
import com.scs.service.myResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myResourceService")
public class myResourceServiceImpl implements myResourceService {
    @Autowired
    private myResourceMapper myResMapper;
    @Override
    public int insertMyResource(myResource myResource) {
        return myResMapper.insertMyResource(myResource);
    }

    @Override
    public List<String> selectMyFocusCourse(String userId) {
        return myResMapper.selectMyFocusCourse(userId);
    }

    @Override
    public List<TeacherOfCourseOB> selectTeacherOfCourse(String userId, String courseName) {
        return myResMapper.selectTeacherOfCourse(userId,courseName);
    }

    @Override
    public List<myResource> selectOneResource(String userId, String courseName, String teacherId) {
        return myResMapper.selectOneResource(userId,courseName,teacherId);
    }

    @Override
    public int batchDeleteFocus(List<Integer> List) {
        return myResMapper.batchDeleteFocus(List);
    }

    @Override
    public List<myResource> selectMyResById(String userId) {
        return myResMapper.selectMyResById(userId);
    }
}
